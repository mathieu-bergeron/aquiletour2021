package ca.ntro.web.mvc;

import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroView;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;

public abstract class NtroViewWeb implements NtroView {

	private HtmlElement rootElement;

	public abstract void initializeViewWeb(NtroContext<?,?> context);

	@Override
	public void initializeView(NtroContext<?,?> context) {
		// XXX: initializeViewWeb must be called manually
		//      to cover for the case where the view rootElement
		//      already exists
	}

	public void setRootElement(HtmlElement rootElement) {
		T.call(this);

		this.rootElement = rootElement;
	}

	public HtmlElement getRootElement() {
		T.call(this);

		return rootElement;
	}

	@Override
	public <V extends NtroView> V findSubView(Class<V> subViewClass, String subViewId) {
		// XXX: Override if needed
		return null;
	}
}
