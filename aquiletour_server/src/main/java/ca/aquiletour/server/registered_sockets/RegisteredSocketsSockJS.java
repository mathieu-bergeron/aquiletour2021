package ca.aquiletour.server.registered_sockets;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jetty.websocket.api.Session;

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

	private static Map<String, SockJSSocket> socketByToken = Ntro.collections().concurrentMap(new HashMap<>());
	private static Map<SockJSSocket, String> tokenBySocket = Ntro.collections().concurrentMap(new HashMap<>());

	public static void onUserChanges(String oldAuthToken, String authToken, NtroUser user) {
		T.call(RegisteredSocketsSockJS.class);
		
		SockJSSocket socket = null;
		
		synchronized (socketByToken) {
			socket = socketByToken.get(oldAuthToken);
		}
		
		if(socket != null) {
			deregisterSocket(socket);
			registerUserSocket(authToken, user, socket);
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

		synchronized (socketByToken) {
			socketByToken.put(authToken, socket);
			tokenBySocket.put(socket, authToken);
		}
	}

	private static void deregisterSocketIfExists(SockJSSocket socket) {
		T.call(RegisteredSocketsSockJS.class);

		boolean ifSocketExists = false;
		synchronized (socketByToken) {
			ifSocketExists = tokenBySocket.containsKey(socket);
		}

		if(ifSocketExists) {
			deregisterSocket(socket);
		}
	}

	public static void deregisterSocket(SockJSSocket socket) {
		T.call(RegisteredSocketsSockJS.class);

		
		String authToken = null;
		SockJSSocket removed = null;
		synchronized (socketByToken) {

			authToken = tokenBySocket.get(socket);
			
			if(authToken != null) {

				tokenBySocket.remove(socket);
				removed = socketByToken.get(authToken);
			}
		}

		if(removed != null) {
			System.out.println("deregistered socket for " + authToken);
		}

		String userId = null;
		if(authToken != null) {
			userId = userIdByToken.get(authToken);
		}

		deregisterUser(authToken, userId);

		deregisterModelObservers(authToken);
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


	public static void sendMessageToUser(NtroUser user, NtroMessage message) {
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
		}
	}


	public static void sendMessageToUserId(String userId, NtroMessage message) {
		T.call(RegisteredSocketsSockJS.class);
		
		Set<String> userTokens = tokensByUserId.get(userId);
		if(userTokens != null) {
			synchronized(userTokens) {
				for(String authToken : userTokens) {
					sendMessageToSocket(authToken, message);
				}
			}
			
		}else {
			
			Log.warning("User has not registered a socket: " + userId);
		}
	}

	public static void sendMessageToSocket(String authToken, NtroMessage message) {
		T.call(RegisteredSocketsSockJS.class);

		SockJSSocket socket = null;
		synchronized (socketByToken) {
			socket = socketByToken.get(authToken);
		}

		if(socket != null) {

			socket.exceptionHandler(e -> {
				Log.error("[sendMessageToSocket] ");
			});
			
			System.out.println("sendMessage: " + Ntro.jsonService().toString(message));
			socket.write(Ntro.jsonService().toString(message));
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

					RegisteredSocketsSockJS.sendMessageToSocket(authToken, message);
				}
			}
		}
	}

	public static void removeObserversWithNoSockets() {
		T.call(RegisteredSocketsSockJS.class);
		
		Set<String> tokensToRemove = new HashSet<>();
		
		synchronized(observedPathsByToken) {
			for(String authToken : observedPathsByToken.keySet()) {
				if(!socketExistsForToken(authToken)) {

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

	private static boolean socketExistsForToken(String authToken) {
		T.call(RegisteredSocketsSockJS.class);
		
		boolean ifExists = false;
		
		synchronized (socketByToken) {
			ifExists = socketByToken.containsKey(authToken);
		}
		
		return ifExists;
	}
}
