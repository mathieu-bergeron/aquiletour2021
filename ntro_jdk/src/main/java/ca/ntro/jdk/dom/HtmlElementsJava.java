package ca.ntro.jdk.dom;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;

public class HtmlElementsJava implements HtmlElements {
	
	private Elements elements;
	
	public HtmlElementsJava(Elements elements) {
		this.elements = elements;
	}
	
	@Override
	public HtmlElement get(int index) {
		Element element = null;

		if(index < elements.size()) {
			element = elements.get(0);
		}

		return new HtmlElementJava(element);
	}

}
