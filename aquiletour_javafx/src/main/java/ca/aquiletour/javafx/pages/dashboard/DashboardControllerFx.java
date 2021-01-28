package ca.aquiletour.javafx.pages.dashboard;

import ca.aquiletour.core.pages.dashboard.DashboardController;
import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.mvc.view.ViewLoader;
import ca.ntro.core.system.trace.T;
import ca.ntro.javafx.ViewLoaderFx;

public class DashboardControllerFx extends DashboardController {

	public DashboardControllerFx(RootController parentController) {
		super(parentController);
		T.call(this);
	}

	@Override
	protected ViewLoader createViewLoader(String lang) {
		T.call(this);

		return new ViewLoaderFx().setFxmlUrl("/views/settings/structure.xml")
				                 .setCssUrl("/views/settings/style.css")
				                 .setTranslationsName("i18n.strings");
	}

}
