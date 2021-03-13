package ca.aquiletour.server.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;

public class VertxServer {
	
	public static void main(String[] args) {
		System.setProperty("vertxweb.environment", "dev");
		
		Vertx vertx = Vertx.vertx();
		
		HttpServer server = vertx.createHttpServer();

		server.requestHandler(request -> {
			
			/* TODO
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 */
			
			
			

		  // This handler gets called for each request that arrives on the server
		  HttpServerResponse response = request.response();
		  response.putHeader("content-type", "text/plain");

		  // Write to the response and end it
		  response.end("Hello World!");
		});

		server.listen(8080);
	}
}
