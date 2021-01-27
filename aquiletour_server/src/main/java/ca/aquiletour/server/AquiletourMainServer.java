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

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;

import ca.aquiletour.server.http.DynamicHandler;
import ca.aquiletour.server.http.HttpConnector;
import ca.aquiletour.server.http.ResourceHandler;
import ca.ntro.core.Ntro;
import ca.ntro.core.initialization.NtroInitializationTask;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskImpl;

public class AquiletourMainServer extends NtroTaskImpl {

	@Override
	protected void initializeTask() {
	}

	@Override
	protected void runTask() {
		T.call(this);
		
		// TODO: fetching option (parsed by InitializationTask)
		String mainDirectory = getPreviousTask(NtroInitializationTask.class).getOption("mainDirectory");
		T.values(mainDirectory);
		
		// Start server
		// always do server-side rendering (except for static resources: Urls starting with _R)
		// always include javascript content (it can be ignored by nojs clients)
		try {
			startServer();
		} catch (Exception e) {
			// TODO: Ntro should provide a Error.fatal method to call on fatal errors
			e.printStackTrace(System.err);
			Ntro.appCloser().close();
		}
	}

	@Override
	protected void onFailure(Exception e) {
		System.err.println("Ntro initialization failed");
		e.printStackTrace(System.err);
	}
	
	private Server createServer(int port) {
		T.call(this);
		
		Server server = new Server(port);

		// TODO: add HTTPS, WS and WSS connectors
        server.addConnector(new ServerConnector(server));

        // NOTE: HandlerList stops after first successful answer
        HandlerList handlers = new HandlerList();

        handlers.addHandler(ResourceHandler.createResourceHandler("/_R", "src/main/resources/public"));
        handlers.addHandler(DynamicHandler.createDynamicHandler("/", "src/main/resources/private"));

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
        
        server.stop();

        server.join();
	}

}
