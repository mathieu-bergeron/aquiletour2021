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
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.util.UrlEncoded;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.messages.AddStudentCsvMessage;
import ca.aquiletour.core.messages.InitializeSessionMessage;
import ca.aquiletour.core.models.session.SessionData;
import ca.aquiletour.core.models.users.Teacher;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.home.ShowHomeMessage;
import ca.aquiletour.core.pages.login.ShowLoginMessage;
import ca.aquiletour.core.pages.root.RootController;
import ca.aquiletour.web.AquiletourBackendRequestHandler;
import ca.aquiletour.web.AquiletourRequestHandler;
import ca.ntro.core.Path;
import ca.ntro.core.mvc.ControllerFactory;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.GraphTraceConnector;
import ca.ntro.jdk.FileLoader;
import ca.ntro.jdk.FileLoaderDev;
import ca.ntro.jdk.web.NtroWindowServer;
import ca.ntro.messages.MessageHandler;
import ca.ntro.services.Ntro;
import ca.ntro.users.NtroSession;

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

		if (request.getContentType() != null && request.getContentType().startsWith("multipart/form-data")) {
			  baseRequest.setAttribute(Request.MULTIPART_CONFIG_ELEMENT, new MultipartConfigElement("/"));
		}

		OutputStream out = response.getOutputStream();
		serveView(baseRequest, response, out);
	}

	private void serveView(Request baseRequest, HttpServletResponse response, OutputStream out)
			throws FileNotFoundException, IOException {

		T.call(this);
		
		/*
		System.out.println("");
		System.out.println("");
		System.out.println("Request for: " + baseRequest.getRequestURI().toString());
		*/
		
		sendSessionMessagesAccordingToCookies(baseRequest);

		Path path = new Path(baseRequest.getRequestURI().toString());
		Map<String, String[]> parameters = baseRequest.getParameterMap();

		executeBackend(baseRequest, response, path, parameters);

		boolean ifJsOnly = ifJsOnlySetCookies(baseRequest, response);

		NtroWindowServer window = newWindow(ifJsOnly, path);
		
		if(!ifJsOnly) {
			executeFrontendOnServer(baseRequest, response, path, parameters, window);
		}
		
		// XXX on the server, the taskGraph is sync
		//     writeResponse will execute AFTER 
		//     every non-blocked task in webApp
		if(!baseRequest.isHandled()) {
			response.setContentType("text/html; charset=utf-8");
			response.setStatus(HttpServletResponse.SC_OK);
			writeResponse(window, baseRequest, out);
		}
	}

	private void executeBackend(Request baseRequest, HttpServletResponse response, Path path,
			Map<String, String[]> parameters) throws IOException {

		AquiletourBackendRequestHandler.sendMessages(path, parameters);

		setSessionCookie(response);

		sendCsvMessage(baseRequest);
	}

	private void sendCsvMessage(Request baseRequest) throws IOException {
		if(Ntro.currentUser() instanceof Teacher) {
			
			String queueId = baseRequest.getParameter("queueId");
			Part filePart = null;
			try {
				filePart = baseRequest.getPart("csvFile");
			} catch (IOException | ServletException e) {}

			if(queueId != null && filePart != null) {
				String fileContent = readPart(filePart);
				
				AddStudentCsvMessage addStudentCsvMessage = Ntro.messages().create(AddStudentCsvMessage.class);
				addStudentCsvMessage.setCsvString(fileContent);
				addStudentCsvMessage.setQueueId(queueId);
				Ntro.backendService().sendMessageToBackend(addStudentCsvMessage);
			}
		}
	}

	private String readPart(Part part) throws IOException {
		StringBuilder builder = new StringBuilder();
		InputStream inputStream = part.getInputStream();
		Scanner scanner = new Scanner(inputStream);
		while(scanner.hasNextLine()) {
			builder.append(scanner.nextLine());
			builder.append(System.lineSeparator());
		}
		scanner.close();

		return builder.toString();
	}

	private void sendSessionMessagesAccordingToCookies(Request baseRequest) {
		T.call(this);
		
		SessionData sessionData = new SessionData();
		sessionData.setCurrentSemester(Constants.COURSE_DRAFTS);
		Ntro.currentSession().setSessionData(sessionData);

		InitializeSessionMessage authenticateSessionUserMessage = Ntro.messages().create(InitializeSessionMessage.class);

		if(hasCookie(baseRequest, "session")) {
			String sessionString = UrlEncoded.decodeString(getCookie(baseRequest, "session"));
			NtroSession session = Ntro.jsonService().fromString(NtroSession.class, sessionString);

			authenticateSessionUserMessage.setSessionUser((User) session.getUser());
		}

		Ntro.backendService().sendMessageToBackend(authenticateSessionUserMessage);
	}

	private void executeFrontendOnServer(Request baseRequest, 
			                             HttpServletResponse response, 
			                             Path path,
			                             Map<String, String[]> parameters,
			                             NtroWindowServer window) {
		
		handleRedirections(baseRequest, response, path);

		NtroContext<User, SessionData> context = new NtroContext<>();
		context.registerLang(Constants.LANG); // TODO
		context.registerUser((User) Ntro.currentUser());
		context.registerSessionData((SessionData) Ntro.currentSession().getSessionData());

		// DEBUG
		// RootController rootController =  ControllerFactory.createRootController(RootController.class, "*", newWindow, context);

		// NORMAL
		RootController rootController =  ControllerFactory.createRootController(RootController.class, path, window, context);

		// Client controller executes after
		// to make sure modifications to the
		// models are loaded up
		GraphTraceConnector trace = rootController.execute();

		//trace.addGraphWriter(new GraphTraceWriterJdk(new File("__task_graphs__", path.toFileName())));

		AquiletourRequestHandler.sendMessages(context, path, parameters);

		// XXX: prepare for next request
		Ntro.reset();
	}

	private void handleRedirections(Request baseRequest, HttpServletResponse response, Path path) {
		// FIXME: there must be a better way to redirect to login
		if(!path.startsWith("connexion")) {
			Ntro.messages().registerHandler(ShowLoginMessage.class, new MessageHandler<ShowLoginMessage>() {
				@Override
				public void handle(ShowLoginMessage message) {
					T.call(this);
					
					String messageToUser = message.getMessageToUser();
					
					// XXX: on the server, ShowLoginMessage is a redirect to /connexion?message=""
					String redirectUrl = "/connexion?message=" + UrlEncoded.encodeString(messageToUser);
					try {
						response.sendRedirect(redirectUrl);
						baseRequest.setHandled(true);
					} catch (IOException e) {
					}
				}
			});
		}

		// FIXME: we need a better way to handle redirects
		if(!path.startsWith("accueil")) {
			Ntro.messages().registerHandler(ShowHomeMessage.class, new MessageHandler<ShowHomeMessage>() {
				@Override
				public void handle(ShowHomeMessage message) {
					T.call(this);
					String redirectUrl = "/accueil";
					try {
						response.sendRedirect(redirectUrl);
						baseRequest.setHandled(true);
					} catch (IOException e) {
					}
				}
			});
		}
	}


	private void setSessionCookie(HttpServletResponse response) {
		T.call(this);
		
		NtroSession session = Ntro.currentSession();
		User user = (User) session.getUser();
		session.setUser(user.toSessionUser());
		setCookie(response, "session", Ntro.jsonService().toString(session));
	}

	private void setSemesterCookie(HttpServletResponse response) {
		T.call(this);
		
		User currentUser = (User) Ntro.currentUser();
		User sessionUser = currentUser.toSessionUser();
		setCookie(response, "user", Ntro.jsonService().toString(sessionUser));
	}
	
	private boolean ifJsOnlySetCookies(Request baseRequest, HttpServletResponse response) {
		T.call(this);
		
		boolean ifJsOnly = true;

		if(baseRequest.getParameter("nojs") != null) {
			
			setCookie(response, "jsOnly" , "false" );
			ifJsOnly = false;

		} else if(baseRequest.getParameter("js") != null) {
			
			setCookie(response, "jsOnly" , "true" );
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
				return UrlEncoded.decodeString(cookie.getValue());
			}
		}
		
		return null;
	}

	private void eraseCookie(HttpServletResponse response, String name) {
		T.call(this);
		
		Cookie cookie = new Cookie(name, "");
		cookie.setPath("/");
		cookie.setMaxAge(0);

		response.addCookie(cookie);
	}

	private void setCookie(HttpServletResponse response, String name, String value) {
		T.call(this);
		
		String trimmedValue = value.replace(" ", "");

		String urlEncodedString = UrlEncoded.encodeString(trimmedValue);
		
		Cookie cookie = new Cookie(name, urlEncodedString);
		cookie.setPath("/");

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

