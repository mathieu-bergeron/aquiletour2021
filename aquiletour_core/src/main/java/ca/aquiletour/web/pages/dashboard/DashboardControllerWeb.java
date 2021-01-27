package ca.aquiletour.web.pages.dashboard;

import java.util.Map;

import ca.aquiletour.core.pages.dashboard.DashboardController;
import ca.aquiletour.web.Path;
import ca.aquiletour.web.RequestHandlerTask;
import ca.ntro.core.Ntro;
import ca.ntro.core.mvc.view.ViewLoader;
import ca.ntro.core.system.trace.T;

public abstract class DashboardControllerWeb extends DashboardController implements RequestHandlerTask {

	@Override
	protected ViewLoader createViewLoader(String lang) {
		return Ntro.viewLoaderWeb()
		           .setHtmlUrl("/views/dashboard/structure.html")
		           .setCssUrl("/views/dashboard/style.css")
		           .setTranslationsUrl("/i18/"+lang+"/strings.json");
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
