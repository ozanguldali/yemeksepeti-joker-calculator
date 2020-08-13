let name, initialCost, iterator = 0;
/*let inputForm = document.getElementById('input_form');
let initialCostField = document.getElementById('initial_cost');
let addButton = document.getElementById('add_button');
let customerListDiv = document.getElementById( 'customerList' );
let submitButton = document.getElementById('submit_button');*/

let checkEmpty = function (key) {
    return ( key.value.length === 0 || key.value === '' )
};

function addRow(key, value) {

    let table = document.getElementById('customer_list');

    let rowCount = table.rows.length;

    let row = table.insertRow( rowCount );

    let nameCell = row.insertCell(0);
    //nameCell.innerHTML = key;

    let nameElement = document.createElement( "input" );
    nameElement.required = true;
    nameElement.type = "text";
    nameElement.name = "name[" + iterator + "]";
    nameElement.className = "input-customer-class";
    nameElement.minLength = 1;
    nameElement.maxLength = 30;
    nameElement.value = key;

    nameCell.appendChild(nameElement);

    let costCell = row.insertCell(1);
    //costCell.innerHTML = value;

    let costElement = document.createElement( "input" );
    costElement.required = true;
    costElement.type = "number";
    costElement.name = "cost[" + iterator + "]";
    costElement.className = "input-customer-class";
    costElement.minLength = 0.01;
    costElement.maxLength = 999999;
    costElement.step = "0.01";
    costElement.pattern = "^\\d+(?:\\.\\d{1,2})?$";
    costElement.value = value;

    costCell.appendChild(costElement);

    let deleteCell = row.insertCell(2);
    let deleteButtonElement = document.createElement("button");

    deleteButtonElement.type = "button";
    deleteButtonElement.id = "delete_element_button";
    deleteButtonElement.name = "delete_element_button[" + iterator + "]";

    deleteButtonElement.className = "delete_element-btn";

    // deleteButtonElement.setAttribute( "onclick", "deleteElementButton.click();" );
    let deleteIconElement = document.createElement("span");

    deleteIconElement.className = "fa fa-trash";

    deleteButtonElement.appendChild(deleteIconElement);

    deleteCell.appendChild(deleteButtonElement);

    iterator = iterator + 1;

}

initialCostField.oninput = function () {
    if (this.value.length > 6) {
        this.value = this.value.slice(0,6);
    }

};

loadButton.onclick = function() {

    loadButton.disabled = true;

    error_injecting.hidden = false;

    if ( onWindowLoad() === true ) {

        customerListDiv.hidden = false;

        let order_table = injecting_DOM.querySelector("body > div.container.ys-main > div > div > div.row.basket-info > div > div > table > tbody");

        let rowCount = order_table.rows.length;

        let i, value, count;
        for ( i = 0; i < rowCount; i++ ) {

            name = order_table.rows[ i ].cells[0].querySelector( "b" ).innerText;

            name = name.replace( '&', 'and' )

            count = order_table.rows[ i ].cells[2].innerText;

            value = order_table.rows[ i ].cells[3].innerText;
            value = value.trim().replace( "TL", "" ).trim().replace( ",", "." ).trim();
            initialCost = parseFloat( value );

            console.log(name);
            console.log(initialCost);

            if ( count === "1" )
                addRow(name, initialCost);

            else {

                let j;
                for ( j = 0; j < count; j++ ) {

                    addRow(name, initialCost/count);

                }

            }

        }

        console.log( injecting_htmlString );

        return false;

    }

    setTimeout(function() {

        error_injecting.innerText = 'Lütfen Elle Giriş Yapınız, veya Yeniden Başlatınız.';

    }, 3 * 1000 );

    console.log( injecting_htmlString );

    return false;

};

addButton.onclick = function() {

    name = document.getElementById('name');

    initialCost = document.getElementById('initial_cost');
    if ( initialCost.value <= 0.01 ) {

        return true;

    }

    if ( !( checkEmpty(name) || checkEmpty(initialCost) ) ) {

        addRow(name.value, initialCost.value);

    } else {

        return true;

    }

    inputForm.reset();

    customerListDiv.hidden = false;

    return false;

};