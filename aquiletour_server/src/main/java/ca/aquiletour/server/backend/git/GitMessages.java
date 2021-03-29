package ca.aquiletour.server.backend.git;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
		registerExerciceMessage.setExercicePath(path.toString());
		registerExerciceMessage.setDirectoryName(directoryName);
		registerExerciceMessage.setCompletionKeywords(directoryName);
		
		sendMessage(registerExerciceMessage);
	}

	private static void sendMessage(NtroMessage message) {
		T.call(GitMessages.class);
		
		String messageString = Ntro.jsonService().toString(message);

		try {

			URL url = new URL("localhost:8888/" + Constants.GIT_API_URL_PATH);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			OutputStream out = con.getOutputStream();
			out.write(messageString.getBytes());
			out.close();

		} catch (IOException e) {
			Log.fatalError("Unable to connect to _git_api", e);
		}
	}

	public static void deRegisterExercice(String courseId, Path path) {
		T.call(GitMessages.class);
		
	}

}
