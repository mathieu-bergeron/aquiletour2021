package ca.aquiletour.core.models.session;

import ca.ntro.users.NtroSessionData;

public class SessionData extends NtroSessionData {

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
}
