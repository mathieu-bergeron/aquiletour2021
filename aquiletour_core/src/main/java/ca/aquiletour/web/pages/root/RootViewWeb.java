package ca.aquiletour.web.pages.root;

import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.mvc.view.NtroView;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class RootViewWeb extends NtroViewWeb implements RootView {

	@Override
	public void installSubView(NtroView view) {
		T.call(this);
		
		NtroViewWeb viewWeb = (NtroViewWeb) view;

		HtmlElement container = this.getRootElement().children("#page-container").get(0);
		
		MustNot.beNull(container);

		HtmlElement subViewElement = viewWeb.getRootElement();
		container.appendElement(subViewElement);
	}

}
