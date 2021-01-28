package ca.ntro.jdk.dom;

import org.jsoup.nodes.Element;

import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlEventListener;


public class HtmlElementJava implements HtmlElement {
	
	private Element jsoupElement;

	public HtmlElementJava(Element jsoupElement) {
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
		
		jsoupElement.appendChild(((HtmlElementJava) element).jsoupElement);
	}
}
