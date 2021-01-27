package ca.aquiletour.core.pages.rootpage;

import ca.aquiletour.core.Constants;
import ca.ntro.core.mvc.view.ViewLoader;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTask;

public abstract class OpenDashboardTask extends NtroTask {

	@Override
	protected void initializeTask() {
		ViewLoader viewLoader = loadView(Constants.LANG);
		viewLoader.setTaskId("ViewLoader");
		
		addSubTask(viewLoader);
	}
	
	protected abstract ViewLoader loadView(String lang);

	@Override
	protected void runTask() {
		T.call(this);
		
		// FIXME: this only makes sense on the Server
		//        in JavaFx, RootpageController will not be a previousTask!
		//        (unless???)
		RootpageController controller = getPreviousTask(RootpageController.class, "RootpageController");
		
		notifyTaskFinished();
	}
	
	protected abstract RootpageController getRootpageController();

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
