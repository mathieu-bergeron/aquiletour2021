var audioElm;


function _ntro_initialize_view(viewName, viewRootHtmlElement){

    const viewRootElement = viewRootHtmlElement.jQueryElement;

    if(viewName == "RootViewWeb"){
        audioElm = viewRootElement.find("#notification-sound");
    }

    const jSweet = true;

    initializeView(viewName, viewRootElement, jSweet);
}

function _ntro_notify(notificationMessage) {
    displayNotification(notificationMessage);
    playNotificationSound();
}

function playNotificationSound() {
    audioElm.play();
}

function displayNotification(notificationMessage) {
    new Notification(notificationMessage);
}


/*
function _ntro_call_modal(jQueryElement, arg){
	console.log(jQueryElement);
	$(jQueryElement).modal(arg);
}*/
