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
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.util.UrlEncoded;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.users.AnonUser;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.teacher.messages.ShowTeacherDashboardMessage;
import ca.aquiletour.core.pages.root.AiguilleurRootController;
import ca.aquiletour.core.pages.users.UsersModel;
import ca.aquiletour.core.pages.users.messages.ShowUsersMessage;
import ca.aquiletour.web.AquiletourBackendRequestHandler;
import ca.aquiletour.web.AquiletourRequestHandler;
import ca.ntro.core.Path;
import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.mvc.ControllerFactory;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.GraphTraceConnector;
import ca.ntro.jdk.FileLoader;
import ca.ntro.jdk.FileLoaderDev;
import ca.ntro.jdk.tasks.GraphTraceWriterJdk;
import ca.ntro.jdk.web.NtroWindowServer;
import ca.ntro.messages.MessageFactory;
import ca.ntro.services.Ntro;

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
		
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");

		System.err.println("");
		System.err.println("");
		System.err.println("");
		System.err.println("");
		
		NtroContext<User> context = new NtroContext<>();
		context.registerLang(Constants.LANG); // TODO

		authenticateUsersAddCookiesSetContext(context, baseRequest, response);

		Path path = new Path(baseRequest.getRequestURI().toString());
		
		boolean ifJsOnly = ifJsOnlySetCookies(baseRequest, response);

		NtroWindowServer newWindow = newWindow(ifJsOnly, path);
		
		if(!ifJsOnly) {

			Map<String, String[]> parameters = baseRequest.getParameterMap();
			AquiletourBackendRequestHandler.sendMessages(context, path, parameters);

			// DEBUG
		    //RootController rootController =  ControllerFactory.createRootController(RootController.class, "*", newWindow, context);

		    // NORMAL
		    AiguilleurRootController rootController =  ControllerFactory.createRootController(AiguilleurRootController.class, path, newWindow, context);


			// Client controller executes after
			// to make sure modifications to the
			// models are loaded up
			GraphTraceConnector trace = rootController.execute();

			//trace.addGraphWriter(new GraphTraceWriterJdk(new File("__task_graphs__", path.toFileName())));

			AquiletourRequestHandler.sendMessages(context, path, parameters);

			// DEBUG
			//Ntro.messageService().sendMessage(MessageFactory.createMessage(ShowUsersMessage.class));
			//Ntro.messageService().sendMessage(MessageFactory.createMessage(ShowTeacherDashboardMessage.class));

			//rootController.getTask().destroy();
			
			// XXX: prepare for next request
			Ntro.reset();
		}
		
		//System.out.println(rootController.getTask().toString());
		
		// XXX the entire taskGraph is not really async
		//     writeResponse will execute AFTER 
		//     every non-blocked task in webApp
		response.setContentType("text/html; charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		writeResponse(newWindow, baseRequest, out);
	}


	private void authenticateUsersAddCookiesSetContext(NtroContext<User> context, Request baseRequest, HttpServletResponse response) {
		T.call(this);
		
		ModelLoader usersLoader = Ntro.modelStore().getLoader(UsersModel.class, "TODO", "allUsers");
		usersLoader.execute();
		UsersModel usersModel = (UsersModel) usersLoader.getModel();
		
		User user = null;
		
		if(baseRequest.getParameter("userId") != null) {
			
			String userId = baseRequest.getParameter("userId");
			user = usersModel.getUsers().valueOf(userId);
			
			if(user == null) {
				user = new AnonUser();
			}
			
			setCookie(response, "user", Ntro.jsonService().toString(user));

		} else if(hasCookie(baseRequest, "user")) {

			String userString = UrlEncoded.decodeString(getCookie(baseRequest, "user"));
			user = Ntro.jsonService().fromString(User.class, userString);

		}else {

			user = new AnonUser();
		}

		context.registerUser(user);
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
		
		String trimmedValue = value.replace(" ", "");

		String urlEncodedString = UrlEncoded.encodeString(trimmedValue);
		
		Cookie cookie = new Cookie(name, urlEncodedString);

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

