package ca.aquiletour.server.pages.root;

import java.util.Map;

import ca.aquiletour.server.pages.dashboard.DashboardControllerServer;
import ca.aquiletour.server.pages.settings.SettingsControllerServer;
import ca.aquiletour.web.pages.dashboard.DashboardControllerWeb;
import ca.aquiletour.web.pages.root.RootControllerWeb;
import ca.aquiletour.web.pages.settings.SettingsControllerWeb;
import ca.ntro.core.Path;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.core.tasks.State;
import ca.ntro.jdk.web.NtroWindowServer;
import ca.ntro.web.NtroWindowWeb;

public class RootControllerServer extends RootControllerWeb {

	@Override
	public void initialRequest(Path path, Map<String, String[]> parameters, String authToken) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newRequest(Path oldPath, Path path, Map<String, String[]> oldParameters,
			Map<String, String[]> parameters, String authToken) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTaskId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTaskId(String taskId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public NtroTask addPreviousTask(NtroTask task) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NtroTask addNextTask(NtroTask task) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void notifySomePreviousTaskFinished() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public State getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasId(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addSubTask(NtroTask task) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifySomeSubTaskFinished() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setParentTask(NtroTask ntroTaskImpl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <NT extends NtroTask> NT getSubTask(Class<NT> taskClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <NT extends NtroTask> NT getSubTask(Class<NT> taskClass, String taskId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void writeHtml(StringBuilder out) {
		// TODO Auto-generated method stub
		
	}
}
