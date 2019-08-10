let submitButton = document.getElementById('submit_button');

submitButton.onclick = function () {

    let table = document.getElementById('customer_list');

    let rowCount = table.rows.length;

    let validation = true;

    let i ;
    for ( i = 0; i < rowCount; i++ ) {

        let name = document.getElementsByName('name[' + i + ']');
        let cost = document.getElementsByName('cost[' + i + ']');

        if ( cost.value <= 0.01 ) {

            return true;

        }

        if ( ( name.length >= 1 && cost.length >= 1 ) && ( checkEmpty( name[0] ) || checkEmpty( cost[0] ) ) ) {

            if ( rowCount !== 1 ) {

                validation = false;

            }

        }

    }

    if (validation)
        document.getElementById( 'customerInfoForm').submit();

    return false;

};
