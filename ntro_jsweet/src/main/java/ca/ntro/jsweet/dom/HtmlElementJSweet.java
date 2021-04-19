package ca.ntro.jsweet.dom;

import java.util.function.BiFunction;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;
import ca.ntro.web.dom.HtmlEventListener;
import ca.ntro.web.dom.HtmlFileListener;
import def.dom.Element;
import def.dom.Event;
import def.dom.EventListener;
import def.dom.File;
import def.dom.FileList;
import def.dom.FileReader;
import def.dom.HTMLInputElement;
import def.jquery.JQuery;
import def.jquery.JQueryEventObject;
import def.js.Function;

import static def.jquery.Globals.$;

import static def.dom.Globals.document;


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

		jQueryElement.append(parseHtml(html));
	}

	@Override
	public void appendElement(HtmlElement element) {
		T.call(this);
		
		JQuery toAppend = ((HtmlElementJSweet) element).jQueryElement;
		
		toAppend.show();
		
		jQueryElement.append(toAppend);
	}

	@Override
	public void insertBefore(HtmlElement element) {
		T.call(this);

		jQueryElement.insertBefore(((HtmlElementJSweet) element).jQueryElement);
	}

	@Override
	public void insertAfter(HtmlElement element) {
		T.call(this);

		jQueryElement.insertAfter(((HtmlElementJSweet) element).jQueryElement);
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
	public void removeFromDocument() {
		T.call(this);
		
		// XXX: the DOM method does not remove event listeners
		//      (unless the element is garbage collected, which
		//       it won't be as our jQueryElement as a reference to it)
		for(int i = 0; i < jQueryElement.length; i++) {
			//Element domElement = jQueryElement.get(i);
			Element domElement = jQueryElement.get(0);
			domElement.remove();
		}

		// XXX: jQuery.remove also removes event listeners
		//jQueryElement.remove();
	}

	@Override
	public void deleteForever() {
		T.call(this);

		jQueryElement.remove();
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
		for(int i = 0; i < jQueryElement.length; i++) {
			jQueryElement.get(i).innerHTML = "";
		}
		
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
	public HtmlElement createElement(String html) {
		return new HtmlElementJSweet(parseHtml(html));
	}

	@Override
	public void readFileFromInput(HtmlFileListener listener) {
		
		HTMLInputElement input = (HTMLInputElement) jQueryElement.get(0);
		FileList fileList = input.files;
		File firstFile = fileList.$get(0);
		
		FileReader fileReader = new FileReader();
		
		fileReader.addEventListener("load", new EventListener() {
			@Override
			public void $apply(Event evt) {
				listener.onReady(fileReader.result.toString());
			}
		});

		fileReader.readAsText(firstFile);
	}
	
	public static JQuery parseHtml(String html) {
		T.call(HtmlElementJSweet.class);
		
		JQuery result = null;

		Object[] parsedHtml = $.parseHTML(html, document, false);

		if(parsedHtml.length == 1) {
			result = $(parsedHtml[0]);
		}else {
			result = $(document.createElement("span"));
			for(Object parsedElement : parsedHtml) {
				result.append($(parsedElement));
			}
		}

		return result;
	}

	@Override
	public void invoke(String functionName, Object[] args) {
		T.call(this);
		
		def.js.Object jQueryObject = (def.js.Object) jQueryElement;
		
		Function _function = jQueryObject.$get(functionName);
		
		System.out.println("_function: " + _function);
		
		_function.apply(jQueryObject, args);
	}

	@Override
	public void trigger(String event) {
		T.call(this);
		
		jQueryElement.trigger(event);
	}

	@Override
	public String text() {
		return jQueryElement.text();
	}
}
