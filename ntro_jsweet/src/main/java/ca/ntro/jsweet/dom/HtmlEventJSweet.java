package ca.ntro.jsweet.dom;

import ca.ntro.web.dom.HtmlEvent;
import def.jquery.JQueryEventObject;

public class HtmlEventJSweet implements HtmlEvent {
	
	private JQueryEventObject jQueryEvent;

	public HtmlEventJSweet(JQueryEventObject jQueryEvent) {
		this.jQueryEvent = jQueryEvent;
	}

	@Override
	public void preventDefault() {
		jQueryEvent.preventDefault();
	}

}
