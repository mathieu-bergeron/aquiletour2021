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

import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;

import ca.aquiletour.core.messages.git.GetCommitsForPath;
import ca.aquiletour.core.messages.git.OnCloneFailed;
import ca.aquiletour.core.messages.git.OnClone;
import ca.aquiletour.core.messages.git.RegisterExercise;
import ca.aquiletour.core.messages.git.RegisterRepo;
import ca.aquiletour.core.pages.git.commit_list.CommitListModel;
import ca.aquiletour.server.backend.git.GitMessages;
import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;
import ca.ntro.services.Ntro;

public class GitHandler extends AbstractHandler {
	
	private static Random random = new Random(new Date().toInstant().getEpochSecond());

	public static ContextHandler createGitHandler(String urlPrefix) {
		T.call(GitHandler.class);

        // Http handler
        ContextHandler dynamicContext = new ContextHandler();
        dynamicContext.setContextPath(urlPrefix);
        
        // XXX: dev-only, disable all caching
        dynamicContext.setInitParameter("cacheControl", "no-store,no-cache,must-revalidate,max-age=0,public");
        dynamicContext.setInitParameter("maxCacheSize", "0");
        
        dynamicContext.setHandler(new GitHandler());
        
        // TODO: dev-only: load resources from ./src/main/ressources NOT ./build/resources/...
        //staticFilesContext.setResourceBase("./src/main/resources");
        
        return dynamicContext;
	}
	
	@Override
	public void handle(String target, 
			           Request baseRequest, 
			           HttpServletRequest request, 
			           HttpServletResponse response)
			    
			    throws IOException, ServletException {
		
		T.call(this);
		
        if (request.getMethod().equals("POST") || request.getMethod().equals("GET")) {
        	T.here();
        	response.getOutputStream().write(("{\r\n" + 
        			"  \"_C\": \"CommitListModel\",\r\n" + 
        			"  \"courseId\": \"H2021\",\r\n" + 
        			"  \"semesterId\": \"mathieu.bergeron/StruDon\",\r\n" + 
        			"  \"groupId\": \"01\",\r\n" + 
        			"  \"exercisePath\": \"/TP1/Exercice 1\",\r\n" + 
        			"  \"studentId\": \"1234500\",\r\n" + 
        			"  \"commits\": {\r\n" + 
        			"    \"_C\": \"ObservableCommitList\",\r\n" + 
        			"    \"value\": [\r\n" + 
        			"      {\r\n" + 
        			"        \"_C\": \"Commit\",\r\n" + 
        			"        \"commitId\": \"521747298a3790fde1710f3aa2d03b55020575a1\",\r\n" + 
        			"        \"exercisePathIfCompleted\": \"/TP1/Exercice 1\",\r\n" + 
        			"        \"commitMessageFirstLine\": \"test\",\r\n" + 
        			"        \"commitMessage\": \"test\",\r\n" + 
        			"        \"timeStamp\": \"976924800000\",\r\n" + 
        			"        \"estimatedEffort\": 12.0,\r\n" + 
        			"        \"modifiedFiles\": [\r\n" + 
        			"          {\r\n" + 
        			"            \"_C\": \"CommitFile\",\r\n" + 
        			"            \"path\": \"test/\",\r\n" + 
        			"            \"estimatedEffort\": 12,\r\n" + 
        			"            \"exercisePath\": \"/TP1/Exercice 1\",\r\n" + 
        			"            \"message\": \"no field in database for now\"\r\n" + 
        			"          },\r\n" + 
        			"          {\r\n" + 
        			"            \"_C\": \"CommitFile\",\r\n" + 
        			"            \"path\": \"test/\",\r\n" + 
        			"            \"estimatedEffort\": 12,\r\n" + 
        			"            \"exercisePath\": \"/TP1/Exercice 1\",\r\n" + 
        			"            \"message\": \"no field in database for now\"\r\n" + 
        			"          },\r\n" + 
        			"          {\r\n" + 
        			"            \"_C\": \"CommitFile\",\r\n" + 
        			"            \"path\": \"test/\",\r\n" + 
        			"            \"estimatedEffort\": 12,\r\n" + 
        			"            \"exercisePath\": \"/TP1/Exercice 1\",\r\n" + 
        			"            \"message\": \"no field in database for now\"\r\n" + 
        			"          }\r\n" + 
        			"        ]\r\n" + 
        			"      },\r\n" + 
        			"      {\r\n" + 
        			"        \"_C\": \"Commit\",\r\n" + 
        			"        \"commitId\": \"521747298a3790fde1710f3aa2d03b55020575a2\",\r\n" + 
        			"        \"exercisePathIfCompleted\": \"/\",\r\n" + 
        			"        \"commitMessageFirstLine\": \"test\",\r\n" + 
        			"        \"commitMessage\": \"test\",\r\n" + 
        			"        \"timeStamp\": \"979171200000\",\r\n" + 
        			"        \"estimatedEffort\": 16.0,\r\n" + 
        			"        \"modifiedFiles\": [\r\n" + 
        			"          {\r\n" + 
        			"            \"_C\": \"CommitFile\",\r\n" + 
        			"            \"path\": \"test/\",\r\n" + 
        			"            \"estimatedEffort\": 16,\r\n" + 
        			"            \"exercisePath\": \"/TP1/Exercice 1\",\r\n" + 
        			"            \"message\": \"no field in database for now\"\r\n" + 
        			"          },\r\n" + 
        			"          {\r\n" + 
        			"            \"_C\": \"CommitFile\",\r\n" + 
        			"            \"path\": \"test/\",\r\n" + 
        			"            \"estimatedEffort\": 16,\r\n" + 
        			"            \"exercisePath\": \"/TP1/Exercice 1\",\r\n" + 
        			"            \"message\": \"no field in database for now\"\r\n" + 
        			"          },\r\n" + 
        			"          {\r\n" + 
        			"            \"_C\": \"CommitFile\",\r\n" + 
        			"            \"path\": \"test/\",\r\n" + 
        			"            \"estimatedEffort\": 16,\r\n" + 
        			"            \"exercisePath\": \"/TP1/Exercice 1\",\r\n" + 
        			"            \"message\": \"no field in database for now\"\r\n" + 
        			"          }\r\n" + 
        			"        ]\r\n" + 
        			"      },\r\n" + 
        			"      {\r\n" + 
        			"        \"_C\": \"Commit\",\r\n" + 
        			"        \"commitId\": \"521747298a3790fde1710f3aa2d03b55020575a3\",\r\n" + 
        			"        \"exercisePathIfCompleted\": \"/TP1/Exercice 2\",\r\n" + 
        			"        \"commitMessageFirstLine\": \"test\",\r\n" + 
        			"        \"commitMessage\": \"test\",\r\n" + 
        			"        \"timeStamp\": \"979257600000\",\r\n" + 
        			"        \"estimatedEffort\": 16.0,\r\n" + 
        			"        \"modifiedFiles\": [\r\n" + 
        			"          {\r\n" + 
        			"            \"_C\": \"CommitFile\",\r\n" + 
        			"            \"path\": \"test/\",\r\n" + 
        			"            \"estimatedEffort\": 16,\r\n" + 
        			"            \"exercisePath\": \"/TP1/Exercice 1\",\r\n" + 
        			"            \"message\": \"no field in database for now\"\r\n" + 
        			"          },\r\n" + 
        			"          {\r\n" + 
        			"            \"_C\": \"CommitFile\",\r\n" + 
        			"            \"path\": \"test/\",\r\n" + 
        			"            \"estimatedEffort\": 16,\r\n" + 
        			"            \"exercisePath\": \"/TP1/Exercice 1\",\r\n" + 
        			"            \"message\": \"no field in database for now\"\r\n" + 
        			"          }\r\n" + 
        			"        ]\r\n" + 
        			"      },\r\n" + 
        			"      {\r\n" + 
        			"        \"_C\": \"Commit\",\r\n" + 
        			"        \"commitId\": \"521747298a3790fde1710f3aa2d03b55020575aa\",\r\n" + 
        			"        \"exercisePathIfCompleted\": \"/\",\r\n" + 
        			"        \"commitMessageFirstLine\": \"test\",\r\n" + 
        			"        \"commitMessage\": \"test\",\r\n" + 
        			"        \"timeStamp\": \"971136000000\",\r\n" + 
        			"        \"estimatedEffort\": 20.0,\r\n" + 
        			"        \"modifiedFiles\": [\r\n" + 
        			"          {\r\n" + 
        			"            \"_C\": \"CommitFile\",\r\n" + 
        			"            \"path\": \"test/\",\r\n" + 
        			"            \"estimatedEffort\": 20,\r\n" + 
        			"            \"exercisePath\": \"/TP1/Exercice 1\",\r\n" + 
        			"            \"message\": \"no field in database for now\"\r\n" + 
        			"          },\r\n" + 
        			"          {\r\n" + 
        			"            \"_C\": \"CommitFile\",\r\n" + 
        			"            \"path\": \"test/\",\r\n" + 
        			"            \"estimatedEffort\": 20,\r\n" + 
        			"            \"exercisePath\": \"/TP1/Exercice 1\",\r\n" + 
        			"            \"message\": \"no field in database for now\"\r\n" + 
        			"          },\r\n" + 
        			"          {\r\n" + 
        			"            \"_C\": \"CommitFile\",\r\n" + 
        			"            \"path\": \"test/\",\r\n" + 
        			"            \"estimatedEffort\": 20,\r\n" + 
        			"            \"exercisePath\": \"/TP1/Exercice 1\",\r\n" + 
        			"            \"message\": \"no field in database for now\"\r\n" + 
        			"          }\r\n" + 
        			"        ]\r\n" + 
        			"      }\r\n" + 
        			"    ]\r\n" + 
        			"  }\r\n" + 
        			"}").getBytes());
        	System.out.println(ModelHandler.readBody(baseRequest));
			response.setStatus(HttpStatus.OK_200);
			baseRequest.setHandled(true);

        }else {

            Log.error("[GitHandler] Invalid HTTP method '" + request.getMethod() + "'!");
            response.setStatus(HttpStatus.METHOD_NOT_ALLOWED_405);
            baseRequest.setHandled(true);
        }
	}

	private void handleGetCommitListMessage(Request baseRequest, 
			                                HttpServletResponse response,
			                                GetCommitsForPath message) {

		System.out.println("handleGetCommitListMessage");
		
		ModelLoader modelLoader = Ntro.modelStore().getLoader(CommitListModel.class, "admin", "bob");
		modelLoader.execute();

		CommitListModel model = (CommitListModel) modelLoader.getModel();
		
		try {

			response.getOutputStream().write(Ntro.jsonService().toString(model).getBytes());

		} catch (IOException e) {

			Log.fatalError("Cannot write model", e);
		}
	}

	private void handleRegisterRepoMessage(Request baseRequest, 
			                               HttpServletResponse response,
			                               RegisterRepo message) {
		
		System.out.println("RegisterRepoMessage");
		
		Timer timer = new Timer();
		
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				T.call(this);

				if(random.nextBoolean()) {
					
					OnClone onCloneMessage = Ntro.messages().create(OnClone.class);
					onCloneMessage.loadStudentExerciseInfo(message);
					GitMessages.sendReply(onCloneMessage);
					
				}else {
					
					OnCloneFailed onCloneFailedMessage = Ntro.messages().create(OnCloneFailed.class);
					onCloneFailedMessage.loadStudentExerciseInfo(message);
					GitMessages.sendReply(onCloneFailedMessage);
				}
			}
			
		}, 1000); // 1 second
		
	}
}
