package ca.aquiletour.core.pages.root;

import ca.ntro.core.tasks.NtroTaskImpl;

public class ShowSettingsTask extends NtroTaskImpl {
	
	private RootController rootController;

	public ShowSettingsTask(RootController rootController) {
		this.rootController = rootController;
	}

	@Override
	protected void initializeTask() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void runTaskAsync() {
		
		rootController.showSettings();
		
		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
