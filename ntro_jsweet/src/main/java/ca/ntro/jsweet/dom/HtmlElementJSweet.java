package ca.ntro.jsweet.dom;

import java.util.function.BiFunction;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;
import ca.ntro.web.dom.HtmlEventListener;
import def.dom.Element;
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
	public void removeListeners() {
		T.call(this);
		
		jQueryElement.off();
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
	public void insertBefore(HtmlElement element) {
		T.call(this);

		jQueryElement.before(((HtmlElementJSweet) element).jQueryElement);
	}

	@Override
	public void insertAfter(HtmlElement element) {
		T.call(this);

		jQueryElement.after(((HtmlElementJSweet) element).jQueryElement);
	}

	@Override
	public HtmlElements children(String cssQuery) {
		T.call(this);

		return new HtmlElementsJSweet(jQueryElement.children(cssQuery));
	}

	@Override
	public HtmlElements find(String cssQuery) {
		T.call(this);
		
		JQuery foundElements = jQueryElement.find(cssQuery);
		
		HtmlElementsJSweet result = null;
		
		if(foundElements.length > 0) {
			result = new HtmlElementsJSweet(foundElements);
		}
		
		return result;
	}

	@Override
	public String id() {
		T.call(this);

		return this.getAttribute("id");
	}

	@Override
	public String getAttribute(String name) {
		T.call(this);

		return jQueryElement.attr(name);
	}

	@Override
	public void setAttribute(String name, String value) {
		T.call(this);

		jQueryElement.attr(name, value);
	}

	@Override
	public void remove() {
		T.call(this);
		
		// XXX: the DOM method does not remove event listeners
		//      (unless the element is garbage collected, which
		//       it won't be as our jQueryElement as a reference to it)
		Element domElement = jQueryElement.get(0);
		domElement.remove();

		// XXX: jQuery.remove also removes event listeners
		//jQueryElement.remove();
	}

	@Override
	public void value(String value) {
		jQueryElement.val(value);
	}

	@Override
	public String value() {
		return jQueryElement.val().toString();
	}

	@Override
	public void empty() {
		jQueryElement.empty();
	}

	@Override
	public void html(String htmlString) {

		// XXX: this would remove listeners
		//jQueryElement.html("");
		jQueryElement.get(0).innerHTML = "";

		if(htmlString != null) {

			Object[] elementsToAppend = $.parseHTML(htmlString);

			if(elementsToAppend != null) {
				for(Object newElement : $.parseHTML(htmlString)) {
					jQueryElement.append(newElement);
				}
			}
		}
	}

	@Override
	public String html() {
		return jQueryElement.html();
	}

	@Override
	public void show() {
		jQueryElement.show();
	}

	@Override
	public void hide() {
		jQueryElement.hide();
	}

	@Override
	public HtmlElement newElement(String html) {
		return new HtmlElementJSweet($(html));
	}

}
