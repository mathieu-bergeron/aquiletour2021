package ca.ntro.jsweet.dom;

import def.jquery.JQuery;

import static def.jquery.Globals.$;

import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;

public class HtmlElementsJSweet implements HtmlElements {
	
	private JQuery jQueryElements;
	
	public HtmlElementsJSweet(JQuery jQueryElements) {
		this.jQueryElements = jQueryElements;
	}

	@Override
	public HtmlElement get(int index) {
		return new HtmlElementJSweet($(jQueryElements.get(index)));
	}

}
