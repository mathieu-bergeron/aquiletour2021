package ca.aquiletour.core.models.user;

import ca.ntro.core.system.trace.T;

public class Admin extends Teacher {
	
	private boolean adminMode = false;

	@Override
	protected void copySessionOnlyInfo(User sessionUser) {
		T.call(this);
		super.copySessionOnlyInfo(sessionUser);
		
		if(sessionUser instanceof Admin) {
			((Admin) sessionUser).adminMode = this.adminMode;
		}
	}

	public boolean getAdminMode() {
		return adminMode;
	}

	public void setAdminMode(boolean adminMode) {
		this.adminMode = adminMode;
	}

	@Override
	public void resetAfterLogout() {
		T.call(this);
		super.resetAfterLogout();

		setAdminMode(false);
	}

	public void toggleAdminMode() {
		T.call(this);
		
		setAdminMode(!getAdminMode());
	}

	@Override
	public boolean actsAsAdmin() {
		T.call(this);
		
		return getAdminMode();
	}
}
