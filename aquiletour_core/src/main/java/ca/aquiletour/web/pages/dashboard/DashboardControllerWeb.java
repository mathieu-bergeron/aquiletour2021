package ca.aquiletour.web.pages.dashboard;

import ca.aquiletour.core.pages.dashboard.DashboardController;
import ca.ntro.core.Ntro;
import ca.ntro.core.mvc.view.ViewLoader;

public abstract class DashboardControllerWeb extends DashboardController {

	@Override
	protected ViewLoader loadView(String lang) {
		return Ntro.viewLoaderWeb()
		           .setHtmlUrl("/views/dashboard/structure.html")
		           .setCssUrl("/views/dashboard/style.css")
		           .setTranslationsUrl("/i18/"+lang+"/strings.json");
	}

}
