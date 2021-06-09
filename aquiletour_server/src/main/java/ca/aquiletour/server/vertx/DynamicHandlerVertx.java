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

package ca.aquiletour.server.vertx;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.UrlEncoded;

import ca.aquiletour.core.AquiletourMain;
import ca.aquiletour.core.Constants;
import ca.aquiletour.core.messages.AddStudentCsvMessage;
import ca.aquiletour.core.messages.InitializeSessionMessage;
import ca.aquiletour.core.models.logs.LogModel;
import ca.aquiletour.core.models.logs.LogModelCourse;
import ca.aquiletour.core.models.logs.LogModelQueue;
import ca.aquiletour.core.models.paths.CoursePath;
import ca.aquiletour.core.models.session.SessionData;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.home.ShowHomeMessage;
import ca.aquiletour.core.pages.login.ShowLoginMessage;
import ca.aquiletour.core.pages.root.RootController;
import ca.aquiletour.web.AquiletourBackendRequestHandler;
import ca.aquiletour.web.AquiletourRequestHandler;
import ca.ntro.backend.BackendError;
import ca.ntro.backend.UserInputError;
import ca.ntro.core.Path;
import ca.ntro.core.mvc.ControllerFactory;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.GraphTraceConnector;
import ca.ntro.jdk.FileLoader;
import ca.ntro.jdk.FileLoaderDev;
import ca.ntro.jdk.web.NtroWindowServer;
import ca.ntro.messages.MessageHandler;
import ca.ntro.messages.ntro_messages.NtroErrorMessage;
import ca.ntro.services.ModelStoreSync;
import ca.ntro.services.Ntro;
import ca.ntro.users.NtroSession;
import io.netty.handler.codec.http.HttpMethod;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServerFileUpload;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.http.Cookie;
import io.vertx.core.http.CookieSameSite;
import io.vertx.ext.web.RoutingContext;

public class DynamicHandlerVertx {

	private String privateFilesPrefix;

	public DynamicHandlerVertx(String publicFilesPrefix) {

		T.call(this);

		this.privateFilesPrefix = publicFilesPrefix;
	}
	
	public static void handle(RoutingContext routingContext) {
		T.call(DynamicHandlerVertx.class);
		
		HttpServerRequest request = routingContext.request();
		HttpServerResponse response = routingContext.response();
		
		/*
		
		request.uploadHandler(upload -> {
			if(Ntro.currentUser() instanceof Teacher 
					&& upload.contentType().startsWith("multipart/form-data")) {

				String semesterId = request.getParam("semesterId");
				String courseId = request.getParam("courseId");
				
				if(semesterId != null && courseId != null) {
					sendCsvMessage(semesterId, courseId, upload);
				}
			}
		}); */
		
		String rawPath = request.uri();
		Path path = Path.fromRawPath(rawPath);
		
		if(rawPath.contains(Constants.LOG_URL_SEGMENT)) {
			
			try {

				serveLog(request, response, path);

			} catch (BackendError e) {
				
				Log.error("Error serving logs: " + e.getMessage());
			}
			
		}else {

			serveView(request, response, path);
		}
	}

	private static void serveLog(HttpServerRequest baseRequest, 
			              HttpServerResponse response, 
			              Path path)

			throws BackendError {
		
		T.call(DynamicHandlerVertx.class);
		
		Path subPath = path.removePrefix(Constants.LOG_URL_SEGMENT);
		
		if(subPath.nameCount() >= 4) {
			
			CoursePath coursePath = CoursePath.fromPath(subPath.subPath(1));
			
			if(subPath.startsWith(Constants.QUEUE_URL_SEGMENT)) {
				
				serveLog(baseRequest, response, coursePath, LogModelQueue.class);
				
			} else if(subPath.startsWith(Constants.COURSE_URL_SEGMENT)) {

				serveLog(baseRequest, response, coursePath, LogModelCourse.class);
			}
		}
	}

	private static <LM extends LogModel> void serveLog(HttpServerRequest baseRequest, 
			              HttpServerResponse response, 
			              CoursePath coursePath, 
			              Class<LM> logModelClass)

			throws BackendError {

		T.call(DynamicHandlerVertx.class);
		
		ModelStoreSync modelStore = new ModelStoreSync(Ntro.modelStore());
		
		StringBuilder logContent = new StringBuilder();
		
		modelStore.readModel(logModelClass, "admin", coursePath, logModel -> {

			logModel.writeCsvFileContent(Constants.CSV_SEPARATOR, logContent);
		});
		
		response.putHeader("content-disposition", "attachment; filename=\"" + coursePath.toFileName() + ".csv\"");
		response.putHeader("content-type", "text/csv; charset=utf-8");
		response.setStatusCode(Response.SC_OK);

		response.end(logContent.toString(), "UTF-8");
	}
	
	


	private static void serveView(HttpServerRequest request, 
			                      HttpServerResponse response, 
			                      Path path) {

		T.call(DynamicHandlerVertx.class);
		
		System.out.println("");
		System.out.println("");
		System.out.println("Request for: " + path.toRawPath());
		
		Map<String, String[]> parameters = new HashMap<>();
		MultiMap names = request.params();
		for(Map.Entry<String, String> entry : names.entries()) {
			String paramName = entry.getKey();
			String paramValue = entry.getValue();
			
			String[] paramValues = parameters.get(paramName);
			if(paramValues == null) {
				paramValues = new String[] {};
			}
			
			String[] newValues = Arrays.copyOf(paramValues, paramValues.length+1);
			newValues[paramValues.length] = paramValue;

			parameters.put(paramName, newValues);
		}
		
		for(Map.Entry<String, String[]> entry : parameters.entrySet()) {
			String paramName = entry.getKey();
			System.out.println(paramName + " " + entry.getValue()[0]);
		}
		

		sendSessionMessagesAccordingToCookies(request);

		executeBackend(path, parameters);

		boolean ifJSweet = ifJsOnlySetCookies(request, response);
		//boolean ifJSweet = false;

		NtroWindowServer window = newWindow(ifJSweet, path);
		//NtroWindowServer window = newWindow(true, path);

		if(!ifJSweet) {


			executeFrontendOnServer(request, response, path, parameters, window);

		}else {
			
			// FIXME: the taskGraph itself should have a notion
			//        of queued messages
			Ntro.messages().sendQueuedMessages();
		}
		
		

		setSessionCookie(response);
		

		// XXX on the server, the taskGraph is sync
		//     writeResponse will execute AFTER 
		//     every non-blocked task in webApp
		
		if(!response.ended()) {

			response.putHeader("content-type", "text/html; charset=utf-8");
			response.setStatusCode(Response.SC_OK);
			writeResponse(window, response);
		}
	}

	private static void executeBackend(Path path,
			                           Map<String, String[]> parameters) {

		NtroContext<User, SessionData> context = AquiletourMain.createNtroContext();
		
		try {

			AquiletourBackendRequestHandler.sendMessages(context, path, parameters);

		}catch(UserInputError e) {

			NtroErrorMessage displayErrorMessage = Ntro.messages().create(NtroErrorMessage.class);
			displayErrorMessage.setMessage(e.getMessage());
			Ntro.messages().send(displayErrorMessage);
		}
	}

	private static void sendCsvMessage(String semesterId, 
			                    String courseId, 
			                    HttpServerFileUpload upload) {
		
		System.out.println("TODO!");
		
		String fileName = "";
		String fileContent = "";

		AddStudentCsvMessage addStudentCsvMessage = Ntro.messages().create(AddStudentCsvMessage.class);

		addStudentCsvMessage.setTeacherId(Ntro.currentUser().getId());
		addStudentCsvMessage.setSemesterId(semesterId);
		addStudentCsvMessage.setCourseId(courseId);
		addStudentCsvMessage.setCsvString(fileContent);
		addStudentCsvMessage.setCsvFilename(fileName);

		Ntro.backendService().sendMessageToBackend(addStudentCsvMessage);
	}

	private static void sendSessionMessagesAccordingToCookies(HttpServerRequest baseRequest) {
		T.call(DynamicHandlerVertx.class);

		InitializeSessionMessage authenticateSessionUserMessage = Ntro.messages().create(InitializeSessionMessage.class);
		

		if(hasCookie(baseRequest, "session")) {
			String sessionString = UrlEncoded.decodeString(getCookie(baseRequest, "session"));

			sessionString = sessionString.replaceAll("%20", "");
			
			NtroSession session = Ntro.jsonService().fromString(NtroSession.class, sessionString);
			
			authenticateSessionUserMessage.setSessionUser(session.getUser());
		}

		Ntro.backendService().sendMessageToBackend(authenticateSessionUserMessage);
	}

	private static void executeFrontendOnServer(HttpServerRequest baseRequest, 
			                                    HttpServerResponse response, 
			                                    Path path,
			                                    Map<String, String[]> parameters,
			                                    NtroWindowServer window) {
		
		handleRedirections(baseRequest, response, path);

		NtroContext<User, SessionData> context = AquiletourMain.createNtroContext();

		// DEBUG
		// RootController rootController =  ControllerFactory.createRootController(RootController.class, "*", newWindow, context);

		// NORMAL
		RootController rootController =  ControllerFactory.createRootController(RootController.class, path, window, context);
		
		Ntro.messages().sendQueuedMessages();

		// Client controller executes after
		// to make sure modifications to the
		// models are loaded up
		GraphTraceConnector trace = rootController.execute();

		//trace.addGraphWriter(new GraphTraceWriterJdk(new File("__task_graphs__", path.toFileName())));

		AquiletourRequestHandler.sendMessages(context, path, parameters);

		// XXX: prepare for next request
		Ntro.reset();
	}


	private static void handleRedirections(HttpServerRequest baseRequest, HttpServerResponse response, Path path) {
		// FIXME: there must be a better way to redirect to login
		if(!path.startsWith("connexion")) {
			Ntro.messages().registerHandler(ShowLoginMessage.class, new MessageHandler<ShowLoginMessage>() {
				@Override
				public void handle(ShowLoginMessage message) {
					T.call(this);
					
					String messageToUser = message.getMessageToUser();
					
					// XXX: on the server, ShowLoginMessage is a redirect to /connexion?message=""
					String redirectUrl = "/connexion?message=" + UrlEncoded.encodeString(messageToUser);
						
					writeRedirect(response, redirectUrl);
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
					writeRedirect(response, redirectUrl);
				}
			});
		}
	}

	private static void writeRedirect(HttpServerResponse response, String redirectUrl) {
		T.call(DynamicHandlerVertx.class);

		response.putHeader("location", redirectUrl);
		response.setStatusCode(Response.SC_TEMPORARY_REDIRECT);
		response.end();
	}


	private static void setSessionCookie(HttpServerResponse response) {
		T.call(DynamicHandlerVertx.class);
		
		NtroSession session = Ntro.currentSession();
		User user = (User) session.getUser();
		session.setUser(user.toSessionUser());
		setCookie(response, "session", Ntro.jsonService().toString(session));
	}

	private static boolean ifJsOnlySetCookies(HttpServerRequest baseRequest, HttpServerResponse response) {
		T.call(DynamicHandlerVertx.class);
		
		boolean ifJSweet = false;

		if(baseRequest.getParam("nojsweet") != null) {
			
			setCookie(response, "jsweet" , "false" );
			ifJSweet = false;

		} else if(baseRequest.getParam("jsweet") != null) {
			
			setCookie(response, "jsweet" , "true" );
			ifJSweet = true;
			
		}else if(hasCookie(baseRequest, "jsweet")) {
			
			String jsOnlyCookie = getCookie(baseRequest, "jsweet");
			ifJSweet = Boolean.valueOf(jsOnlyCookie);

		}
		
		return ifJSweet;

	}

	private static NtroWindowServer newWindow(boolean ifJSweet, Path path) {
		T.call(DynamicHandlerVertx.class);

		NtroWindowServer newWindow;

		if(ifJSweet) {

			newWindow = new NtroWindowServer("/private/index.html");
			
		}else {

			newWindow = new NtroWindowServer("/private/nojsweet.html");

		} 

		newWindow.setCurrentPath(path);

		return newWindow;
	}
	
	private static boolean hasCookie(HttpServerRequest request, String name) {
		T.call(DynamicHandlerVertx.class);
		
		return request.getCookie(name) != null;
	}

	private static String getCookie(HttpServerRequest request, String name) {
		T.call(DynamicHandlerVertx.class);
		
		return request.getCookie(name).getValue();
	}

	@SuppressWarnings("unused")
	private static void eraseCookie(HttpServerResponse response, String name) {
		T.call(DynamicHandlerVertx.class);
		
		Cookie cookie = Cookie.cookie(name, "");
		cookie.setPath("/");
		cookie.setMaxAge(0);

		response.addCookie(cookie);
	}

	private static void setCookie(HttpServerResponse response, String name, String value) {
		T.call(DynamicHandlerVertx.class);
		
		String trimmedValue = value.replace(" ", "%20");

		String urlEncodedString = UrlEncoded.encodeString(trimmedValue);
		
		Cookie cookie = Cookie.cookie(name, urlEncodedString);
		cookie.setPath("/");

		response.addCookie(cookie);
	}

	private static void writeResponse(NtroWindowServer window, HttpServerResponse response) {
		T.call(DynamicHandlerVertx.class);

		StringBuilder builder = new StringBuilder();
		window.writeHtml(builder);

		response.end(builder.toString(), "UTF-8");
	}
}

