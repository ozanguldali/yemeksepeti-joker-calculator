chrome.runtime.onMessage.addListener(function(request, sender) {
    if (request.action === "getSource") {
        injecting_htmlString = request.source;
    }
});

function onWindowLoad() {

    let message = document.getElementById('error_injecting' );

    chrome.tabs.executeScript(null, {
        file: "/webapp/resources/js/getPageSource.js"
    }, function() {
        // If you try and inject into an extensions page or the webstore/NTP you'll get an error
        if (chrome.runtime.lastError) {

            message.innerText = 'Lütfen Yemeksepeti Sipariş sayfasını açınız.';

        }
    });

    injecting_DOM = new DOMParser().parseFromString( injecting_htmlString, "text/html");

    if( injecting_DOM.querySelector( "head > link:nth-child(56)" ) !== null &&
        injecting_DOM.querySelector( "head > link:nth-child(56)" ).href.toString() === "https://www.yemeksepeti.com/siparis/bilgi" ) {

        message.style.color = "green";
        message.innerText = "Bilgiler Başarıyla Alındı.";

        return true;

    } else {

        message.innerText = 'Yemeksepeti Sipariş sayfasında değilsiniz.';

        return false;

    }

}

window.onload = onWindowLoad;