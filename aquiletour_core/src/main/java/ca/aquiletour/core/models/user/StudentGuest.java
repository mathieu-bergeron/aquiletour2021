package ca.aquiletour.core.models.user;

import ca.ntro.core.system.trace.T;

public class StudentGuest extends Student {

	@Override
	public User toSessionUser() {
		StudentGuest sessionUser = new StudentGuest();
		
		copySessionOnlyInfo(sessionUser);
		
		return sessionUser;
	}

	@Override
	public void copyPublicInfomation(User otherUser) {
		super.copyPublicInfomation(otherUser);

		setFirstname(otherUser.getId());
		setLastname("");
	}

	@Override
	public boolean isGuest() {
		T.call(this);
		
		return true;
	}

}
