package ca.aquiletour.web.pages.dashboard;

import java.util.Map;

import ca.aquiletour.core.pages.dashboard.DashboardController;
import ca.aquiletour.core.pages.dashboard.messages.AddCourseMessage;
import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.Ntro;
import ca.ntro.core.mvc.view.ViewLoader;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.MessageFactory;
import ca.ntro.web.Path;
import ca.ntro.web.RequestHandlerTask;

public abstract class DashboardControllerWeb extends DashboardController implements RequestHandlerTask {

	private AddCourseMessage addCourseMessage = MessageFactory.getOutgoingMessage(AddCourseMessage.class);

	public DashboardControllerWeb(RootController parentController) {
		super(parentController);
	}

	@Override
	protected ViewLoader createViewLoader(String lang) {
		return Ntro.viewLoaderWeb()
		           .setHtmlUrl("/views/dashboard/structure.html")
		           .setCssUrl("/views/dashboard/style.css")
		           .setTranslationsUrl("/i18/"+lang+"/strings.json")
		           .setTargetClass(DashboardViewWeb.class);
	}

	@Override
	public void initialRequest(Path path, 
			                   Map<String, String[]> parameters, 
			                   String authToken) {
		T.call(this);
		
		if(parameters.containsKey("title")) {
			
			String title = parameters.get("title")[0];
			
			addCourseMessage.setText(title);
			
			addNextTask(addCourseMessage);
		}
		
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
