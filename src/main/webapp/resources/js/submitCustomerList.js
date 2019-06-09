let submitButton = document.getElementById('submit_button');

//TODO
let submitTask = function () {

    let table = document.getElementById('customer-list');

    submitButton.disabled = table.getElementsByTagName("tr").length < 2;

};

/*let setCustomerInfo = function () {

    let temp_customerList = new Map();

    let table = document.getElementById('customer-list');

    let rowCount = table.rows.length;

    for ( let i = 1; i < rowCount; i++ ) {

        let name = table.getElementsByTagName('tr')[ i ].getElementsByTagName( 'td' )[ 0 ];
        let cost = table.getElementsByTagName('tr')[ i ].getElementsByTagName( 'td' )[ 1 ];

        temp_customerList.set(name, cost);

    }

    let customerList = '<%=temp_customerList%>';

};*/

submitButton.onclick = function () {

    let table = document.getElementById('customer_list');

    let rowCount = table.rows.length;

    if ( rowCount !== 1 ) {

        window.location.href = "jokerResult.html";

        document.getElementById("hiddenField" ).submit();

    }

};
