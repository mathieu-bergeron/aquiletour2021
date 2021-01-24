package ca.aquiletour.web;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTask;

public class HandlerTask extends NtroTask {


	// XXX: OutputStream not supported in JSweet
	public void writeHtml(StringBuilder out) {
		T.call(this);

		out.append("Bonjour!");
	}

	@Override
	protected void runTask() {
		
		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}


}
