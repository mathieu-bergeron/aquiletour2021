package ca.aquiletour.server.registered_sockets;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jetty.websocket.api.Session;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

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

public class RegisteredSockets {

	private static Map<DocumentPath, Set<String>> tokensByObservedPath = new ConcurrentHashMap<DocumentPath, Set<String>>();
	private static Map<String, Set<DocumentPath>> observedPathsByToken = new ConcurrentHashMap<String, Set<DocumentPath>>();

	private static Map<String, Set<String>> tokensByUserId = new ConcurrentHashMap<String, Set<String>>();
	private static Map<String, String> userIdByToken = new ConcurrentHashMap<String, String>();

	// XXX: Session is a Jetty Session (a socket)
	private static BiMap<String, Session> socketByToken = HashBiMap.create();

	public static void registerUserSocket(NtroUser user, Session socket) {
		T.call(RegisteredSockets.class);
		
		if(socketByToken.inverse().containsKey(socket)) {
			deregisterSocket(socket);
		}

		socketByToken.put(user.getAuthToken(), socket);
		
		Set<String> userTokens = tokensByUserId.get(user.getId());
		if(userTokens == null) {
			userTokens = new ConcurrentHashSet<String>();
			tokensByUserId.put(user.getId(), userTokens);
		}
		userTokens.add(user.getAuthToken());
		
		userIdByToken.put(user.getAuthToken(), user.getId());
		
		System.out.println("registered user socket for " + user.getId() + " " + user.getAuthToken());
	}

	public static void deregisterSocket(Session socket) {
		T.call(RegisteredSockets.class);

		String authToken = socketByToken.inverse().get(socket);
		String userId = userIdByToken.get(authToken);

		Session removed = socketByToken.remove(authToken);
		if(removed != null) {
			System.out.println("deregistered socket for " + authToken);
		}

		deregisterUser(authToken, userId);

		deregisterModelObservers(authToken);
	}

	private static void deregisterUser(String authToken, String userId) {
		T.call(RegisteredSockets.class);

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
		T.call(RegisteredSockets.class);

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
		T.call(RegisteredSockets.class);

		sendMessageToUserId(user.getId(), message);
	}

	public static void forEachSocket(String userId, AuthTokenIterator lambda) throws BackendError {
		T.call(RegisteredSockets.class);

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
		T.call(RegisteredSockets.class);
		
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
		T.call(RegisteredSockets.class);

		Session socket = socketByToken.get(authToken);

		if(socket != null) {
			if(socket.isOpen()) {
				try {
					
					System.out.println("sendMessage: " + Ntro.jsonService().toString(message));
					socket.getRemote().sendString(Ntro.jsonService().toString(message));

				} catch (IOException e) {
					Log.error("Unable to send message to user: " + authToken);
				}
			}
		}
	}

	public static void registerModelObserver(NtroUser user, DocumentPath documentPath) {
		T.call(RegisteredSockets.class);

		registerModelObserver(user.getAuthToken(), documentPath);
	}

	public static void registerModelObserver(String authToken, DocumentPath documentPath) {
		T.call(RegisteredSockets.class);

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
		T.call(RegisteredSockets.class);
		
		if(args.size() > 0) {
			System.out.println("onValueMethodInvoked: " + valuePath + " " + methodName + " " + args.get(0));
		}else {
			System.out.println("onValueMethodInvoked: " + valuePath + " " + methodName);
		}
		
		Set<String> observerTokens = tokensByObservedPath.get(valuePath.getDocumentPath());

		if(observerTokens != null) {

			synchronized(observerTokens) {
				for(String authToken : observerTokens) {

					NtroInvokeValueMethodMessage message = Ntro.messages().create(NtroInvokeValueMethodMessage.class);
					message.setValuePath(valuePath);
					message.setMethodName(methodName);
					message.setArgs(args);

					RegisteredSockets.sendMessageToSocket(authToken, message);
				}
			}
		}
	}

	public static void removeObserversWithNoSockets() {
		T.call(RegisteredSockets.class);
		
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
		T.call(RegisteredSockets.class);

		return socketByToken.containsKey(authToken);
	}
}
