// let cleanButton = document.getElementById('clear_button');

cleanButton.onclick = function () {

    let table = document.getElementById('customer_list');

    let rowCount = table.rows.length;

    for ( let i = rowCount - 1; i > 0; i-- ) {
        table.deleteRow( i );
    }

    defaultDecimal.checked = true;

    customerListDiv.hidden = true;

    customerListDiv.hidden = true;

    error_injecting.hidden = true;

    error_injecting.innerText = "Yükleniyor...";

    loadButton.disabled = false;

};
