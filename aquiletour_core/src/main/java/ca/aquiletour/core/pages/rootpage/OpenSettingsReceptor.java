package ca.aquiletour.core.pages.rootpage;

import ca.aquiletour.core.messages.OpenSettingsMessage;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.MessageReceptionTask;

public class OpenSettingsReceptor extends MessageReceptionTask {
	
	RootpageController rootpageController;

	public OpenSettingsReceptor(RootpageController rootpageController) {
		T.call(this);
		
		this.rootpageController = rootpageController;
	}


	@Override
	protected void runTask() {
		T.call(this);
		
		// XXX: just to show the idea (not useful here)
		OpenSettingsMessage message = (OpenSettingsMessage) getMessage();
		
		rootpageController.openSettings();
	}

	@Override
	protected void onFailure(Exception e) {
		
	}

}
