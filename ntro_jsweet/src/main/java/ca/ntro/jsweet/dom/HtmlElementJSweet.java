package ca.ntro.jsweet.dom;

import java.util.function.BiFunction;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;
import ca.ntro.web.dom.HtmlEventListener;
import def.jquery.JQuery;
import def.jquery.JQueryEventObject;

import static def.jquery.Globals.$;


public class HtmlElementJSweet extends HtmlElement {

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

		jQueryElement.append($.parseHTML(html));
	}

	@Override
	public void appendElement(HtmlElement element) {
		T.call(this);

		jQueryElement.append(((HtmlElementJSweet) element).jQueryElement);
	}

	@Override
	public HtmlElements children(String cssQuery) {
		T.call(this);

		return new HtmlElementsJSweet(jQueryElement.children(cssQuery));
	}

	@Override
	public HtmlElements find(String cssQuery) {
		T.call(this);

		return new HtmlElementsJSweet(jQueryElement.find(cssQuery));
	}

	@Override
	public void setAttribute(String name, String value) {
		T.call(this);

		jQueryElement.attr(name, value);
	}

	@Override
	public void remove() {
		T.call(this);

		jQueryElement.remove();
	}

	@Override
	public void value(String value) {
		jQueryElement.val(value);
	}

	@Override
	public String getValue() {
		return jQueryElement.val().toString();
	}
}
