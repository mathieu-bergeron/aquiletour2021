package ca.aquiletour.web.widgets;

import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;

public class BootstrapAlert {

	private HtmlElement container;
	private HtmlElement messageSpan;
	
	public BootstrapAlert(HtmlElement container) {
		T.call(this);

		this.container = container;
		messageSpan = this.container.find(".message-text").get(0);
		
		MustNot.beNull(messageSpan);
	}

	public void hide() {
		T.call(this);
		
		container.hide();
	}

	public void displayMessage(String message) {
		T.call(this);
		
		container.show();
		messageSpan.text(message);
	}

}
