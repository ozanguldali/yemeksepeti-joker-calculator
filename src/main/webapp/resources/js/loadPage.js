chrome.runtime.onMessage.addListener(function(request, sender) {
    if (request.action === "getSource") {
        injecting_htmlString = request.source;
    }
});

function onWindowLoad() {

    let message = document.getElementById('error_injecting' );

    try {

        chrome.tabs.executeScript(
            null,
            {
                file: "/webapp/resources/js/getPageSource.js"
            },
            function() {
                // If you try and inject into an extensions page or the webstore/NTP you'll get an error
                if (chrome.runtime.lastError) {

                    message.innerText = 'Lütfen Yemeksepeti Sipariş sayfasını açınız.';
                    console.log( "Execute Script Error:\t" + chrome.runtime.lastError.message );

                }
            }
        );

        injecting_DOM = new DOMParser().parseFromString( injecting_htmlString, "text/html");

        if( injecting_DOM.querySelector( "head > title" ) !== null &&
            injecting_htmlString.includes( "https://www.yemeksepeti.com/siparis/bilgi" ) &&
            injecting_DOM.querySelector( "head > title" ).textContent.toString() === "Sipariş Bilgi - Yemek Sepeti"
        ) {

            message.style.color = "green";
            message.innerText = "Bilgiler Başarıyla Yüklendi.";

            return true;

        } else {

            message.innerText = 'Yemeksepeti Sipariş sayfasında değilsiniz.';

            return false;

        }

    } catch (e) {

        console.log( e.message );
        return false;
        
    }

}

window.onload = onWindowLoad;