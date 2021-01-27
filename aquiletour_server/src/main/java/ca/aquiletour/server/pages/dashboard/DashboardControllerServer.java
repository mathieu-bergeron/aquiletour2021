package ca.aquiletour.server.pages.dashboard;

import java.util.Map;

import ca.aquiletour.core.pages.root.RootController;
import ca.aquiletour.web.Path;
import ca.aquiletour.web.pages.dashboard.DashboardControllerWeb;

public class DashboardControllerServer extends DashboardControllerWeb {

	@Override
	protected RootController getRootController() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initialRequest(Path path, Map<String, String[]> parameters, String authToken) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newRequest(Path oldPath, Path path, Map<String, String[]> oldParameters,
			Map<String, String[]> parameters, String authToken) {
		// TODO Auto-generated method stub
		
	}


}
