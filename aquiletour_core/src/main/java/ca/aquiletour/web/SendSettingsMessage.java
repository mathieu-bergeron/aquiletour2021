package ca.aquiletour.web;

import ca.aquiletour.core.messages.OpenSettingsMessage;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.messages.MessageFactory;

public class SendSettingsMessage extends NtroTask {

	@Override
	protected void runTask() {
		T.call(this);
		
		OpenSettingsMessage openSettings = MessageFactory.getOutgoingMessage(OpenSettingsMessage.class);

		openSettings.sendMessage();
		
		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
