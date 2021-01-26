package ca.aquiletour.web;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTask;

public class SendDashboardMessage extends NtroTask {

	@Override
	protected void runTask() {
		T.call(this);

	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
