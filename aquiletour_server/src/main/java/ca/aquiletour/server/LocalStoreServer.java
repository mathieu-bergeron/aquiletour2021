package ca.aquiletour.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.messages.git.GetCommitsForPath;
import ca.aquiletour.core.pages.git.commit_list.models.CommitListModel;
import ca.aquiletour.server.registered_sockets.RegisteredSockets;
import ca.ntro.core.json.JsonLoader;
import ca.ntro.core.json.JsonLoaderMemory;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.jdk.services.LocalStoreFiles;
import ca.ntro.jdk.services.ResourceLoaderTaskJdk;
import ca.ntro.messages.NtroModelMessage;
import ca.ntro.services.ModelStore;
import ca.ntro.services.Ntro;
import ca.ntro.stores.DocumentPath;
import ca.ntro.stores.ValuePath;

public class LocalStoreServer extends LocalStoreFiles {

	@Override
	public JsonLoader getJsonLoader(Class<? extends NtroModel> targetClass, DocumentPath documentPath) {
		T.call(this);
		
		JsonLoader jsonLoader = null;
		
		if(documentPath.getCollection().equals(Ntro.introspector().getSimpleNameForClass(CommitListModel.class))) {
			System.out.println("[LocalStoreServer] special case for : " + documentPath.getCollection());
			
			GetCommitsForPath getCommitsForPath = GetCommitsForPath.fromDocumentPath(documentPath);
			jsonLoader = jsonLoaderFromRequest(Constants.GIT_API_URL, getCommitsForPath);

		}
		
		if(jsonLoader == null) {
			
			jsonLoader = super.getJsonLoader(targetClass, documentPath);

		}

		return jsonLoader;
	}
	
	@Override
	public void onValueMethodInvoked(ValuePath valuePath, String methodName, List<Object> args) {
		T.call(LocalStoreServer.class);

		RegisteredSockets.onValueMethodInvoked(valuePath, methodName, args);
	}

	@Override
	protected JsonLoader jsonLoaderFromRequest(String serviceUrl, NtroModelMessage message) {
		T.call(this);
		
		String messageString = Ntro.jsonService().toString(message);
		System.out.println(messageString);
		byte[] messageBytes = messageString.getBytes(StandardCharsets.UTF_8);
		int messageLength = messageBytes.length;
		
		String response = null;

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
			
			int responseCode = con.getResponseCode();
			System.out.println(responseCode);
			
			if(responseCode == 200) {
				
				InputStream in = con.getInputStream();
				
				response = ResourceLoaderTaskJdk.readStream(in);
				System.out.println(response + "bababa");
				
			}else {
				
				Log.error("[LocalStoreServer] responseCode != 200 for " + messageString);
				response = ModelStore.emptyModelString(message.getDocumentPath());
			}
			
		} catch (IOException e) {
			Log.error("Unable to connect to " + serviceUrl);
			response = ModelStore.emptyModelString(message.getDocumentPath());
		}

		return new JsonLoaderMemory(response);
	}
}
