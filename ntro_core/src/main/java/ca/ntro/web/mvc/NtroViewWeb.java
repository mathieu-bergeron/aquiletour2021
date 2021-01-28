package ca.ntro.web.mvc;

import ca.ntro.core.mvc.view.NtroView;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;

public class NtroViewWeb implements NtroView {
	
	private HtmlElement rootElement;
	
	public void setRootElement(HtmlElement rootElement) {
		T.call(this);
		
		this.rootElement = rootElement;
	}

}
