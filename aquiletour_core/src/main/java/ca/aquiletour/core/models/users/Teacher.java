package ca.aquiletour.core.models.users;

import ca.ntro.core.system.trace.T;

public class Teacher extends User {
	
	private boolean studentMode = false;

	@Override
	public User toSessionUser() {
		Teacher sessionUser = new Teacher();
		
		copySessionOnlyInfo(sessionUser);

		return sessionUser;
	}

	@Override
	protected void copySessionOnlyInfo(User sessionUser) {
		T.call(this);
		super.copySessionOnlyInfo(sessionUser);
		
		if(sessionUser instanceof Teacher) {
			((Teacher) sessionUser).studentMode = this.studentMode;
		}
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

	@Override
	public void resetAfterLogout() {
		T.call(this);
		super.resetAfterLogout();

		setStudentMode(false);
	}
}
