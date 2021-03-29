package ca.aquiletour.server.backend.git;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.messages.git.RegisterExerciceMessage;
import ca.ntro.core.Path;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;
import ca.ntro.services.Ntro;

public class GitMessages {

	public static void registerExercice(String courseId, Path path) {
		T.call(GitMessages.class);

		String directoryName = "exercice";
		
		if(path.nameCount() > 0) {
			directoryName = path.name(path.nameCount()-1);
		}
		
		RegisterExerciceMessage registerExerciceMessage = Ntro.messages().create(RegisterExerciceMessage.class);
		registerExerciceMessage.setCourseId(courseId);
		registerExerciceMessage.setExerciseId(path.toString());
		registerExerciceMessage.setExercisePath(path.toString());
		registerExerciceMessage.setCompletionKeywords(directoryName);
		
		sendMessage(registerExerciceMessage);
	}

	public static void sendMessage(NtroMessage message) {
		T.call(GitMessages.class);

		System.out.println("GitMessages.sendMessage: ");
		System.out.println(Ntro.jsonService().toString(message));

		sendMessageToService(message, Constants.GIT_API_URL);
	}

	public static void sendReply(NtroMessage message) {
		T.call(GitMessages.class);

		System.out.println("GitMessages.sendReply: ");
		System.out.println(Ntro.jsonService().toString(message));

		sendMessageToService(message, Constants.HOST + ca.ntro.core.Constants.MESSAGES_URL_PATH_HTTP + "/");
	}

	static void sendMessageToService(NtroMessage message, String serviceUrl) {
		T.call(GitMessages.class);

		String messageString = Ntro.jsonService().toString(message);
		byte[] messageBytes = messageString.getBytes(StandardCharsets.UTF_8);
		int messageLength = messageBytes.length;

		try {

			URL url = new URL(serviceUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setFixedLengthStreamingMode(messageLength);
			con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			con.setDoOutput(true);
			con.connect();
			OutputStream out = con.getOutputStream();
			out.write(messageBytes);
			
			// XXX: we need to getResponseCode for the request to be trully sent??
			int responseCode = con.getResponseCode();
			//System.out.println("Response code: " + responseCode);
			
		} catch (IOException e) {
			Log.fatalError("Unable to connect to _git_api", e);
		}
	}

	public static void deRegisterExercice(String courseId, Path path) {
		T.call(GitMessages.class);
		
	}

}
