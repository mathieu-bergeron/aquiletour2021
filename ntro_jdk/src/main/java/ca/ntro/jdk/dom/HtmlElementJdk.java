package ca.ntro.jdk.dom;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;
import ca.ntro.web.dom.HtmlEventListener;


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

		jsoupElement.before(otherElement.jsoupElement);
	}

	@Override
	public void insertAfter(HtmlElement element) {
		T.call(this);

		MustNot.beNull(jsoupElement);

		HtmlElementJdk otherElement = (HtmlElementJdk) element;

		MustNot.beNull(otherElement);
		MustNot.beNull(otherElement.jsoupElement);

		jsoupElement.after(otherElement.jsoupElement);
	}

	@Override
	public HtmlElements children(String cssQuery) {
		T.call(this);

		Elements elements = jsoupElement.children().select(cssQuery);

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
	public void remove() {
		T.call(this);

		jsoupElement.remove();
	}

	@Override
	public void value(String value) {
		jsoupElement.val(value);
	}

	@Override
	public String getValue() {
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

}
