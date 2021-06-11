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

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;

import ca.aquiletour.server.http.ModelHandler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;

import ca.aquiletour.core.AquiletourMain;
import ca.aquiletour.core.messages.time.TimePassesMessage;
import ca.aquiletour.server.AquiletourConfig;
import ca.aquiletour.server.backend.semester_list.SemesterListManager;
import ca.aquiletour.server.backend.users.UserManager;
import ca.aquiletour.server.http.DynamicHandler;
import ca.aquiletour.server.http.MessageHandler;
import ca.aquiletour.server.http.ResourceHandler;
import ca.aquiletour.server.http.WebSocketHandler;
import ca.aquiletour.web.ViewLoaderRegistrationWeb;
import ca.ntro.backend.BackendError;
import ca.ntro.core.Constants;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskAsync;
import ca.ntro.jdk.digest.PasswordDigest;
import ca.ntro.services.ModelStoreSync;
import ca.ntro.services.Ntro;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.bridge.BridgeOptions;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.sockjs.SockJSBridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.ext.web.handler.sockjs.SockJSHandlerOptions;
import io.vertx.ext.web.handler.sockjs.SockJSSocket;

public class AquiletourMainServerVertx extends NtroTaskAsync {
	
	private Timer timePassesTimer;

	@Override
	protected void runTaskAsync() {
		T.call(this);

		PasswordDigest.initialize(((AquiletourConfig) Ntro.config()).getPasswordSalt());

		ViewLoaderRegistrationWeb.registerViewLoaders();
		
		AquiletourMain.registerSerializableClasses();
		
		if(Ntro.config().isProd()) {
			Ntro.jsonService().setPrettyPrinting(true);
		}else {
			Ntro.jsonService().setPrettyPrinting(false);
		}
		
		sendTimePassesMessages();

		try {

			UserManager.initialize(new ModelStoreSync(Ntro.modelStore()));
			SemesterListManager.initialize(new ModelStoreSync(Ntro.modelStore()));

		} catch (BackendError e) {
			Log.error("Could not initialize: " + e.getMessage());
		}

		try {
			startServer();
		} catch (Exception e) {
			e.printStackTrace(System.err);
			Ntro.appCloser().close();
		}

		notifyTaskFinished();
	}

	private void sendTimePassesMessages() {
		T.call(this);

		long periodSeconds = ca.aquiletour.core.Constants.TIME_PASSES_PERIOD_SECONDS;

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

	private void startServer() throws Exception, IOException, InterruptedException {
		T.call(this);

		if(!Ntro.config().isProd()) {
			System.setProperty("vertxweb.environment", "dev");
		}
		
		VertxOptions vertxOptions = new VertxOptions();
		if(Ntro.config().isProd()) {
			vertxOptions.setWorkerPoolSize(1000);
		}else {
			vertxOptions.setWorkerPoolSize(3);
		}

		Vertx vertx = Vertx.vertx(vertxOptions);

		vertx.exceptionHandler(exception -> {
			exception.printStackTrace();
		});
		
		EventBus eventBus = vertx.eventBus();
		eventBus.registerDefaultCodec(TextMessage.class,new TextMessageCodec());
		
		Router router = Router.router(vertx);

		router.route(HttpMethod.POST, "/*").handler(BodyHandler.create());

		router.route(HttpMethod.POST, Constants.MESSAGES_URL_PATH_HTTP + "*").blockingHandler(routingContext -> {

			MessageHandlerVertx.handleRequest(routingContext);
		});

		router.route(HttpMethod.POST, Constants.MODELS_URL_PREFIX + "*").blockingHandler(routingContext -> {

			ModelHandlerVertx.handle(routingContext);

		});

		ResourceHandlerVertx resourceHandler = ResourceHandlerVertx.createResourceHandler(Constants.RESOURCES_URL_PREFIX, "/public");
		router.route(Constants.RESOURCES_URL_PREFIX + "*").blockingHandler(routingContext -> {
			resourceHandler.handle(routingContext);
		});
		
		SockJSHandlerOptions sockJSOptions = new SockJSHandlerOptions();

		sockJSOptions.setLocalWriteHandler(true);
		SockJSHandler sockJSHandler = SockJSHandler.create(vertx, sockJSOptions);
		
		
		router.mountSubRouter(Constants.MESSAGES_URL_PATH_SOCKET, sockJSHandler.socketHandler(socket -> {
			
			socket.handler(messageBuffer -> {

				MessageHandlerVertx.handleMessage(socket, messageBuffer);
			});
		}));



		router.route("/*").blockingHandler(routingContext -> {

			DynamicHandlerVertx.handle(routingContext);
		});
		
		router.errorHandler(500, rc -> {
		  Throwable failure = rc.failure();
		  if (failure != null) {
			failure.printStackTrace();
		  }
		});
		
		HttpServer server = vertx.createHttpServer();
		server.requestHandler(router);
		
		server.listen(8080);
	}
}
