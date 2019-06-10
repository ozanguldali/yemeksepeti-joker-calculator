let submitButton = document.getElementById('submit_button');
let statusDisplay = '';

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

        // addBookmark();

        window.location.href = "jokerResult.html";

        document.getElementById( 'customerInfoForm').submit();

        // postForm();

    }

};



/*function addBookmark() {
    // Cancel the form submit
    event.preventDefault();

    // The URL to POST our data to
    let postUrl = document.getElementById('customerInfoForm').getAttribute( 'action' );

    // Set up an asynchronous AJAX POST request
    let xhr = new XMLHttpRequest();
    xhr.open('POST', postUrl, true);

    let table = document.getElementById('customer_list');

    let rowCount = table.rows.length;

    let params = '';

    for ( let i = 0; i < ( rowCount - 1 ) * 2; i++ ) {

        let key, value,  nameKey, nameValue, costKey, costValue;

        if ( i % 2 === 0 ) {

            nameKey = table.getElementsByTagName( 'td' )[ i ].getElementsByTagName( 'input' )[0].getAttribute('name');
            nameValue = table.getElementsByTagName( 'td' )[ i ].textContent;

        } else {

            costKey = table.getElementsByTagName( 'td' )[ i ].getElementsByTagName( 'input' )[0].getAttribute('name');
            costValue = table.getElementsByTagName( 'td' )[ i ].textContent;

        }

        key = table.getElementsByTagName( 'td' )[ i ].getElementsByTagName( 'input' )[0].getAttribute('name');
        value = table.getElementsByTagName( 'td' )[ i ].textContent;

        params = params + key + '=' + value + '&';

    }

    if ( params.endsWith('&') ) {
        params = params.substring( 0, params.length - 2 );
    }


    // Replace any instances of the URLEncoded space char with +
    params = params.replace(/%20/g, '+');

    // Set correct header for form data
    let formContentType = 'application/x-www-form-urlencoded';
    xhr.setRequestHeader('Content-type', formContentType);

    // Handle request state change events
    xhr.onreadystatechange = function() {
        // If the request completed
        if (xhr.readyState === 4) {
            statusDisplay.innerHTML = '';
            if (xhr.status === 200) {
                // If it was a success, close the popup after a short delay
                statusDisplay.innerHTML = 'Saved!';
                window.setTimeout(window.close, 1000);
            } else {
                // Show what went wrong
                statusDisplay.innerHTML = 'Error saving: ' + xhr.statusText;
            }
        }
    };

    // Send the request and set status
    xhr.send(params);

    statusDisplay.innerHTML = 'Saving...';
}

window.addEventListener('load', function(evt) {
    // Cache a reference to the status display SPAN
    statusDisplay = document.getElementById('status-display');
    // Handle the bookmark form submit event with our addBookmark function
    document.getElementById('customerInfoForm')
        .addEventListener('button', addBookmark);
    // Get the event page
    chrome.runtime.getBackgroundPage(function(eventPage) {
        // Call the getPageInfo function in the event page, passing in
        // our onPageDetailsReceived function as the callback. This
        // injects content.js into the current tab's HTML
        getPageDetails(onPageDetailsReceived);
    });
});*/

