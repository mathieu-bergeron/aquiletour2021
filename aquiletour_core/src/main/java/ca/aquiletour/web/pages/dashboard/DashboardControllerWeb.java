package ca.aquiletour.web.pages.dashboard;

import java.util.Map;

import ca.aquiletour.core.pages.dashboard.DashboardController;
import ca.aquiletour.core.pages.dashboard.messages.AddCourseMessage;
import ca.aquiletour.core.pages.dashboard.values.CourseSummary;
import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.Ntro;
import ca.ntro.core.Path;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.core.tasks.State;
import ca.ntro.messages.MessageFactory;
import ca.ntro.web.RequestHandler;

public class DashboardControllerWeb extends DashboardController implements RequestHandler {

	@Override
	public void initialRequest(Path path, Map<String, String[]> parameters, String authToken) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newRequest(Path oldPath, Path path, Map<String, String[]> oldParameters,
			Map<String, String[]> parameters, String authToken) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}
}
