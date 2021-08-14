package ca.ntro.jdk.dom;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.AnimationListener;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;
import ca.ntro.web.dom.HtmlEventListener;
import ca.ntro.web.dom.HtmlFileListener;

public class HtmlElementJdk extends HtmlElement {

	private Element jsoupElement;

	public HtmlElementJdk(Element jsoupElement) {
		this.jsoupElement = jsoupElement;
	}

	@Override
	public void text(String newText) {
		jsoupElement.text(newText);
	}

	@Override
	public String text() {
		return jsoupElement.text();
	}

	@Override
	public void removeListeners() {
		T.call(this);
		// XXX: event listeners ignored on server
	}

	@Override
	public void addEventListener(String event, HtmlEventListener listener) {
		T.call(this);
		// XXX: event listeners ignored on server
	}


	@Override
	public void appendHtml(String html) {
		T.call(this);

		jsoupElement.append(html);
	}

	@Override
	public void appendElement(HtmlElement element) {
		T.call(this);

		MustNot.beNull(jsoupElement);

		HtmlElementJdk otherElement = (HtmlElementJdk) element;

		MustNot.beNull(otherElement);
		MustNot.beNull(otherElement.jsoupElement);

		jsoupElement.appendChild(otherElement.jsoupElement);
	}

	@Override
	public void insertBefore(HtmlElement element) {
		T.call(this);

		MustNot.beNull(jsoupElement);

		HtmlElementJdk otherElement = (HtmlElementJdk) element;

		MustNot.beNull(otherElement);
		MustNot.beNull(otherElement.jsoupElement);

		otherElement.jsoupElement.before(jsoupElement);
	}

	@Override
	public void insertAfter(HtmlElement element) {
		T.call(this);

		MustNot.beNull(jsoupElement);

		HtmlElementJdk otherElement = (HtmlElementJdk) element;

		MustNot.beNull(otherElement);
		MustNot.beNull(otherElement.jsoupElement);

		otherElement.jsoupElement.after(jsoupElement);
	}

	@Override
	public HtmlElements children(String cssQuery) {
		T.call(this);

		Elements elements = jsoupElement.select(">" + cssQuery);

		return new HtmlElementsJdk(elements);
	}

	@Override
	public HtmlElements find(String cssQuery) {
		T.call(this);

		Elements elements = jsoupElement.select(cssQuery);

		return new HtmlElementsJdk(elements);
	}

	@Override
	public String toString() {
		return jsoupElement.html();
	}

	@Override
	public String id() {
		T.call(this);

		return jsoupElement.id();
	}

	@Override
	public String getAttribute(String name) {
		T.call(this);

		return jsoupElement.attr(name);
	}

	@Override
	public void setAttribute(String name, String value) {
		T.call(this);

		setAttributeNoSideEffect(name, value);
	}

	@Override
	public void setAttributeNoSideEffect(String name, String value) {
		T.call(this);

		jsoupElement.attr(name, value);
	}

	@Override
	public void removeFromDocument() {
		T.call(this);

		jsoupElement.remove();
	}

	@Override
	public void deleteForever() {
		T.call(this);

		jsoupElement.remove();
	}

	@Override
	public void value(String value) {
		jsoupElement.val(value);
	}

	@Override
	public String value() {
		return jsoupElement.val();
	}

	@Override
	public void empty() {
		jsoupElement.empty();
	}

	@Override
	public void html(String htmlString) {
		jsoupElement.html(htmlString);
	}

	@Override
	public String html() {
		return jsoupElement.html();
	}

	@Override
	public void show() {
		String styleString = getStyleString();

		styleString = styleString.replace("display:none !important;", "");

		jsoupElement.attr("style", styleString);
	}

	@Override
	public void hide() {
		String styleString = getStyleString();
		
		if(!styleString.contains("display:none !important;")){
			styleString += " display:none !important;";
		}
		
		jsoupElement.attr("style", styleString);
	}

	private String getStyleString() {
		String styleString = jsoupElement.attr("style");
		if(styleString == null) {
			styleString = "";
		}
		return styleString;
	}

	@Override
	public HtmlElement createElement(String html) {
		return new HtmlElementJdk(parseHtml(html));
	}

	public static Element parseHtml(String html) {
		T.call(HtmlElementJdk.class);

		Element result = null;
		
		Document jsoupDocument = Jsoup.parse(html, StandardCharsets.UTF_8.name());

		List<Node> childNodes = jsoupDocument.body().childNodes();
		
		List<Element> elements = new ArrayList<>();
		
		for(Node childNode : childNodes) {
			if(childNode instanceof Element) {
				elements.add((Element) childNode);
			}
		}

		if(elements.size() == 1) {
			result = elements.get(0).clone();
		}else {
			result = new Element("div");
			for(Element childElement : elements) {
				result.appendChild(childElement.clone());
			}
		}

		return result;
	}

	@Override
	public void readFileFromInput(HtmlFileListener listener) {
		// XXX: not supported on the server
	}

	@Override
	public void invoke(String string, Object[] objects) {
		// XXX: not supported on the server
	}

	@Override
	public void trigger(String event) {
		// XXX: not supported on the server
	}

	@Override
	public void animate(Map<String, Object> properties, long duration, AnimationListener listener) {
		listener.animationFinished();
	}

	@Override
	public void css(String property, String value) {
		// XXX: not supported on the server
	}

	@Override
	public void css(String property, double value) {
		// XXX: not supported on the server
	}

	@Override
	public HtmlElement createTag(String tagName) {
		return new HtmlElementJdk(new Element(Tag.valueOf(tagName),""));
	}

	@Override
	public void removeAttribute(String name) {
		T.call(this);
		
		jsoupElement.removeAttr(name);
	}

	@Override
	public void addClass(String styleClass) {
		T.call(this);
		
		jsoupElement.addClass(styleClass);
	}

	@Override
	public void removeClass(String styleClass) {
		T.call(this);

		jsoupElement.removeClass(styleClass);
	}

	@Override
	public HtmlElement clone() {
		T.call(this);
		
		return new HtmlElementJdk(jsoupElement.clone());
	}

	@Override
	public void initializeJs(String viewName) {
		T.call(this);
		// XXX: not supported server-side
	}

	@Override
	public HtmlElements parents(String cssQuery) {
		T.call(this);

		return new HtmlElementsJdk(jsoupElement.parents().select(cssQuery));
	}

	@Override
	public void installFormAutoSubmit() {
		T.call(this);
		// XXX: not supported on server-side
	}

}
