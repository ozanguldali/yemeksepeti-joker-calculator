let name, initialCost;
let inputForm = document.getElementById('input_form');
let addButton = document.getElementById('add_button');

function addRow(key, value) {

    let table = document.getElementById('customer_list');

    let rowCount = table.rows.length;

    let row = table.insertRow( rowCount );
    let cell1 = row.insertCell(0);

    cell1.innerHTML = key;
    let cell2 = row.insertCell(1);

    cell2.innerHTML = value;
    let cell3 = row.insertCell(2);
    let deleteButtonElement = document.createElement("button");
    deleteButtonElement.type = "button";
    deleteButtonElement.id = "delete_element_button";
    deleteButtonElement.name = "delete_element_button[" + rowCount + "]";
    deleteButtonElement.className = "delete_element-btn";

    // deleteButtonElement.setAttribute( "onclick", "deleteElementButton.click();" );
    let deleteIconElement = document.createElement("span");

    deleteIconElement.className = "fa fa-trash";

    deleteButtonElement.appendChild(deleteIconElement);

    cell3.appendChild(deleteButtonElement);

}

addButton.onclick = function() {

    name = document.getElementById('name');
    initialCost = document.getElementById('initial_cost');

    if ( !( checkEmpty(name) || checkEmpty(initialCost) ) ) {

        addRow(name.value, initialCost.value);

    } else {
        return true;
    }

    inputForm.reset();
    return false;

};

//TODO: Could not perform field validation
/*$(table).ready(function () {

    $(".input-add-class").click(function () {
        let name = $("#name").val();
        let initialCost = $("#initial_cost").val();

        let markup = "<tr><td>" + name + "</td>" +
            "<td>" + initialCost + "</td>" +
            "<td><button type=\"button\" id=\"delete_element_button\" name=\"delete_element_button[1]\" " +
            "class=\"delete_element-btn\">" + "<span class=\"fa fa-trash\"></span>" +
            "</button></td>" +
            "</tr>";
        $(table).append(markup);
        inputForm.reset();
        return false;

    });

});*/
