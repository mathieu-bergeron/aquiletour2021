package ca.ntro.jsweet.dom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;


import ca.ntro.core.Path;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.AnimationListener;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;
import ca.ntro.web.dom.HtmlEvent;
import ca.ntro.web.dom.HtmlEventListener;
import ca.ntro.web.dom.HtmlFileListener;
import ca.ntro.web.dom.LinkListener;
import ca.ntro.web.dom.SubmitListener;
import def.dom.Element;
import def.dom.Event;
import def.dom.EventListener;
import def.dom.File;
import def.dom.FileList;
import def.dom.FileReader;
import def.dom.HTMLInputElement;
import def.es6.Globals;
import def.jquery.JQuery;
import def.jquery.JQueryEventObject;
import def.js.Function;

import static jsweet.util.Lang.function;

import static def.jquery.Globals.$;

import static def.dom.Globals.document;

import static def.dom.Globals.history;

import static def.dom.Globals.window;

import static def.js.Globals.undefined;

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

				listener.onEvent(new HtmlEventJSweet(t));

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

		appendElement(new HtmlElementJSweet(parseHtml(html)));
	}

	@Override
	public void appendElement(HtmlElement element) {
		T.call(this);
		
		JQuery toAppend = ((HtmlElementJSweet) element).jQueryElement;
		
		toAppend.show();
		
		jQueryElement.append(toAppend);

		//installFormAutosubmit(toAppend);
	}

	@Override
	public void initializeJs(String viewName) {
		T.call(this);

		Globals._ntro_initialize_view(viewName, this);
		
		/*
		installFormAutosubmit(jQueryElement);
		installLinksClickEvents(jQueryElement);
		*/
	}

	private void installAllLinkHandlers(JQuery rootElement) {
		T.call(this);

		JQuery anchors = rootElement.find("a");
		anchors.each(new BiFunction<Integer, Element, Object>() {

			@Override
			public Object apply(Integer t, Element a) {
				
				HtmlElementJSweet anchor = new HtmlElementJSweet($(a));
				
				installLinkHandler(anchor, null);

				return null;
			}

		});
	}

	private void installLinkHandler(HtmlElementJSweet anchor, LinkListener listener) {
		T.call(this);

		String href = anchor.getAttribute("href");
		
		if(href != null) {

			installLinkHandler(anchor, href, listener);
		}
	}

	private void installAllFormSubmitHandlers(JQuery rootElement) {
		T.call(this);

		JQuery forms = rootElement.find("form");
		forms.each(new BiFunction<Integer, Element, Object>() {
			@Override
			public Object apply(Integer t, Element formElement) {
				T.call(this);

				JQuery form = $(formElement);

				installFormSubmitHandler(form, null);

				return null;
			}

		});
	}

	private void installFormSubmitHandler(JQuery form, SubmitListener listener) {
		T.call(this);

		form.off();
		form.on("submit", new BiFunction<JQueryEventObject, Object, Object>() {
			@Override
			public Object apply(JQueryEventObject t, Object u) {
				T.call(this);

				t.preventDefault();
				
				String href = form.attr("action");
				if(href == null || href.isEmpty()) {
					href = window.location.pathname;
				}else if(!href.startsWith("/")) {
					href = window.location.pathname + href;
				}
				
				Map<String, String[]> parameters = new HashMap<>();
				JQuery formInputs = form.find("[name]");
				putInputParameters(parameters, formInputs);

				String formId = form.attr("id");
				if(formId != null && !formId.isEmpty()) {
					JQuery otherInputs = $(document).find("[form='"+formId+"']");
					putInputParameters(parameters, otherInputs);
					
				}else {
					formId = "unknownForm";
				}

				history.pushState(null, formId, href);
				
				Ntro.router().sendMessagesFor(Ntro.context(), Path.fromRawPath(href), parameters);
				
				if(listener != null) {
					listener.onFormSubmitted();
				}

				return null;
			}
		});
	}

	private void putInputParameters(Map<String, String[]> parameters, JQuery formInputs) {
		T.call(this);

		for(int i = 0; i < formInputs.length; i++) {
			 JQuery formInput = $(formInputs.get(i));
			 putInputParameter(formInput, parameters);
		}
	}

	private void putInputParameter(JQuery formInput, Map<String, String[]> parameters) {
		T.call(this);

		String name = formInput.attr("name");
		String value = formInput.val().toString();
		if(value != null) {
			parameters.put(name, new String[] {value});
		}
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
		
		JQuery foundElements = jQueryElement.find(cssQuery).addBack(cssQuery);

		return new HtmlElementsJSweet(foundElements);
	}

	@Override
	public String id() {
		T.call(this);

		return this.getAttribute("id");
	}

	@Override
	public String getAttribute(String name) {
		T.call(this);

		String value = jQueryElement.attr(name);
		if(value == undefined) {
			value = null;
		}
		return value;
	}

	@Override
	public void setAttributeNoSideEffect(String name, String value) {
		T.call(this);
		
		jQueryElement.attr(name, value);
	}

	@Override
	public void setAttribute(String name, String value) {
		T.call(this);

		setAttributeNoSideEffect(name, value);
		
		/*
		if(name.equals("href")) {
			installHrefClickEvent(this, value);
		}*/
	}

	private void installLinkHandler(HtmlElement anchor, String href, LinkListener listener) {
		T.call(this);

		anchor.removeListeners();
		anchor.addEventListener("click", new HtmlEventListener() {
			@Override
			public void onEvent(HtmlEvent e) {
				T.call(this);
				
				e.preventDefault();
				
				String fullHref = href;
				
				if(href.isEmpty() || href.startsWith("?") || href.startsWith("#")) {
					
					fullHref = window.location.pathname + href;
				}

				history.pushState(null, jQueryElement.text(), fullHref);

				Ntro.router().sendMessagesFor(Ntro.context(), fullHref);
				
				if(listener != null) {
					listener.onLinkFollowed();
				}
			}
		});
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

		// XXX: jQueryElement.html("") would remove listeners
		for(int i = 0; i < jQueryElement.length; i++) {
			jQueryElement.get(i).innerHTML = "";
		}

		if(htmlString != null) {
			appendElement(new HtmlElementJSweet(parseHtml(htmlString)));
		}
	}

	@Override
	public String html() {
		return jQueryElement.html();
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
		
		JQuery result = $(document.createElement("div"));

		// FIXME: this will remove duplicate ids
		//        we cannot use ids in partials
		Object[] nodes = $.parseHTML(html, document, false);
		
		List<Element> elements = new ArrayList<>();
		for(Object nodeObject : nodes) {
			if(nodeObject instanceof Element) {
				Element element = (Element) nodeObject;
				if(element.nodeType == 1 ) {
					elements.add(element);
				}
			}
		}
		
		if(elements.size() == 1) {

			result = $(nodes[0]);

		}else if(elements.size() > 0) {

			for(Element element  : elements) {
				result.append($(element));
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

	@Override
	public void animate(Map<String, Object> properties, long duration, AnimationListener listener) {
		T.call(this);
		
		jQueryElement.animate(properties, 
				              duration, 
				              function(()-> listener.animationFinished()));
	}

	@Override
	public void css(String property, String value) {
		T.call(this);
		
		jQueryElement.css(property, value);
	}

	@Override
	public void css(String property, double value) {
		T.call(this);
		
		jQueryElement.css(property, value);
	}

	@Override
	public HtmlElement createTag(String tagName) {
		return new HtmlElementJSweet($(tagName));
	}

	@Override
	public void removeAttribute(String name) {
		jQueryElement.removeAttr(name);
	}

	@Override
	public void addClass(String styleClass) {
		T.call(this);
		
		jQueryElement.addClass(styleClass);
	}

	@Override
	public void removeClass(String styleClass) {
		T.call(this);

		jQueryElement.removeClass(styleClass);
	}

	@Override
	public HtmlElement clone() {
		T.call(this);

		return new HtmlElementJSweet(jQueryElement.clone(false));
	}

	@Override
	public HtmlElements parents(String cssQuery) {
		T.call(this);

		return new HtmlElementsJSweet(jQueryElement.parent(cssQuery));
	}

	@Override
	public void installFormSubmitHandler() {
		T.call(this);

		installFormSubmitHandler(jQueryElement, null);
	}

	@Override
	public void installFormSubmitHandler(SubmitListener listener) {
		T.call(this);

		installFormSubmitHandler(jQueryElement, listener);
	}

	@Override
	public void removeFormSubitHandler() {
		T.call(this);

		jQueryElement.off();
	}

	@Override
	public void click() {
		T.call(this);
		
		jQueryElement.click();
	}

	@Override
	public void installLinkHandler() {
		T.call(this);

		installLinkHandler(this, null);
	}

	@Override
	public void installLinkHandler(LinkListener listener) {
		T.call(this);
		
		installLinkHandler(this, listener);
	}

	@Override
	public void removeLinkHandler() {
		T.call(this);
		
		jQueryElement.off();
	}

	@Override
	public void displayNotification(String notificationMessage) {
		T.call(this);

		Globals._ntro_notify(notificationMessage);
	}

	/*
	@Override
	public void modal(String arg) {
		T.call(this);

		Globals._ntro_call_modal(jQueryElement, arg);
	}*/

}
