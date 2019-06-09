let submitButton = document.getElementById('submit_button');

//TODO
let submitTask = function () {

    let table = document.getElementById('customer-list');

    submitButton.disabled = table.getElementsByTagName("tr").length < 2;

};

submitButton.onclick = function () {

    let table = document.getElementById('customer_list');

    let rowCount = table.rows.length;

    submitButton.disabled = rowCount === 1;

};
