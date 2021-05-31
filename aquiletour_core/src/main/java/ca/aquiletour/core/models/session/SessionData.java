package ca.aquiletour.core.models.session;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.pages.dashboard.student.models.CurrentTaskStudent;
import ca.ntro.core.system.trace.T;
import ca.ntro.users.NtroSessionData;

public class SessionData extends NtroSessionData {

	private int failedPasswordAttemps = 0;
	private String loginCode = "";
	private List<String> activeSemesterIds = new ArrayList<>();
	private CurrentTasksByCourseKey currentTasksByCourseKey = new CurrentTasksByCourseKey();

	public String getLoginCode() {
		return loginCode;
	}

	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}

	public List<String> getActiveSemesterIds() {
		return activeSemesterIds;
	}

	public void setActiveSemesterIds(List<String> activeSemesterIds) {
		this.activeSemesterIds = activeSemesterIds;
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

	public CurrentTasksByCourseKey getCurrentTasksByCourseKey() {
		return currentTasksByCourseKey;
	}

	public void setCurrentTasksByCourseKey(CurrentTasksByCourseKey currentTasksByCourseKey) {
		this.currentTasksByCourseKey = currentTasksByCourseKey;
	}

	public void updateCurrentTasks(CoursePath coursePath, List<CurrentTaskStudent> currentTasks) {
		T.call(this);
		
		getCurrentTasksByCourseKey().updateCurrentTasks(coursePath, currentTasks);
	}
}
