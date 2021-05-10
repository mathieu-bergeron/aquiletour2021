package ca.aquiletour.core.models.session;

import ca.aquiletour.core.Constants;
import ca.ntro.core.system.trace.T;
import ca.ntro.users.NtroSessionData;

public class SessionData extends NtroSessionData {

	private int failedPasswordAttemps = 0;
	private String loginCode = "";
	private String currentSemester = "";

	public String getLoginCode() {
		return loginCode;
	}

	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}

	public String getCurrentSemester() {
		return currentSemester;
	}

	public void setCurrentSemester(String currentSemester) {
		this.currentSemester = currentSemester;
	}

	public int getFailedPasswordAttemps() {
		return failedPasswordAttemps;
	}

	public void setFailedPasswordAttemps(int failedPasswordAttemps) {
		this.failedPasswordAttemps = failedPasswordAttemps;
	}
	
	public void incrementFailedPasswordAttemps() {
		T.call(this);
		
		failedPasswordAttemps++;
	}

	public void resetFailedPasswordAttemps() {
		T.call(this);
		
		failedPasswordAttemps = 0;
	}
	
	public boolean hasReachedMaxPasswordAttemps() {
		T.call(this);
		
		return failedPasswordAttemps >= Constants.MAX_PASSWORD_ATTEMPS;
	}
}
