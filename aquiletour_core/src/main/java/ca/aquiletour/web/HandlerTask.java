package ca.aquiletour.web;

import ca.aquiletour.web.pages.rootpage.RootpageControllerWeb;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTask;

public class HandlerTask extends NtroTask {

	@Override
	protected void initializeTask() {
		T.call(this);
	}

	@Override
	protected void runTask() {
		T.call(this);
		
		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

	// XXX: OutputStream not supported in JSweet
	public void writeHtml(StringBuilder out) {
		T.call(this);

		getSubTask(RootpageControllerWeb.class, "RootpageController").writeHtml(out);
	}
}
