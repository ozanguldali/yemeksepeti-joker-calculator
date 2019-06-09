let submitButton = document.getElementById('submit_button');

//TODO
let submitTask = function () {

    let table = document.getElementById('customer-list');

    submitButton.disabled = table.getElementsByTagName("tr").length < 2;

};

submitButton.onclick = function () {

    window.location.href = "jokerResult.html";

};
