package ca.aquiletour.core.pages.rootpage;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTask;

public class OpenDashboardTask extends NtroTask {

	@Override
	protected void initializeTask() {
		//ViewLoader viewLoader = loadView(lang);
		//viewLoader.setTaskId("ViewLoader");
		
		//addSubTask(viewLoader);
	}

	@Override
	protected void runTask() {
		T.call(this);

		RootpageController controller = getPreviousTask(RootpageController.class);
		
		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
