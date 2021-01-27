package ca.aquiletour.web;

import java.util.Map;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.rootpage.OpenDashboardTask;
import ca.aquiletour.core.pages.rootpage.OpenSettingsTask;
import ca.aquiletour.core.pages.rootpage.RootpageController;
import ca.ntro.core.system.trace.T;

public abstract class AquiletourRequestHandler {
	
	public HandlerTask initialRequest(String path, 
			                          Map<String, String[]> parameters, 
			                          String authToken) {
		T.call(this);
		
		// TODO: get this from request
		Constants.LANG = "fr";

		RootpageController rootpageController = rootpageController();
		rootpageController.setTaskId("RootpageController");
		
		HandlerTask handler = new HandlerTask();

		handler.addSubTask(rootpageController);
		
		if(path.contains("settings")) {
			
			OpenSettingsTask openSettingsTask = rootpageController.openSettingsTask();
			
			// TODO: NtroTask must support subgraph
			//       the nextTask of a subTask should also be a subTask
			rootpageController.addNextTask(openSettingsTask);
			handler.addSubTask(openSettingsTask);
			
		}else if(path.contains("dashboard")) {
			
			OpenDashboardTask openDashboardTask = rootpageController.openDashboardTask();

			rootpageController.addNextTask(openDashboardTask);
			handler.addSubTask(openDashboardTask);
		}
		
		return handler;
	}
	
	protected abstract RootpageController rootpageController();

	public HandlerTask newRequest(String oldPath, 
			               String path, 
			               Map<String, String[]> oldParameters, 
			               Map<String, String[]> parameters, 
			               String authToken) {
		T.call(this);
		
		// TODO: this should only be called in JS
		//       we send appropriate messages to the rootpage controller

		// TODO
		return new HandlerTask();
	}

	

}
