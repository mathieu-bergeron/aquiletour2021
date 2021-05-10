package ca.ntro.jdk.dom;

import org.jsoup.select.Elements;

import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElementLambda;
import ca.ntro.web.dom.HtmlElements;

public class HtmlElementsJdk extends HtmlElements {
	
	private Elements elements;
	
	public HtmlElementsJdk(Elements elements) {
		this.elements = elements;
	}
	
	@Override
	public HtmlElement get(int index) {
		HtmlElement element = null;

		if(index < elements.size()) {
			element = new HtmlElementJdk(elements.get(index));
		}

		return element;
	}

	@Override
	public int size() {
		T.call(this);
		
		return elements.size();
	}

	@Override
	public void forEach(HtmlElementLambda lambda) {
		T.call(this);

		elements.forEach(e -> lambda.execute(new HtmlElementJdk(e)));
	}

}
