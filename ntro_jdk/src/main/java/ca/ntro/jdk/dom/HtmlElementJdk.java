package ca.ntro.jdk.dom;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeFilter;

import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
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

		styleString = styleString.replace("display:none;", "");

		jsoupElement.attr("style", styleString);
	}

	@Override
	public void hide() {
		String styleString = getStyleString();
		
		if(!styleString.contains("display:none;")){
			styleString += "display:none;";
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

		if(childNodes.size() == 1) {
			result = (Element) childNodes.get(0);
		}else {
			result = new Element("span");
			for(Node childNode : childNodes) {
				result.appendChild(childNode.clone());
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

}
