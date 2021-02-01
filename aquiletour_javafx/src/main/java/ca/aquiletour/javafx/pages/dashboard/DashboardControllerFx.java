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

		return new ViewLoaderFx().setFxmlUrl("/views/dashboard/structure.xml")
				                 .setCssUrl("/views/dashboard/style.css")
				                 .setTranslationsName("i18n.strings");
	}

	@Override
	protected ViewLoader createCourseSummaryViewLoader(String lang) {
		T.call(this);

		return new ViewLoaderFx().setFxmlUrl("/views/course_summary/structure.xml")
				                 .setCssUrl("/views/course_summary/style.css")
				                 .setTranslationsName("i18n.strings");
	}

}
