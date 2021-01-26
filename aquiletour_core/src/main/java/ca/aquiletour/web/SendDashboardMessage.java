package ca.aquiletour.web;

import ca.aquiletour.core.messages.OpenDashboardMessage;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.messages.MessageFactory;

public class SendDashboardMessage extends NtroTask {
	
	private OpenDashboardMessage openDashboardMesssage = MessageFactory.getOutgoingMessage(OpenDashboardMessage.class);
	
	@Override
	protected void runTask() {
		T.call(this);
		
		openDashboardMesssage.sendMessage();

		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
