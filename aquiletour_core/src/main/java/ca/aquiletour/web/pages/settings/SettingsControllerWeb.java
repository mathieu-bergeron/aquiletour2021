package ca.aquiletour.web.pages.settings;

import java.util.Map;

import ca.aquiletour.core.pages.root.RootController;
import ca.aquiletour.core.pages.settings.SettingsController;
import ca.aquiletour.web.Path;
import ca.aquiletour.web.RequestHandlerTask;
import ca.ntro.core.Ntro;
import ca.ntro.core.mvc.view.ViewLoader;
import ca.ntro.core.system.trace.T;

public abstract class SettingsControllerWeb extends SettingsController implements RequestHandlerTask {

	public SettingsControllerWeb(RootController parentController) {
		super(parentController);
	}

	@Override
	protected ViewLoader createViewLoader(String lang) {
		return Ntro.viewLoaderWeb()
		           .setHtmlUrl("/views/settings/structure.html")
		           .setCssUrl("/views/settings/style.css")
		           .setTranslationsUrl("/i18/"+lang+"/strings.json")
		           .setTargetClass(SettingsViewWeb.class);
	}

	@Override
	public void initialRequest(Path path, 
			                   Map<String, String[]> parameters, 
			                   String authToken) {
		T.call(this);
		
	}

	@Override
	public void newRequest(Path oldPath, 
			               Path path, 
			               Map<String, String[]> oldParameters, 
			               Map<String, String[]> parameters, 
			               String authToken) {

		T.call(this);
		
	}

}
