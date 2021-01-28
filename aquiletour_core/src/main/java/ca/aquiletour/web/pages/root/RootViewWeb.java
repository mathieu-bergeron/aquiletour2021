package ca.aquiletour.web.pages.root;

import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.mvc.view.NtroView;
import ca.ntro.web.mvc.NtroViewWeb;

public class RootViewWeb extends NtroViewWeb implements RootView {

	@Override
	public void installSubView(NtroView view) {
		
		NtroViewWeb viewWeb = (NtroViewWeb) view;

		// FIXME: not quite. We must select the page-container
		this.getRootElement().appendElement(viewWeb.getRootElement());
	}

}
