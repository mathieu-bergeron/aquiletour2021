package ca.aquiletour.core.models.user;

import ca.ntro.core.system.trace.T;
import ca.ntro.users.NtroUser;

public class Teacher extends User {
	
	private boolean studentMode = false;

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
		
		return !studentMode && !actsAsAdmin();
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

	@Override
	public boolean isGuest() {
		T.call(this);
		
		return false;
	}

	public boolean isStudent() {
		T.call(this);

		return false;
	}

	public boolean isTeacher() {
		T.call(this);

		return true;
	}

	public boolean actsAsStudent() {
		T.call(this);

		return getStudentMode();
	}

	@Override
	protected void deepCopyOf(NtroUser toCopy) {
		T.call(this);
		
		super.deepCopyOf(toCopy);
		
		if(toCopy instanceof Teacher) {
			
			Teacher userToCopy = (Teacher) toCopy;

			setStudentMode(userToCopy.getStudentMode());
		}
	}
}
