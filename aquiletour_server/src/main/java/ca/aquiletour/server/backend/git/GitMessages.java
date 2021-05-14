package ca.aquiletour.server.backend.git;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.messages.git.DeRegisterExercise;
import ca.aquiletour.core.messages.git.RegisterExercise;
import ca.aquiletour.core.models.courses.CoursePath;
import ca.ntro.core.Path;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;
import ca.ntro.services.Ntro;

public class GitMessages {

	public static void registerExercice(CoursePath coursePath, String groupId, Path taskPath) {
		T.call(GitMessages.class);

		String directoryName = "exercice";
		
		if(taskPath.nameCount() > 0) {
			directoryName = taskPath.name(taskPath.nameCount()-1);
		}
		
		RegisterExercise registerExerciceMessage = Ntro.messages().create(RegisterExercise.class);
		registerExerciceMessage.setCourseId(coursePath.teacherId() + "/" + coursePath.courseId());
		registerExerciceMessage.setGroupId(groupId);
		registerExerciceMessage.setExercisePath(taskPath.toString());
		registerExerciceMessage.setSourceFolderPath(taskPath.toString());
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
			Log.warning("Unable to connect to _git_api");
		}
	}

	public static void deleteExercise(CoursePath coursePath, String groupId, Path taskPath) {

		T.call(GitMessages.class);
		
		DeRegisterExercise deleteExercice = Ntro.messages().create(DeRegisterExercise.class);
		deleteExercice.setCourseId(coursePath.teacherId() + "/" + coursePath.courseId());
		deleteExercice.setGroupId(groupId);
		deleteExercice.setExercisePath(taskPath.toString());
		
		sendMessage(deleteExercice);
	}

}
