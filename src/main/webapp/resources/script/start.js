"use strict"

/**
 * @type {HTMLCanvasElement}
 */
const clockCanvas = document.getElementById("clock");

const {height, width} = clockCanvas.getBoundingClientRect();
if (width !== height) {
    console.error("Plot is not a square");
}

const center = width / 2,
    radius = center * 0.9,

    borderWidth = width / 50,

    streakLength = 1.5 * borderWidth,
    streakWidth = borderWidth / 2,

    hoursLength = radius * 0.6,
    minutesLength = radius * 0.8,
    secondLength = radius * 0.9,

    hoursWidth = borderWidth,
    minutesWidth = hoursWidth / 2,
    secondsWidth = minutesWidth / 2,

    fontSize = 1.5 * width / 15,
    fontSizeStr = "px",
    fontFamily = "Comic Sans MS",
    fontStr = fontSizeStr + " " + fontFamily;

let rgbString;

updateClock();
drawClock();

function updateClock() {
    rgbString = "rgb(" +
        Math.round(Math.random() * 255) + "," +
        Math.round(Math.random() * 255) + "," +
        Math.round(Math.random() * 255) +
        ")";
}

function drawClock() {
    if (clockCanvas.getContext) {

        const ctx = clockCanvas.getContext("2d");

        ctx.clearRect(0,0,width,height);

        // form
        ctx.fillStyle = rgbString;
        ctx.beginPath();
        ctx.arc(center, center, radius, 0, 2 * Math.PI, false);
        ctx.fill();
        // border
        ctx.strokeStyle = "black"
        ctx.lineWidth = borderWidth;
        ctx.beginPath();
        ctx.arc(center, center, radius, 0, 2 * Math.PI, false);
        ctx.stroke();

        // streaks
        ctx.strokeStyle = "black"
        ctx.lineWidth = streakWidth;
        for (let i = 0; i < 12; i++) {
            const angle = 2 * Math.PI * i / 12;
            ctx.beginPath();
            ctx.moveTo(center + radius * Math.sin(angle), center - radius * Math.cos(angle));
            ctx.lineTo(center + (radius - streakLength) * Math.sin(angle), center - (radius - streakLength) * Math.cos(angle));
            ctx.stroke();
        }

        const date = new Date();

        const day = date.toDateString();

        // date
        ctx.strokeStyle = "gray"
        ctx.font = fontSize + fontStr;
        ctx.textBaseline = "middle";
        ctx.textAlign = "center";
        ctx.strokeText(day, center, center);

        const hours = date.getHours() % 12,
            hoursAngle = 2 * Math.PI * hours / 12,
            minutes = date.getMinutes(),
            minutesAngle = 2 * Math.PI * minutes / 60,
            seconds = date.getSeconds(),
            secondsAngle = 2 * Math.PI * seconds / 60;

        // hours
        ctx.strokeStyle = "black"
        ctx.lineWidth = hoursWidth;
        ctx.beginPath();
        ctx.moveTo(center, center);
        ctx.lineTo(center + hoursLength * Math.sin(hoursAngle), center - hoursLength * Math.cos(hoursAngle));
        ctx.stroke();
        // minutes
        ctx.strokeStyle = "black"
        ctx.lineWidth = minutesWidth;
        ctx.beginPath();
        ctx.moveTo(center, center);
        ctx.lineTo(center + minutesLength * Math.sin(minutesAngle), center - minutesLength * Math.cos(minutesAngle));
        ctx.stroke();
        // seconds
        ctx.strokeStyle = "red"
        ctx.lineWidth = secondsWidth;
        ctx.beginPath();
        ctx.moveTo(center, center);
        ctx.lineTo(center + secondLength * Math.sin(secondsAngle), center - secondLength * Math.cos(secondsAngle));
        ctx.stroke();

    }
}


setInterval(() => {
    drawClock();
}, 1000)
setInterval(() => {
    updateClock();
}, 8000)


/**
 * @type {HTMLImageElement}
 */
const cookie = document.getElementById("cookieImage");

cookie.onclick = () => {
    if (Math.random() < 0.8) {
        alert("What's time is it?");
    } else {
        alert("IT'S TEA TIME!");
        cookie.hidden = true; // Cookies also want tea
        setTimeout(() => {
            window.location.href = "/easter_egg_page.xhtml";
        }, 2000)
    }
}