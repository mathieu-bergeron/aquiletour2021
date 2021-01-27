package ca.aquiletour.core.pages.dashboard;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.mvc.view.ViewLoader;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskImpl;

public abstract class DashboardController extends NtroController {

	@Override
	protected void initializeTask() {
		ViewLoader viewLoader = loadView(Constants.LANG);
		viewLoader.setTaskId("ViewLoader");
		
		addSubTask(viewLoader);
	}
	
	@Override
	protected void runTask() {
		T.call(this);
		
		// FIXME: this only makes sense on the Server
		//        in JavaFx, RootpageController will not be a previousTask!
		//        (unless???)
		RootController controller = getPreviousTask(RootController.class, "RootpageController");
		
		notifyTaskFinished();
	}
	
	protected abstract RootController getRootController();

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
