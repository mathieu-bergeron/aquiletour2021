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


import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.jetty.server.Response;

import ca.aquiletour.server.registered_sockets.RegisteredSocketsSockJS;
import ca.aquiletour.server.registered_sockets.RegisteredSocketsWebSocket;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;
import ca.ntro.messages.ntro_messages.NtroRegisterSocketMessage;
import ca.ntro.services.Ntro;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.sockjs.SockJSSocket;

public class MessageHandlerVertx {

	public static void handleRequest(RoutingContext routingContext) {
		T.call(MessageHandlerVertx.class);
		
		HttpServerRequest request = routingContext.request();
		HttpServerResponse response = routingContext.response();
		
        if (request.method().equals(HttpMethod.POST)) {
        	
        	String messageText = routingContext.getBodyAsString();

			Log.info("[handleRequest] messageText: " + messageText);

			try {

				NtroMessage message = Ntro.jsonService().fromString(NtroMessage.class, messageText);
				Ntro.backendService().sendMessageToBackend(message);

			}catch(ClassCastException e) {

				Log.warning("[MessageHandlerVertx] not a NtroMessage: " + messageText);
			}

			response.setStatusCode(Response.SC_OK);
			response.end();

        }else {

            Log.error("[MessageHandlerVertx] Invalid HTTP method " + request.method() + "!");
            response.setStatusCode(Response.SC_METHOD_NOT_ALLOWED);
            response.end();
        }
	}

	public static void handleMessage(SockJSSocket socket, Buffer messageBuffer) {
		T.call(MessageHandlerVertx.class);
		
		String messageText = null;
		if(messageBuffer != null) {
			messageText = messageBuffer.toString();
		}

		if(messageText != null && !messageText.isEmpty()) {
			Log.info("[handleMessage] messageText: " + messageText);

			try {

				NtroMessage message = Ntro.jsonService().fromString(NtroMessage.class, messageText);
				
				if(message instanceof NtroRegisterSocketMessage) {
					
					NtroRegisterSocketMessage  ntroRegisterSocketMessage = (NtroRegisterSocketMessage) message;
					
					RegisteredSocketsSockJS.registerUserSocket(ntroRegisterSocketMessage.getAuthToken(), 
							                                   ntroRegisterSocketMessage.getUser(), 
							                                   socket);

				}else {
					
					Ntro.backendService().sendMessageToBackend(message);
				}

			}catch(ClassCastException e) {

				Log.warning("[MessageHandlerVertx] not a NtroMessage: " + messageText);
			}

		}
	}
}
