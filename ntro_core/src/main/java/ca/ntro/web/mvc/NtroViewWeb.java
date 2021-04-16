package ca.ntro.web.mvc;

import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroView;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;

public abstract class NtroViewWeb implements NtroView {

	private HtmlElement rootElement;

	public abstract void initializeViewWeb(NtroContext<?> context);

	@Override
	public void initializeView(NtroContext<?> context) {
		initializeViewWeb(context);

		// Process DOM interactivity
		if (this.getRootElement() != null) {
			Ntro.domProcessor().runOnElement(this.getRootElement());
		}
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
