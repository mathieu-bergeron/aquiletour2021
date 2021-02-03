package ca.aquiletour.web.pages.settings;

import java.util.Map;

import ca.aquiletour.core.pages.root.RootController;
import ca.aquiletour.core.pages.settings.SettingsController;
import ca.ntro.core.Ntro;
import ca.ntro.core.Path;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.core.tasks.State;
import ca.ntro.web.RequestHandler;

public class SettingsControllerWeb extends SettingsController implements RequestHandler {

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
