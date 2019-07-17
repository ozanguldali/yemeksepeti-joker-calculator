chrome.runtime.onMessage.addListener(function(request, sender, sendResponse) {
    // If the request asks for the DOM content...
    if (request.method && (request.method === "getDOM")) {
        // ...send back the content of the <html> element
        // (Note: You can't send back the current '#document',
        //  because it is recognised as a circular object and
        //  cannot be converted to a JSON string.)
        let html = document.all[0];
        sendResponse({ "htmlContent": html.innerHTML });
    }
});

function getPageDetails(callback) {
    // Inject the content script into the current page
    chrome.tabs.executeScript(null, { file: 'content.js' });
    // When a message is received from the content script
    chrome.runtime.onMessage.addListener(function(message) {
        // Call the callback function
        callback(message);
    });
}
