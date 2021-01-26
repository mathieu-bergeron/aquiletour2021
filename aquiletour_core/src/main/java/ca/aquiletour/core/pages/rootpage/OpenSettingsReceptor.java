package ca.aquiletour.core.pages.rootpage;

import ca.ntro.core.system.trace.T;
import ca.ntro.messages.MessageReceptionTask;

public class OpenSettingsReceptor extends MessageReceptionTask {

	@Override
	protected void runTask() {
		T.call(this);

	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
