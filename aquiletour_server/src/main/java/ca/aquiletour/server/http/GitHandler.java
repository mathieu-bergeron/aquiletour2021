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

import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.util.UrlEncoded;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.messages.AddStudentCsvMessage;
import ca.aquiletour.core.messages.AuthenticateSessionUserMessage;
import ca.aquiletour.core.messages.git.RegisterRepoMessage;
import ca.aquiletour.core.models.users.Guest;
import ca.aquiletour.core.models.users.Teacher;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.home.ShowHomeMessage;
import ca.aquiletour.core.pages.login.ShowLoginMessage;
import ca.aquiletour.core.pages.root.RootController;
import ca.aquiletour.web.AquiletourBackendRequestHandler;
import ca.aquiletour.web.AquiletourRequestHandler;
import ca.ntro.core.Path;
import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.mvc.ControllerFactory;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.GraphTraceConnector;
import ca.ntro.jdk.FileLoader;
import ca.ntro.jdk.FileLoaderDev;
import ca.ntro.jdk.random.SecureRandomString;
import ca.ntro.jdk.web.NtroWindowServer;
import ca.ntro.messages.MessageHandler;
import ca.ntro.messages.NtroMessage;
import ca.ntro.services.Ntro;
import ca.ntro.users.Session;

public class GitHandler extends AbstractHandler {

	public static ContextHandler createGitHandler(String urlPrefix) {
		T.call(GitHandler.class);

        // Http handler
        ContextHandler dynamicContext = new ContextHandler();
        dynamicContext.setContextPath(urlPrefix);
        
        // XXX: dev-only, disable all caching
        dynamicContext.setInitParameter("cacheControl", "no-store,no-cache,must-revalidate,max-age=0,public");
        dynamicContext.setInitParameter("maxCacheSize", "0");
        
        dynamicContext.setHandler(new GitHandler(urlPrefix));
        
        // TODO: dev-only: load resources from ./src/main/ressources NOT ./build/resources/...
        //staticFilesContext.setResourceBase("./src/main/resources");
        
        return dynamicContext;
	}
	
	private String urlPrefix;

	public GitHandler(String urlPrefix) {
		T.call(this);

		this.urlPrefix = urlPrefix;
	}
	
	@Override
	public void handle(String target, 
			           Request baseRequest, 
			           HttpServletRequest request, 
			           HttpServletResponse response)
			    
			    throws IOException, ServletException {
		
		T.call(this);
		
        if (request.getMethod().equals("POST")) {
        	
        	String body = ModelHandler.readBody(baseRequest);
        	
        	System.out.println("GitHandler::body " + body);

			NtroMessage message = Ntro.jsonService().fromString(NtroMessage.class, body);
			
			if(message instanceof RegisterRepoMessage) {

				handleRegisterRepoMessage(baseRequest, response, (RegisterRepoMessage) message);
				
			}else {

				Log.error("[GitHandler] Unsupported message '" + Ntro.introspector().ntroClassFromObject(message).simpleName() + "'");
				response.setStatus(HttpStatus.NO_CONTENT_204);
				baseRequest.setHandled(true);
			}

			response.setStatus(HttpStatus.OK_200);
			baseRequest.setHandled(true);

        }else {

            Log.error("[GitHandler] Invalid HTTP method '" + request.getMethod() + "'!");
            response.setStatus(HttpStatus.METHOD_NOT_ALLOWED_405);
            baseRequest.setHandled(true);
        }
	}

	private void handleRegisterRepoMessage(Request baseRequest, 
			HttpServletResponse response,
			RegisterRepoMessage message) {
		
	}
}
