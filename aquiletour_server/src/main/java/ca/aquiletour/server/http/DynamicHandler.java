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
import java.util.Calendar;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.HttpCookie;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.dashboard.messages.AddCourseMessage;
import ca.aquiletour.core.pages.dashboard.messages.ShowDashboardMessage;
import ca.aquiletour.core.pages.dashboard.values.CourseSummary;
import ca.aquiletour.core.pages.queue.messages.AddAppointmentMessage;
import ca.aquiletour.core.pages.queue.messages.DeleteAppointmentMessage;
import ca.aquiletour.core.pages.queue.messages.ShowQueueMessage;
import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.aquiletour.core.pages.root.RootController;
import ca.aquiletour.core.pages.settings.ShowSettingsMessage;
import ca.aquiletour.core.pages.users.UsersModel;
import ca.aquiletour.core.pages.users.values.User;
import ca.aquiletour.web.AquiletourRequestHandler;
import ca.ntro.core.Ntro;
import ca.ntro.core.Path;
import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.mvc.ControllerFactory;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.services.stores.LocalStore;
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
		
		NtroContext context = new NtroContext();
		context.setLang(Constants.LANG); // TODO

		boolean userIsLoggedIn = authenticateUsersAddCookiesSetContext(context, baseRequest, response);

		Path path;
		
		if(userIsLoggedIn) {
			
			path = new Path(baseRequest.getRequestURI().toString());
			
		}else {
			
			path = new Path("/");
			
		}

		
		boolean ifJsOnly = ifJsOnlySetCookies(baseRequest, response);

		NtroWindowServer newWindow = newWindow(ifJsOnly, path);
		
		if(!ifJsOnly) {

		    RootController rootController =  ControllerFactory.createRootController(RootController.class, path, newWindow, context);

			rootController.execute();

			Map<String, String[]> parameters = baseRequest.getParameterMap();

			// XXX: sending a message unblocks a task
			AquiletourRequestHandler.sendMessages(context, path, parameters);
		}
		
		//System.out.println(rootController.getTask().toString());
		
		// XXX the entire taskGraph is not really async
		//     writeResponse will execute AFTER 
		//     every non-blocked task in webApp
		response.setContentType("text/html; charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		writeResponse(newWindow, baseRequest, out);
	}


	private boolean authenticateUsersAddCookiesSetContext(NtroContext context, Request baseRequest, HttpServletResponse response) {
		T.call(this);
		
		boolean isUserLoggedIn = false;

		ModelLoader usersLoader = LocalStore.getLoader(UsersModel.class, "TODO", "allUsers");
		usersLoader.execute();
		UsersModel usersModel = (UsersModel) usersLoader.getModel();
		
		if(hasCookie(baseRequest, "userId") 
				&& hasCookie(baseRequest, "authToken")) {
			
			String userId = getCookie(baseRequest, "userId");
			String authToken = getCookie(baseRequest, "authToken");
			
			isUserLoggedIn = usersModel.isUserValid(userId, authToken);
			
			if(!isUserLoggedIn) {
				eraseCookie(response, "userId");
				eraseCookie(response, "authToken");

			}else {

				context.setAuthToken(authToken);
				context.setUserId(userId);
			}
			
		}else if(baseRequest.getParameter("userId") != null 
				&& baseRequest.getParameter("authToken") != null) {
			
			String userId = baseRequest.getParameter("userId");
			String authToken  = baseRequest.getParameter("authToken");

			isUserLoggedIn = usersModel.isUserValid(userId, authToken);
			
			if(isUserLoggedIn) {
				setCookie(response, "userId", userId);
				setCookie(response, "authToken", authToken);
				
				context.setAuthToken(authToken);
				context.setUserId(userId);
			}
		}

		if(!isUserLoggedIn){

		    User defaultUser = usersModel.getUsers().getValue().values().iterator().next();

		    String userId = defaultUser.getUserId();
		    String authToken = defaultUser.getAuthToken();

			setCookie(response, "userId", userId);
			setCookie(response, "authToken", authToken);
			
			context.setAuthToken(authToken);
			context.setUserId(userId);
		}

		return isUserLoggedIn;
	}


	private boolean ifJsOnlySetCookies(Request baseRequest, HttpServletResponse response) {
		T.call(this);
		
		boolean ifJsOnly = true;

		if(baseRequest.getParameter("nojs") != null) {
			
			response.addCookie(new Cookie("jsOnly", "false"));
			ifJsOnly = false;

		} else if(baseRequest.getParameter("js") != null) {
			
			response.addCookie(new Cookie("jsOnly", "true"));
			ifJsOnly = true;
			
		}else if(hasCookie(baseRequest, "jsOnly")) {
			
			String jsOnlyCookie = getCookie(baseRequest, "jsOnly");
			ifJsOnly = Boolean.valueOf(jsOnlyCookie);

		}
		
		return ifJsOnly;

	}

	private NtroWindowServer newWindow(boolean ifJsOnly, Path path) {
		T.call(this);

		NtroWindowServer newWindow;

		if(ifJsOnly) {

			newWindow = new NtroWindowServer("/private/index.html");
			
		}else {

			newWindow = new NtroWindowServer("/private/nojs.html");

		} 

		newWindow.setCurrentPath(path);

		return newWindow;
	}
	
	private boolean hasCookie(Request baseRequest, String name) {
		T.call(this);
		
		if(baseRequest.getCookies() == null) return false;
		
		for(Cookie cookie : baseRequest.getCookies()) {
			if(cookie.getName().equals(name)) {
				return true;
			}
		}
		
		return false;
	}

	private String getCookie(Request baseRequest, String name) {
		T.call(this);
		
		if(baseRequest.getCookies() == null) return null;
		
		for(Cookie cookie : baseRequest.getCookies()) {
			if(cookie.getName().equals(name)) {
				return cookie.getValue();
			}
		}
		
		return null;
	}

	private void eraseCookie(HttpServletResponse response, String name) {
		T.call(this);
		
		Cookie cookie = new Cookie(name, "");
		cookie.setMaxAge(0);

		response.addCookie(cookie);
	}

	private void setCookie(HttpServletResponse response, String name, String value) {
		T.call(this);
		
		Cookie cookie = new Cookie(name, value);

		response.addCookie(cookie);
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

