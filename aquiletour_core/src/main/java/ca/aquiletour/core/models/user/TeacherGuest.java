package ca.aquiletour.core.models.user;

import ca.ntro.core.system.trace.T;

public class TeacherGuest extends Teacher {

	@Override
	public User toSessionUser() {
		TeacherGuest sessionUser = new TeacherGuest();
		
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
	public boolean actsAsTeacher() {
		T.call(this);
		
		return true;
	}

	@Override
	public boolean isGuest() {
		T.call(this);
		
		return true;
	}
}
