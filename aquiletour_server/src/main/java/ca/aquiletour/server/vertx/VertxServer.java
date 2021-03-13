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
			 *  /_resources/path : servir fichier statique (selon le path)
			 *  /_messages       : répondre à un message (message dans le body POST)
			 *  /                : démarrer une App et répondre en HTML
			 *  
			 *  Chaque session a:
			 *  + Une file de messages. Quand on répond à un message, on flush la file de messages
			 *  
			 *  En plus un usager peut avoir des sockets
			 *  + Quand on répond à un message, on flush une copie de la file sur tout les sockets de l'usager
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
