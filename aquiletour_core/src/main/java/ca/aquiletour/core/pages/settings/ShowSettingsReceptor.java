package ca.aquiletour.core.pages.settings;

import ca.ntro.core.mvc.MessageReceptor;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskImpl;

public class ShowSettingsReceptor extends MessageReceptor {
	
	private SettingsController settingsController;

	public ShowSettingsReceptor(SettingsController settingsController) {
		this.settingsController = settingsController;
	}

	@Override
	protected void initializeTask() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void runTaskAsync() {
		T.call(this);
		
		settingsController.showSettings();
		
		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
