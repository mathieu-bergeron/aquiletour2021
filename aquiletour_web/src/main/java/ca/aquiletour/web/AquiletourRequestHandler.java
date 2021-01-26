package ca.aquiletour.web;

import java.util.Map;

import ca.aquiletour.core.pages.rootpage.RootpageMain;
import ca.ntro.core.system.trace.T;

public abstract class AquiletourRequestHandler {
	
	public HandlerTask initialRequest(String path, 
			                          Map<String, String[]> parameters, 
			                          String authToken) {
		T.call(this);
		
		// FIXME: get this from request
		String lang = "fr";
		
		RootpageMain rootpageMain = rootpageMain(lang);
		
		HandlerTask handler = new HandlerTask();

		handler.addSubTask(rootpageMain, "RootPageMain");
		
		if(path.contains("settings")) {
			
			SendSettingsMessage sendSettingsMessage = new SendSettingsMessage();

			// TODO: NtroTask must support sugraph
			//       the nextTask of a subTask should also be a subTask
			rootpageMain.addNextTask(sendSettingsMessage);
			
		}else if(path.contains("dashboard")) {
			
			SendDashboardMessage sendDashboardMessage = new SendDashboardMessage();

			rootpageMain.addNextTask(sendDashboardMessage);
		}
		
		return handler;
	}
	
	protected abstract RootpageMain rootpageMain(String lang);

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
