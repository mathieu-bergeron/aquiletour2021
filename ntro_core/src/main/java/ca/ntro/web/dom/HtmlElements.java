package ca.ntro.web.dom;

import ca.ntro.core.system.trace.T;

public abstract class HtmlElements {
	
	public abstract HtmlElement get(int index);
	public abstract int size();
	
	public abstract void forEach(HtmlElementLambda lambda);

	public void appendToAttribute(String name, String toAppend) {
		T.call(this);

		this.forEach(e -> {
			String value = e.getAttribute(name);
			value += toAppend;
			e.setAttribute(name, value);
		});
	}
}
