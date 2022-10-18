document.getElementById("cookieImage").onclick = () => {
    if (Math.random() < 0.8) {
        alert("This site uses cookies, but you are not asked :>");
    } else {
        alert("Don't crumble your cookies here!");
    }
}