function _ntro_initialize_view(viewName, viewRootHtmlElement){
    const viewRootElement = viewRootHtmlElement.jQueryElement;

    const jSweet = true;

    initializeView(viewName, viewRootElement, jSweet);
}

/*
function _ntro_call_modal(jQueryElement, arg){
	console.log(jQueryElement);
	$(jQueryElement).modal(arg);
}*/
