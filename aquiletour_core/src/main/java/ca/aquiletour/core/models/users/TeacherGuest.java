package ca.aquiletour.core.models.users;

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

		setName(otherUser.getId());
		setSurname("");
	}

	@Override
	public boolean actsAsTeacher() {
		T.call(this);
		
		return true;
	}
}
