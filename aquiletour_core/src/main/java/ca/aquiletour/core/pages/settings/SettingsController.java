package ca.aquiletour.core.pages.settings;

import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.system.trace.T;

public abstract class SettingsController extends NtroController {
	
	@Override
	protected void initializeTask() {
		//ViewLoader viewLoader = loadView(lang);
		//viewLoader.setTaskId("ViewLoader");
		
		//addSubTask(viewLoader);
	}

	
	@Override
	protected void runTaskAsync() {
		T.call(this);

		RootController controller = getPreviousTask(RootController.class, "RootpageController");
		
		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		
	}

}
