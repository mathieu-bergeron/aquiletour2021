// Copyright (C) (2020) (Mathieu Bergeron) (mathieu.bergeron@cmontmorency.qc.ca)
//
// This file is part of tutoriels4f5
//
// tutoriels4f5 is free software: you can redistribute it and/or modify
// it under the terms of the GNU Affero General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// tutoriels4f5 is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Affero General Public License for more details.
//
// You should have received a copy of the GNU Affero General Public License
// along with aquiletour.  If not, see <https://www.gnu.org/licenses/>

package ca.aquiletour.server.http;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.dashboard.messages.AddCourseMessage;
import ca.aquiletour.core.pages.dashboard.messages.ShowDashboardMessage;
import ca.aquiletour.core.pages.dashboard.values.CourseSummary;
import ca.aquiletour.core.pages.root.RootController;
import ca.aquiletour.core.pages.settings.ShowSettingsMessage;
import ca.ntro.core.Ntro;
import ca.ntro.core.Path;
import ca.ntro.core.mvc.ControllerFactory;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.ContainerTask;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.jdk.FileLoader;
import ca.ntro.jdk.FileLoaderDev;
import ca.ntro.jdk.web.NtroWindowServer;
import ca.ntro.messages.MessageFactory;

public class DynamicHandler extends AbstractHandler {

	public static ContextHandler createDynamicHandler(String urlPrefix, String privateFilesPrefix) {
		T.call(DynamicHandler.class);

        // Http handler
        ContextHandler dynamicContext = new ContextHandler();
        dynamicContext.setContextPath(urlPrefix);
        
        // XXX: dev-only, disable all caching
        dynamicContext.setInitParameter("cacheControl", "no-store,no-cache,must-revalidate,max-age=0,public");
        dynamicContext.setInitParameter("maxCacheSize", "0");
        
        dynamicContext.setHandler(new DynamicHandler(urlPrefix, privateFilesPrefix, new FileLoaderDev()));
        
        // TODO: dev-only: load resources from ./src/main/ressources NOT ./build/resources/...
        //staticFilesContext.setResourceBase("./src/main/resources");
        
        return dynamicContext;
	}
	
	private String resourcesUrlPrefix;
	private String privateFilesPrefix;
	private FileLoader fileLoader;

	public DynamicHandler(String resourcesUrlPrefix, 
			String publicFilesPrefix, 
			FileLoader fileLoader) {

		T.call(this);

		this.resourcesUrlPrefix = resourcesUrlPrefix;
		this.privateFilesPrefix = publicFilesPrefix;
		this.fileLoader = fileLoader;
	}
	
	@Override
	public void handle(String target, 
			           Request baseRequest, 
			           HttpServletRequest request, 
			           HttpServletResponse response)
			    
			    throws IOException, ServletException {
		
		T.call(this);
		

		OutputStream out = response.getOutputStream();
		serveView(baseRequest, response, out);
	}

	private void serveView(Request baseRequest, HttpServletResponse response, OutputStream out)
			throws FileNotFoundException, IOException {

		T.call(this);
		
		response.setContentType("text/html; charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		
		String authToken = null; // TODO
		Constants.LANG = "fr";   // TODO
		
		Path path = new Path(baseRequest.getRequestURI().toString());
		
		NtroWindowServer newWindow = ((NtroWindowServer) Ntro.window()).clone();
		
		newWindow.setCurrentPath(path);

		RootController rootController =  ControllerFactory.createRootController(RootController.class, path, newWindow);

		rootController.execute();

		// XXX: sending a message unblocks a task
		if(path.startsWith("settings")) {
			
			ShowSettingsMessage showSettingsMessage = MessageFactory.getOutgoingMessage(ShowSettingsMessage.class);
			showSettingsMessage.sendMessage();
			
		}else if(path.startsWith("dashboard")) {

			ShowDashboardMessage showDashboardMessage = MessageFactory.getOutgoingMessage(ShowDashboardMessage.class);
			showDashboardMessage.sendMessage();
			
			String courseTitle = baseRequest.getParameter("title");
			String summaryText = baseRequest.getParameter("summary");
			String summaryDate = baseRequest.getParameter("date");
			
			if(courseTitle != null && summaryText != null && summaryDate != null) {

				AddCourseMessage addCourseMessage = MessageFactory.getOutgoingMessage(AddCourseMessage.class);
				addCourseMessage.setCourse(new CourseSummary(courseTitle, summaryText, summaryDate));
				addCourseMessage.sendMessage();
			}
		}
		
		//System.out.println(rootController.getTask().toString());
		
		// XXX the entire taskGraph is not really async
		//     writeResponse will execute AFTER 
		//     every non-blocked task in webApp
		writeResponse(newWindow, baseRequest, out);
	}

	private void writeResponse(NtroWindowServer window, Request baseRequest, OutputStream out) {
		T.call(this);

		StringBuilder builder = new StringBuilder();
		window.writeHtml(builder);

		try {

			out.write(builder.toString().getBytes());

		} catch (IOException e) {
			e.printStackTrace();
		}

		baseRequest.setHandled(true);
	}
	
	
	
}

