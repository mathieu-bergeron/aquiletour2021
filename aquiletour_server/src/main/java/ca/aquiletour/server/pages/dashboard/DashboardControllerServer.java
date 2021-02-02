package ca.aquiletour.server.pages.dashboard;


import java.util.Map;

import ca.aquiletour.core.pages.root.RootController;
import ca.aquiletour.web.pages.dashboard.DashboardControllerWeb;
import ca.ntro.core.Path;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.core.tasks.State;

public class DashboardControllerServer extends DashboardControllerWeb {

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
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}
}
