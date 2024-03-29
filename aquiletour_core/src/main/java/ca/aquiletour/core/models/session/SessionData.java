package ca.aquiletour.core.models.session;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.paths.CoursePath;
import ca.aquiletour.core.pages.dashboard.student.models.CurrentTaskStudent;
import ca.ntro.core.system.trace.T;
import ca.ntro.users.NtroSessionData;

public class SessionData extends NtroSessionData {

	private int failedPasswordAttemps = 0;
	private String loginCode = "";
	private String currentCategoryId = Constants.CATEGORY_ID_CURRENT;
	private CurrentTasksByCourseKey currentTasksByCourseKey = new CurrentTasksByCourseKey();

	public String getLoginCode() {
		return loginCode;
	}

	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}

	public String getCurrentCategoryId() {
		return currentCategoryId;
	}

	public void setCurrentCategoryId(String currentCategoryId) {
		this.currentCategoryId = currentCategoryId;
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

	public SessionData toPublicData() {
		
		SessionData publicData = new SessionData();
		
		publicData.setFailedPasswordAttemps(getFailedPasswordAttemps());
		publicData.setCurrentCategoryId(getCurrentCategoryId());
		publicData.setCurrentTasksByCourseKey(getCurrentTasksByCourseKey());

		return publicData;
	}

	@Override
	protected void deepCopyOf(NtroSessionData toCopy) {
		T.call(this);
		
		super.deepCopyOf(toCopy);
		
		if(toCopy instanceof SessionData) {
			
			SessionData sessionToCopy = (SessionData) toCopy;
			
			setFailedPasswordAttemps(sessionToCopy.getFailedPasswordAttemps());
			setCurrentCategoryId(sessionToCopy.getCurrentCategoryId());
			setCurrentTasksByCourseKey(getCurrentTasksByCourseKey().clone());
			setLoginCode(getLoginCode());
		}
	}
}
