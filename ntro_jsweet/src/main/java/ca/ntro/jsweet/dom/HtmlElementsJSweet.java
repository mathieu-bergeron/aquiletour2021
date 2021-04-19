package ca.ntro.jsweet.dom;

import def.jquery.JQuery;

import static def.jquery.Globals.$;

import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElementLambda;
import ca.ntro.web.dom.HtmlElements;

public class HtmlElementsJSweet extends HtmlElements {
	
	private JQuery jQueryElements;
	
	public HtmlElementsJSweet(JQuery jQueryElements) {
		this.jQueryElements = jQueryElements;
	}

	@Override
	public HtmlElement get(int index) {
		return new HtmlElementJSweet($(jQueryElements.get(index)));
	}

	@Override
	public int size() {
		return (int) jQueryElements.length;
	}

	@Override
	public void forEach(HtmlElementLambda lambda) {
		T.call(this);
		
		for(int i = 0; i < jQueryElements.length; i++) {
			lambda.execute(new HtmlElementJSweet($(jQueryElements.get(i))));
		}
	}

}
