package ca.aquiletour.core.pages.dashboard;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.mvc.view.ViewLoader;
import ca.ntro.core.system.trace.T;

public abstract class DashboardController extends NtroController {

	@Override
	protected void initializeTask() {
		ViewLoader viewLoader = createViewLoader(Constants.LANG);
		viewLoader.setTaskId("ViewLoader");
		
		addSubTask(viewLoader);
	}
	
	@Override
	protected void runTaskAsync() {
		T.call(this);
		
		// FIXME: this only makes sense on the Server
		//        in JavaFx, RootpageController will not be a previousTask!
		//        (unless???)
		RootController controller = getPreviousTask(RootController.class, "RootpageController");
		
		DashboardView view = (DashboardView) getSubTask(ViewLoader.class, "ViewLoader").getView();
		
		notifyTaskFinished();
	}
	
	protected abstract RootController getRootController();

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
