package ca.aquiletour.server.registered_sockets;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ca.ntro.backend.BackendError;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;
import ca.ntro.messages.ntro_messages.NtroInvokeValueMethodMessage;
import ca.ntro.services.Ntro;
import ca.ntro.stores.DocumentPath;
import ca.ntro.stores.ValuePath;
import ca.ntro.users.NtroUser;
import io.vertx.core.impl.ConcurrentHashSet;
import io.vertx.ext.web.handler.sockjs.SockJSSocket;

public class RegisteredSocketsSockJS {

	private static Map<DocumentPath, Set<String>> tokensByObservedPath = Ntro.collections().concurrentMap(new HashMap<>());
	private static Map<String, Set<DocumentPath>> observedPathsByToken = Ntro.collections().concurrentMap(new HashMap<>());

	private static Map<String, Set<String>> tokensByUserId = Ntro.collections().concurrentMap(new HashMap<>());
	private static Map<String, String> userIdByToken = Ntro.collections().concurrentMap(new HashMap<>());

	private static Map<String, Set<SockJSSocket>> socketsByToken = Ntro.collections().concurrentMap(new HashMap<>());
	private static Map<SockJSSocket, String> tokenBySocket = Ntro.collections().concurrentMap(new HashMap<>());

	public static void onUserChanges(String oldAuthToken, String authToken, NtroUser user) {
		T.call(RegisteredSocketsSockJS.class);
		
		Set<SockJSSocket> sockets = socketsByToken.get(oldAuthToken);
		
		if(sockets != null) {
			synchronized (sockets) {
				for(SockJSSocket socket: sockets) {

					deregisterSocket(socket);

					registerUserSocket(authToken, user, socket);
				}
			}
		}
	}

	public static void registerUserSocket(String authToken, NtroUser user, SockJSSocket socket) {
		T.call(RegisteredSocketsSockJS.class);
		
		deregisterSocketIfExists(socket);

		registerSocket(authToken, socket);
		
		Set<String> userTokens = tokensByUserId.get(user.getId());
		if(userTokens == null) {
			userTokens = Ntro.collections().concurrentSet(new HashSet<>());
			tokensByUserId.put(user.getId(), userTokens);
		}
		userTokens.add(authToken);
		
		userIdByToken.put(authToken, user.getId());
		
		System.out.println("registered user socket for " + user.getId() + " " + authToken);
	}

	private static void registerSocket(String authToken, SockJSSocket socket) {
		T.call(RegisteredSocketsSockJS.class);
		
		if(authToken == null) {
			Log.error("[registerSocket] authToken is null");
			return;
		}
		
		Set<SockJSSocket> sockets = socketsByToken.get(authToken);
		if(sockets == null) {
			sockets = new ConcurrentHashSet<SockJSSocket>();
			socketsByToken.put(authToken, sockets);
		}

		sockets.add(socket);
		tokenBySocket.put(socket, authToken);
		
		Log.info("[registerSocket] " + sockets.size() + " socket(s) for authToken " + authToken);
		
		socket.endHandler(t -> {
			deregisterSocket(socket);
		});
	}

	private static void deregisterSocketIfExists(SockJSSocket socket) {
		T.call(RegisteredSocketsSockJS.class);

		if(tokenBySocket.containsKey(socket)) {
			deregisterSocket(socket);
		}
	}

	public static void deregisterSocket(SockJSSocket socket) {
		T.call(RegisteredSocketsSockJS.class);

		
		String authToken = tokenBySocket.get(socket);
		boolean removed = false;
		boolean lastAuthTokenSocket = false;
			
		if(authToken != null) {

			tokenBySocket.remove(socket);
			
			Set<SockJSSocket> sockets = socketsByToken.get(authToken);
			if(sockets != null) {
				
				removed = sockets.remove(socket);
			}
			
			lastAuthTokenSocket = sockets.isEmpty();
		}

		if(removed) {
			System.out.println("deregistered socket for " + authToken);
		}

		if(lastAuthTokenSocket) {

			String userId = null;
			if(authToken != null) {
				userId = userIdByToken.get(authToken);
			}
			
			if(userId != null) {

				deregisterUser(authToken, userId);
				deregisterModelObservers(authToken);

			}else {
				
				Log.warning("[deregisterSocket] userId not found for " + authToken);
			}
		}
	}

	private static void deregisterUser(String authToken, String userId) {
		T.call(RegisteredSocketsSockJS.class);

		Set<String> userTokens = tokensByUserId.get(userId);

		if(userTokens != null) {
			userTokens.remove(authToken);
			if(userTokens.isEmpty()) {
				tokensByUserId.remove(userId);
			}
		}

		userIdByToken.remove(authToken);
	}

	private static void deregisterModelObservers(String authToken) {
		T.call(RegisteredSocketsSockJS.class);
		
		if(authToken == null) {
			Log.error("[deregisterModelObservers] authToken is null");
			return;
		}

		Set<DocumentPath> observedPaths = observedPathsByToken.get(authToken);

		if(observedPaths != null) {

			synchronized(observedPaths) {

				for(DocumentPath observedPath : observedPaths) {

					Set<String> observerTokens = tokensByObservedPath.get(observedPath);

					if(observerTokens != null) {

						observerTokens.remove(authToken);

						if(observerTokens.isEmpty()) {
							tokensByObservedPath.remove(observedPath);
						}
					}
				}
			}
		}

		observedPathsByToken.remove(authToken);
	}


	public static void sendMessageToUser(NtroUser user, NtroMessage message) throws BackendError {
		T.call(RegisteredSocketsSockJS.class);

		sendMessageToUserId(user.getId(), message);
	}

	public static void forEachSocket(String userId, AuthTokenIterator lambda) throws BackendError {
		T.call(RegisteredSocketsSockJS.class);

		Set<String> userTokens = tokensByUserId.get(userId);

		if(userTokens != null) {
			synchronized(userTokens) {
				for(String authToken : userTokens) {
					lambda.execute(authToken);
				}
			}

		}else {
			
			Log.warning("[forEachSocket] no socket for userId " + userId);
		}
	}


	public static void sendMessageToUserId(String userId, NtroMessage message) throws BackendError {
		T.call(RegisteredSocketsSockJS.class);
		
		forEachSocket(userId, authToken -> {

			sendMessageToSockets(authToken, message);
		});
	}

	public static void sendMessageToSockets(String authToken, NtroMessage message) {
		T.call(RegisteredSocketsSockJS.class);
		
		if(authToken == null) {
			Log.error("[sendMessageToSockets] authToken is null");
			return;
		}

		Set<SockJSSocket> sockets = socketsByToken.get(authToken);
		if(sockets != null) {
			synchronized (sockets) {
				for(SockJSSocket socket : sockets) {
					System.out.println("sendMessage to " + authToken);
					System.out.println(Ntro.jsonService().toString(message));
					socket.write(Ntro.jsonService().toString(message));
				}
			}

		}else {
			Log.warning("sendMessage: sockets closed for " + authToken);
		}
	}

	public static void registerModelObserver(NtroUser user, DocumentPath documentPath) {
		T.call(RegisteredSocketsSockJS.class);

		registerModelObserver(user.getAuthToken(), documentPath);
	}

	public static void registerModelObserver(String authToken, DocumentPath documentPath) {
		T.call(RegisteredSocketsSockJS.class);

		Set<String> observerTokens = tokensByObservedPath.get(documentPath);
		if(observerTokens == null) {
			observerTokens = new ConcurrentHashSet<String>();
			tokensByObservedPath.put(documentPath, observerTokens);
		}
		observerTokens.add(authToken);
		
		Set<DocumentPath> observedPaths = observedPathsByToken.get(authToken);
		if(observedPaths == null) {
			observedPaths = new ConcurrentHashSet<DocumentPath>();
			observedPathsByToken.put(authToken, observedPaths);
		}
		observedPaths.add(documentPath);
	}
	
	public static void onValueMethodInvoked(ValuePath valuePath, String methodName, List<Object> args) {
		T.call(RegisteredSocketsSockJS.class);
		
		if(args.size() == 0) {

			Log.info("onValueMethodInvoked: " + valuePath + " " + methodName);

		}else if(args.size() == 1){

			Log.info("onValueMethodInvoked: " + valuePath + " " + methodName + " " + args.get(0));

		}else if(args.size() == 2){

			Log.info("onValueMethodInvoked: " + valuePath + " " + methodName
														+ " " + args.get(0) 
														+ " " + args.get(1));
		}else {

			Log.info("onValueMethodInvoked: " + valuePath + " " + methodName
														+ " " + args.get(0) 
														+ " " + args.get(1) 
														+ " " + args.get(2));
		}
		
		Set<String> observerTokens = tokensByObservedPath.get(valuePath.getDocumentPath());

		if(observerTokens != null) {

			synchronized(observerTokens) {
				for(String authToken : observerTokens) {

					NtroInvokeValueMethodMessage message = Ntro.messages().create(NtroInvokeValueMethodMessage.class);
					message.setValuePath(valuePath);
					message.setMethodName(methodName);
					message.setArgs(args);

					RegisteredSocketsSockJS.sendMessageToSockets(authToken, message);
				}
			}
		}
	}

	public static void removeObserversWithNoSockets() {
		T.call(RegisteredSocketsSockJS.class);
		
		Set<String> tokensToRemove = new HashSet<>();
		
		synchronized(observedPathsByToken) {
			for(String authToken : observedPathsByToken.keySet()) {
				if(!ifSocketExists(authToken)) {

					Set<DocumentPath> observedPaths = observedPathsByToken.get(authToken);
					if(observedPaths != null) {
						synchronized(observedPaths) {
							for(DocumentPath observedPath : observedPaths) {
								Set<String> observerTokens = tokensByObservedPath.get(observedPath);
								if(observerTokens != null) {
									observerTokens.remove(authToken);
								}
							}
						}
					}

					tokensToRemove.add(authToken);
				}
			}
		}
		
		for(String tokenToRemove : tokensToRemove) {
			observedPathsByToken.remove(tokenToRemove);
		}
	}

	private static boolean ifSocketExists(String authToken) {
		T.call(RegisteredSocketsSockJS.class);

		if(authToken == null) {
			Log.error("[ifSocketExists] authToken is null");
			return false;
		}
		
		boolean ifExists = false;

		Set<SockJSSocket> sockets = socketsByToken.get(authToken);
		
		if(sockets != null && !sockets.isEmpty()) {

			ifExists = true;

		}

		return ifExists;
	}

	public static void deregisterOrphanSockets(Set<String> activeAuthTokens) {
		T.call(RegisteredSocketsSockJS.class);
		
		synchronized (socketsByToken) {

			for(Map.Entry<String, Set<SockJSSocket>> entry : socketsByToken.entrySet()) {
				
				String authToken = entry.getKey();
				
				if(!activeAuthTokens.contains(authToken)) {
					
					Set<SockJSSocket> sockets = entry.getValue();
					if(sockets != null) {
						synchronized (sockets) {
							for(SockJSSocket socket : sockets) {
								deregisterSocket(socket);
							}
						}
					}
				}
			}
		}
	}
}
