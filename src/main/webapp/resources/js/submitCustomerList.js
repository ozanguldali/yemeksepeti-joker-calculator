let submitButton = document.getElementById('submit_button');

submitButton.onclick = function () {

    let loader = document.getElementsByClassName( "se-pre-con" )[0];

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

    if (validation) {

        main.style.pointerEvents = "none";

        loader.hidden = false;

        /*$(window).load(function() {
            // Animate loader off screen
            loader.fadeOut("slow");
        });*/

        let form = document.getElementById( 'customerInfoForm');

        let formElements = form.elements;

        for ( const input of formElements ) {

            console.log(input)

            let input_name = input.name;

            if ( input_name !== "" && input_name.includes( 'name' ) ) {

                let input_value = input.value;

                if ( input_value.includes( '&' ) ) {

                    let arr = input_value.split( '&' );
                    input_value = arr.join( 'and' );
                    input.value = input_value;

                }

            }

        }

        form.submit();

    }

    return false;

};
