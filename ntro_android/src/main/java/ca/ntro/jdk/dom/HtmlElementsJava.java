package ca.ntro.jdk.dom;

import org.jsoup.select.Elements;

import ca.ntro.core.web.dom.HtmlElement;
import ca.ntro.core.web.dom.HtmlElements;

public class HtmlElementsJava implements HtmlElements {
	
	private Elements elements;
	
	public HtmlElementsJava(Elements elements) {
		this.elements = elements;
	}
	
	@Override
	public HtmlElement get(int index) {
		return new HtmlElementJava(elements.get(index)); 
	}

}
