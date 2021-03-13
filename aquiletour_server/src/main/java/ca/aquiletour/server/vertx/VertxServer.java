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
			 *  /_httpmessages   : messages reçu par le body d'un POST
			 *  /_socketmessages : messages reçu via une connexion SockJS
			 *  /                : démarrer une App et répondre en HTML
			 *  
			 *  Pour les deux réceptions de messages
			 *  + Envoyer le message au Backend
			 *  + Flusher la file de messages de l'usager
			 *    -- via la réponse HTTP
			 *    -- ou via le socket
			 *    
			 *  Chaque session a:
			 *  + Une file de messages. Quand on répond à un message, on flush la file de messages
			 *  
			 *  En plus un usager peut avoir des sockets
			 *  + Quand on répond à un message, on flush une copie de la file sur tout les sockets de l'usager
			 *  
			 *  
			 *  ID de session et ID de lastLogin
			 *  
			 *  https://neilmadden.blog/2018/08/30/moving-away-from-uuids/
			 *  
			 *  20-byte SecureRandom encodé en base64
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
