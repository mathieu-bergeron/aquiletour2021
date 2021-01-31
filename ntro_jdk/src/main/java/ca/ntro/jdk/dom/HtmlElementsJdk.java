package ca.ntro.jdk.dom;

import org.jsoup.select.Elements;

import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;

public class HtmlElementsJdk implements HtmlElements {
	
	private Elements elements;
	
	public HtmlElementsJdk(Elements elements) {
		this.elements = elements;
	}
	
	@Override
	public HtmlElement get(int index) {
		HtmlElement element = null;

		if(index < elements.size()) {
			element = new HtmlElementJdk(elements.get(0));
		}

		return element;
	}

}
