//let closeAlertButton = document.getElementById("close_alert");
let i;

closeAlertButton.onclick = function () {

    let div = this.parentElement;
    div.style.opacity = "0";

    setTimeout(function() {

        div.style.display = "none";

        },

    600);

};
