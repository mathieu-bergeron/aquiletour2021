package ca.aquiletour.core.models.users;

import ca.ntro.core.system.trace.T;

public class Teacher extends User {
	
	private boolean studentMode = false;

	@Override
	public User toSessionUser() {
		Teacher sessionUser = new Teacher();
		
		copySessionOnlyInfo(sessionUser);
		
		sessionUser.studentMode = this.studentMode;

		return sessionUser;
	}


	public boolean getStudentMode() {
		return studentMode;
	}


	public void setStudentMode(boolean studentMode) {
		this.studentMode = studentMode;
	}

	@Override
	public boolean actsAsTeacher() {
		T.call(this);
		
		return !studentMode;
	}


	public void toggleStudentMode() {
		T.call(this);
		
		setStudentMode(!getStudentMode());
	}
}
