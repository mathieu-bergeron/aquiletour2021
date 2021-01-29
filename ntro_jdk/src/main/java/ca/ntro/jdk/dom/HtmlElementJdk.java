package ca.ntro.jdk.dom;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;
import ca.ntro.web.dom.HtmlEventListener;


public class HtmlElementJdk implements HtmlElement {
	
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
		
		T.values(jsoupElement.toString(), otherElement.jsoupElement.toString());
		
		jsoupElement.appendChild(otherElement.jsoupElement);

		T.values(jsoupElement.toString());
		
	}

	@Override
	public HtmlElements children(String cssQuery) {
		T.call(this);
		
		Elements elements = jsoupElement.select(cssQuery);

		return new HtmlElementsJdk(elements);
	}
	
	@Override
	public String toString() {
		return jsoupElement.html();
	}

	@Override
	public void setAttribute(String name, String value) {
		T.call(this);
		
		jsoupElement.attr(name, value);
	}
}