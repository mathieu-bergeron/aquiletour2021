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

package ca.aquiletour.server;

import java.io.IOException;
import java.security.Principal;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;

import ca.aquiletour.server.http.ModelHandler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;

import ca.aquiletour.core.AquiletourMain;
import ca.aquiletour.core.messages.time.TimePassesMessage;
import ca.aquiletour.server.backend.semester_list.SemesterListManager;
import ca.aquiletour.server.backend.users.UserManager;
import ca.aquiletour.server.http.DynamicHandler;
import ca.aquiletour.server.http.MessageHandler;
import ca.aquiletour.server.http.ResourceHandler;
import ca.aquiletour.server.http.WebSocketHandler;
import ca.aquiletour.web.ViewLoaderRegistrationWeb;
import ca.ntro.core.Constants;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskAsync;
import ca.ntro.services.Ntro;
import ca.ntro.services.NtroInitializationTask;

public class AquiletourMainServer extends NtroTaskAsync {
	
	private Timer timePassesTimer;

	@Override
	protected void runTaskAsync() {
		T.call(this);
		

		// TODO: fetching option (parsed by InitializationTask)
		String mainDirectory = getPreviousTask(NtroInitializationTask.class, Constants.INITIALIZATION_TASK_ID).getOption("mainDirectory");

		ViewLoaderRegistrationWeb.registerViewLoaders();
		
		AquiletourMain.registerSerializableClasses();
		
		Ntro.jsonService().setPrettyPrinting(true);
		
		sendTimePassesMessages();

		UserManager.initialize(new ModelStoreSync(Ntro.modelStore()));
		SemesterListManager.initialize(new ModelStoreSync(Ntro.modelStore()));

		// Start server
		// always do server-side rendering (except for static resources: Urls starting with _resources)
		// always include javascript content (it can be ignored by nojs clients)
		try {
			startServer();
		} catch (Exception e) {
			// TODO: Ntro should provide a Error.fatal method to call on fatal errors
			e.printStackTrace(System.err);
			Ntro.appCloser().close();
		}

		notifyTaskFinished();
	}

	private void sendTimePassesMessages() {
		T.call(this);

		long periodSeconds = 5;

		timePassesTimer =  new Timer();
		timePassesTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				T.call(this);
				
				TimePassesMessage timePassesMessage = Ntro.messages().create(TimePassesMessage.class);
				timePassesMessage.setElapsedTimeSeconds(periodSeconds);
				timePassesMessage.setCurrentTime(Ntro.calendar().now());
				
				Ntro.messages().send(timePassesMessage);
			}
		}, 0, periodSeconds * 1000);
	}

	@Override
	protected void onFailure(Exception e) {
		System.err.println("Ntro initialization failed");
		e.printStackTrace(System.err);
	}

	private Server createServer(int port) throws ServletException {
		T.call(this);

		Server server = new Server(port);

		// TODO: add HTTPS, WS and WSS connectors
        server.addConnector(new ServerConnector(server));

        // NOTE: HandlerList stops after first successful answer
        HandlerList handlers = new HandlerList();

		handlers.addHandler(ModelHandler.createModelHandler(Constants.MODELS_URL_PREFIX));
		handlers.addHandler(ResourceHandler.createResourceHandler(Constants.RESOURCES_URL_PREFIX, "/public"));
		handlers.addHandler(WebSocketHandler.createWebSocketHandler(Constants.SOCKET_PREFIX));
		handlers.addHandler(MessageHandler.createMessageHandler(Constants.HTTP_PREFIX));
		handlers.addHandler(DynamicHandler.createDynamicHandler("/", "/private"));

        server.setHandler(handlers);

		return server;
	}

	private void startServer() throws Exception, IOException, InterruptedException {
		T.call(this);

        int port = 8080;
        Server server = createServer(port);

        server.start();

        System.out.println(String.format("\n\nListening on http://localhost:%s", port));
        System.out.println("\n\nPress Enter to stop the server...");

        System.in.read();
        
        timePassesTimer.cancel();
        
        server.stop();

        server.join();
	}

}
