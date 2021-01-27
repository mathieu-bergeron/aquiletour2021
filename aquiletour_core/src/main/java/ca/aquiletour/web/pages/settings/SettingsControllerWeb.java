package ca.aquiletour.web.pages.settings;

import ca.aquiletour.core.pages.settings.SettingsController;
import ca.ntro.core.Ntro;
import ca.ntro.core.mvc.view.ViewLoader;

public abstract class SettingsControllerWeb extends SettingsController {

	@Override
	protected ViewLoader loadView(String lang) {
		return Ntro.viewLoaderWeb()
		           .setHtmlUrl("/views/settings/structure.html")
		           .setCssUrl("/views/settings/style.css")
		           .setTranslationsUrl("/i18/"+lang+"/strings.json");
	}

}
