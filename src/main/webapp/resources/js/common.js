let closeAlertButton = document.getElementById('close_alert');
let inputForm = document.getElementById('input_form');
let initialCostField = document.getElementById('initial_cost');
let error_injecting = document.getElementById( 'error_injecting' );
let injecting_htmlString = '';
let injecting_DOM;
let loadButton = document.getElementById( 'load_button' );
let addButton = document.getElementById('add_button');
let customerListDiv = document.getElementById( 'customerList' );
// let submitButton = document.getElementById('submit_button');
let table = document.getElementById('customer_list');
let defaultDecimal = document.getElementById( 'decimal-0' );
let cleanButton = document.getElementById('clear_button');
let backButton = document.getElementById('back_button');
let backButton_browser = document.getElementById( 'back_button_browser' );

window.setTimeout(function() {

    $( '.alert' )
        .fadeTo( 500, 0 )
        .slideUp( 500, function() {

            $( this ).remove();

        });
    }, 4000

);
