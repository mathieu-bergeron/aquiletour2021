package ca.ntro.jdk.dom;

import org.jsoup.nodes.Element;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.web.dom.HtmlElement;
import ca.ntro.core.web.dom.HtmlEventListener;


public class HtmlElementJava implements HtmlElement {
	
	private Element element;

	public HtmlElementJava(Element element) {
		this.element = element;
	}

	@Override
	public void text(String newText) {
		element.text(newText);
	}

	@Override
	public void addEventListener(String event, HtmlEventListener listener) {
		T.call(this);
		// XXX: event listeners ignored on server
	}
	
	
	@Override
	public void appendHtml(String html) {
		T.call(this);

		element.append(html);
	}

}
