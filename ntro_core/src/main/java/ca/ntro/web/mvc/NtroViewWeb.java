package ca.ntro.web.mvc;

import ca.ntro.core.mvc.NtroView;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;

public abstract class NtroViewWeb implements NtroView {

	private HtmlElement rootElement;

	public void initializeWebView() {

	}

	@Override
	public void initialize() {
		// TODO - DOM processing
	}

	protected void setRootElement(HtmlElement rootElement) {
		T.call(this);

		this.rootElement = rootElement;
	}

	public HtmlElement getRootElement() {
		T.call(this);

		return rootElement;
	}
}
