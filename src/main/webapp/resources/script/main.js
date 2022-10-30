"use strict"

// noinspection ES6UnusedImports
import {
    validateNumberRadioField,
    validateNumberCheckboxField,
    validateNumberTextField,

    clearNumberRadioField,
    clearNumberTextField,
    clearNumberCheckboxField
}
    from "./validators.js";

// noinspection ES6UnusedImports
import {
    getRadioNumberValue,
    forcedGetRadioValue,

    getTextNumberValue,
    forcedGetTextValue,

    getCheckboxNumberValue,
    forcedGetCheckboxValue
}
    from "./getters.js";

/**
 * @type {NodeListOf<HTMLInputElement>}
 */
let xField = document.getElementsByName("submitForm:xSpinner_input"),
    yField = document.getElementsByName("submitForm:yTextField"),
    rField = document.getElementsByName("submitForm:rCheckbox");

/**
 * @type {HTMLFormElement}
 */
let submitForm = document.getElementById("submitForm");

/**
 * @type {HTMLInputElement}
 */
let submitButton = document.getElementById("submitForm:submitButton");

/**
 * @type {HTMLTableElement}
 */
let historyTableContent = document.getElementById("historyTable_data");

/**
 * @type {HTMLCanvasElement}
 */
let canvas = document.getElementById("plotCanvas");

/**
 * @type {boolean}
 */
let xValid = false,
    yValid = false,
    rValid = false;

// Main function
setInterval(() => {
    submitForm = document.getElementById("submitForm");
    if (!submitForm.classList.contains("apply-all-listeners")) {
        reinitializeVariables();
        initializeFields();
        initTable();
        initCanvas();
        submitForm.classList.add("apply-all-listeners")
    }
}, 100)
// Main interval


function reinitializeVariables() {
    xField = document.getElementsByName("submitForm:xSpinner_input");
    yField = document.getElementsByName("submitForm:yTextField");
    rField = document.getElementsByName("submitForm:rCheckbox");
    submitButton = document.getElementById("submitForm:submitButton");
    historyTableContent = document.getElementById("historyTable_data");
    canvas = document.getElementById("plotCanvas");
}

function initializeFields() {
    initializeXField();
    initializeYField();
    initializeRField();
    setInterval(() => {
        validateFields();
    }, 1000)
}

function validateFields() {
    validateXField();
    validateYField();
    validateRField();
}

function initializeXField() {
    xField.forEach(element => {
        element.onchange = () => { validateXField() };
    })
}

/**
 * @return {boolean}
 */
function validateXField() {
    xValid = validateNumberTextField(xField, -3, 3);
    return xValid;
}

/**
 * @return {number|null}
 */
function forcedGetX() {
    const xValue = parseFloat(forcedGetTextValue(xField));
    if (!isNaN(xValue)) {
        return xValue;
    } else {
        return null;
    }
}

/**
 * @param {string} value
 */
function forcedSetX(value) {
    xField.forEach(element => {
        element.value = value;
    })
}



function initializeYField() {
    yField.forEach(element => {
        element.onkeyup = () => { validateYField() };
        element.onkeydown = () => { validateYField() };
    })
}

/**
 * @return {boolean}
 */
function validateYField() {
    yValid = validateNumberTextField(yField, -5, 5);
    return yValid;
}

/**
 * @return {number|null}
 */
function forcedGetY() {
    const yValue = parseFloat(forcedGetTextValue(yField));
    if (!isNaN(yValue)) {
        return yValue;
    } else {
        return null;
    }
}

/**
 * @param {string} value
 */
function forcedSetY(value) {
    yField.forEach(element => {
        element.value = value;
    })
}



function initializeRField() {
    rField.forEach(value => {
        value.onclick = (() => { drawPlotOnCanvas(validateAndGetRValueOrNull()) })
    })
}

/**
 * @return {null|number}
 */
function validateAndGetRValueOrNull() {
    validateRField();
    return getRValueOrNull();
}

/**
 * @return {null|number}
 */
function getRValueOrNull() {
    if (!rValid) return null;
    return getCheckboxNumberValue(rField);
}

/**
 * @return {boolean}
 */
function validateRField() {
    rValid = validateNumberCheckboxField(rField);
    return rValid;
}

/**
 * @return {number|null}
 */
function forcedGetR() {
    const rValue = parseFloat(forcedGetCheckboxValue(rField));
    if (!isNaN(rValue)) {
        return rValue;
    } else {
        return null;
    }
}



/**
 * @type {HTMLTableRowElement|null}
 */
let hoveringRow = null;

function initTable() {
    for (let row of historyTableContent.rows) {
        row.onmouseenter = () => {
            hoveringRow = row;
            drawPlot();
        }
        row.onmouseleave = () => {
            hoveringRow = null;
            drawPlot();
        }
    }
}


const {height, width} = canvas.getBoundingClientRect();
if (width !== height) {
    console.error("Plot is not a square");
}

const center = width / 2,
    unit = width / 10,

    axisWidth = width / 100,
    axisWidthOffset = (axisWidth - 1) / 2,
    axisMargin = width / 60,

    arrowLength = width / 20,
    arrowWidth = arrowLength / 3,

    streakLengthOffset = axisWidth,
    streakLength = 2 * streakLengthOffset + 1,
    streakWidth = axisWidth,
    streakWidthOffset = axisWidthOffset,

    fontSize = width / 15,
    fontSizeStr = "px",
    fontFamily = "Comic Sans MS",
    fontStr = fontSizeStr + " " + fontFamily;

const xCol = 3,
    yCol = 4,
    rCol = 5,
    resCol = 6;

let xPoint = null,
    yPoint = null;
const xPointRoundParam = 1e0,
    yPointRoundParam = 1e2;

/**
 * @param {number|null} newValue
 */
function setXPoint(newValue) {
    if (newValue == null) {
        xPoint = newValue;
    } else {
        xPoint = Math.min(
            Math.max(
                Math.round(newValue * xPointRoundParam) / xPointRoundParam,
                -3
            ),
            3
        );
    }
}

/**
 * @param {number|null} newValue
 */
function setYPoint(newValue) {
    if (newValue == null) {
        yPoint = newValue;
    } else {
        yPoint = Math.round(newValue * yPointRoundParam) / yPointRoundParam;
    }
}

drawPlotOnCanvas(null);

function drawPlot() {
    drawPlotOnCanvas(forcedGetR())
}

/**
 * @param {number|null} rValue
 */
function drawPlotOnCanvas(rValue) {
    if (canvas.getContext) {
        const ctx = canvas.getContext("2d");

        ctx.clearRect(0,0,width,height);

        // ctx.strokeRect(0,0,width,height);

        if (rValue && rValue > 0) {
            const rValueHalf = rValue / 2;

            ctx.fillStyle = "lightblue";
            // area
            ctx.beginPath();
            ctx.moveTo(center, center);
            ctx.lineTo(center, center - rValueHalf * unit);
            ctx.lineTo(center - rValueHalf * unit, center);
            ctx.arcTo(center - rValueHalf * unit, center + rValueHalf * unit, center, center + rValueHalf * unit, rValueHalf * unit);
            ctx.lineTo(center, center + rValue * unit);
            ctx.lineTo(center + rValueHalf * unit, center + rValue * unit);
            ctx.lineTo(center + rValueHalf * unit, center);
            ctx.fill();
        }

        ctx.fillStyle = "black";
        // OX
        ctx.fillRect(axisMargin, center - axisWidthOffset, width - 2 * axisMargin, axisWidth);
        ctx.beginPath();
        ctx.moveTo(width, center);
        ctx.lineTo(width - arrowLength, center - arrowWidth);
        ctx.lineTo(width - arrowLength, center + arrowWidth + 1);
        ctx.lineTo(width, center + 1);
        ctx.fill();

        // OY
        ctx.fillRect(center - axisWidthOffset, axisMargin, axisWidth, width - 2 * axisMargin);
        ctx.beginPath();
        ctx.moveTo(center, 0);
        ctx.lineTo(center - arrowWidth, arrowLength);
        ctx.lineTo(center + arrowWidth + 1, arrowLength);
        ctx.lineTo(center + 1, 0)
        ctx.fill();

        // lines
        ctx.fillRect(center - streakLengthOffset, center + 4 * unit - streakWidthOffset, streakLength, streakWidth);
        ctx.fillRect(center - streakLengthOffset, center + 3 * unit - streakWidthOffset, streakLength, streakWidth);
        ctx.fillRect(center - streakLengthOffset, center + 2 * unit - streakWidthOffset, streakLength, streakWidth);
        ctx.fillRect(center - streakLengthOffset, center + unit - streakWidthOffset, streakLength, streakWidth);
        ctx.fillRect(center - streakLengthOffset, center - unit - streakWidthOffset, streakLength, streakWidth);
        ctx.fillRect(center - streakLengthOffset, center - 2 * unit - streakWidthOffset, streakLength, streakWidth);
        ctx.fillRect(center - streakLengthOffset, center - 3 * unit - streakWidthOffset, streakLength, streakWidth);
        ctx.fillRect(center - streakLengthOffset, center - 4 * unit - streakWidthOffset, streakLength, streakWidth);
        ctx.fillRect(center + 4 * unit - streakWidthOffset, center - streakLengthOffset, streakWidth, streakLength);
        ctx.fillRect(center + 3 * unit - streakWidthOffset, center - streakLengthOffset, streakWidth, streakLength);
        ctx.fillRect(center + 2 * unit - streakWidthOffset, center - streakLengthOffset, streakWidth, streakLength);
        ctx.fillRect(center + unit - streakWidthOffset, center - streakLengthOffset, streakWidth, streakLength);
        ctx.fillRect(center - unit - streakWidthOffset, center - streakLengthOffset, streakWidth, streakLength);
        ctx.fillRect(center - 2 * unit - streakWidthOffset, center - streakLengthOffset, streakWidth, streakLength);
        ctx.fillRect(center - 3 * unit - streakWidthOffset, center - streakLengthOffset, streakWidth, streakLength);
        ctx.fillRect(center - 4 * unit - streakWidthOffset, center - streakLengthOffset, streakWidth, streakLength);

        // text OX
        ctx.font = fontSize + fontStr;
        ctx.textAlign = "center";
        ctx.textBaseline = "bottom";
        ctx.fillText("-4", center - 4 * unit, center - streakWidth);
        ctx.fillText("-3", center - 3 * unit, center - streakWidth);
        ctx.fillText("-2", center - 2 * unit, center - streakWidth);
        ctx.fillText("-1", center - unit, center - streakWidth);
        ctx.fillText("1", center + unit, center - streakWidth);
        ctx.fillText("2", center + 2 * unit, center - streakWidth);
        ctx.fillText("3", center + 3 * unit, center - streakWidth);
        ctx.fillText("4", center + 4 * unit, center - streakWidth);

        // text OY
        ctx.textAlign = "left";
        ctx.textBaseline = "middle";
        ctx.fillText("-4", center + streakLength, center + 4 * unit);
        ctx.fillText("-3", center + streakLength, center + 3 * unit);
        ctx.fillText("-2", center + streakLength, center + 2 * unit);
        ctx.fillText("-1", center + streakLength, center + unit);
        ctx.fillText("1", center + streakLength, center - unit);
        ctx.fillText("2", center + streakLength, center - 2 * unit);
        ctx.fillText("3", center + streakLength, center - 3 * unit);
        ctx.fillText("4", center + streakLength, center - 4 * unit);

        // X
        ctx.textAlign = "left";
        ctx.textBaseline = "top";
        ctx.fillText("X", width - arrowLength - arrowWidth, center + arrowWidth * 2);

        // Y
        ctx.textAlign = "right";
        ctx.textBaseline = "bottom";
        ctx.fillText("Y", center - arrowWidth * 2, fontSize + arrowWidth);

        // points from table
        const historyTableContent = document.getElementById("historyTable_data");
        for (const row of historyTableContent.rows) {
            if (!row.classList.contains("ui-datatable-empty-message")) {
                const xValueCell = parseFloat(row.cells[xCol].innerText),
                    yValueCell = parseFloat(row.cells[yCol].innerText),
                    rValueCell = parseFloat(row.cells[rCol].innerText),
                    resCell = row.cells[resCol].innerText.toLowerCase();
                if (!isNaN(xValueCell) && !isNaN(yValueCell) && !isNaN(rValueCell)) {
                    ctx.fillStyle = (rValueCell === rValue) ? ((resCell.includes("not")) ? "orange" : "green") : "grey";
                    ctx.beginPath();
                    const pointScale = (row === hoveringRow) ? 1.5 : 1;
                    ctx.arc(center + unit * xValueCell, center - unit * yValueCell, streakWidth * 1.5 * pointScale, 0, 2 * Math.PI, false);
                    ctx.fill();
                }
            }
        }

        // red point
        if (xPoint != null && yPoint != null) {
            ctx.fillStyle = "red";
            ctx.beginPath();
            ctx.arc(center + unit * xPoint, center - unit * yPoint, streakWidth, 0, 2 * Math.PI, false);
            ctx.fill();

            ctx.font = (fontSize * 0.8) + fontStr;
            ctx.textAlign = (xPoint > 0) ? "end" : "start";
            ctx.textBaseline = (yPoint > 0) ? "top" : "bottom";
            ctx.fillText(" " + xPoint + ", " + yPoint + " ", center + unit * xPoint, center - unit * yPoint);
        }
    }
}

/**
 * @param {number} xOffset
 * @return {number}
 */
function getXCoordinateOrConvert(xOffset) {
    let xValue = forcedGetX();
    if (xValue == null) xValue = convertXValueInCoordinate(xOffset);
    return xValue;
}

/**
 * @param {number} xOffset
 * @return {number}
 */
function convertXValueInCoordinate(xOffset) {
    return (xOffset - center) / unit;
}

/**
 * @param {number} yOffset
 * @return {number}
 */
function getYCoordinateOrConvert(yOffset) {
    let yValue = forcedGetY();
    if (yValue == null) yValue = convertYValueInCoordinate(yOffset);
    return yValue;
}

/**
 * @param {number} yOffset
 * @return {number}
 */
function convertYValueInCoordinate(yOffset) {
    return - (yOffset - center) / unit;
}

function initCanvas() {
    canvas.onmousemove = (event) => {
        const bound = canvas.getBoundingClientRect();
        setXPoint(getXCoordinateOrConvert(event.x - bound.x));
        setYPoint(getYCoordinateOrConvert(event.y - bound.y));
        drawPlot();
    }

    canvas.onmouseleave = () => {
        setXPoint(null);
        setYPoint(null);
        drawPlot();
    }

    canvas.onclick = () => {
        const rValue = validateAndGetRValueOrNull();
        if (rValue) {
            forcedSetX((xPoint == null) ? "" : xPoint.toString());
            forcedSetY((yPoint == null) ? "" : yPoint.toString());
            document.getElementById("submitForm:submitButton").click();
            setTimeout(() => {
                drawPlot();
            }, 500)
        } else {
            rField[0].parentElement.parentElement.classList.add("blink");
            setTimeout(() => {
                rField[0].parentElement.parentElement.classList.remove("blink");
            }, 50)
        }
    }
}


document.getElementById("cookieImage").onclick = () => {
    if (Math.random() < 0.8) {
        alert("This site uses cookies, but you are not asked :>");
    } else {
        alert("Don't crumble your cookies here!");
    }
}