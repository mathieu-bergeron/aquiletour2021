package ca.ntro.jsweet.dom;

import java.util.function.BiFunction;

import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlEventListener;
import def.jquery.JQuery;
import def.jquery.JQueryEventObject;

public class HtmlElementJSweet implements HtmlElement {
	
	private JQuery jQueryElement;
	
	public HtmlElementJSweet(JQuery jQueryElement) {
		this.jQueryElement = jQueryElement;
	}

	@Override
	public void text(String newText) {
		jQueryElement.text(newText);
	}

	@Override
	public void addEventListener(String event, HtmlEventListener listener) {
		T.call(this);

		jQueryElement.on(event, new BiFunction<JQueryEventObject, Object, Object>() {

			@Override
			public Object apply(JQueryEventObject t, Object u) {
				T.call(this);
				
				// FIXME: only for <a>?
				t.preventDefault();
				
				listener.onEvent();
				
				return null;
			}
		});
	}

	@Override
	public void appendHtml(String html) {
		T.call(this);
		
		// FIXME: parse HTML first
		jQueryElement.html(html);
	}
}
