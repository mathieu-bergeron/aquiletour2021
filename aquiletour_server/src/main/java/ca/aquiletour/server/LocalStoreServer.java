package ca.aquiletour.server;

import java.io.IOException;
import java.util.List;

import ca.ntro.core.NtroUser;
import ca.ntro.core.system.log.Log;
import ca.ntro.jdk.services.LocalStoreFiles;
import ca.ntro.messages.MessageFactory;
import ca.ntro.messages.ntro_messages.InvokeValueMethodNtroMessage;
import ca.ntro.stores.ValuePath;

public class LocalStoreServer extends LocalStoreFiles {
	
	@Override
	public void onValueMethodInvoked(ValuePath valuePath, String methodName, List<Object> args) {

		NtroUser observatorUser = RegisteredSockets.getUserThatObservesModel(valuePath.getDocumentPath());
		
		if(observatorUser != null) {
			
			System.out.println("invokeValueMethodMessage: " + valuePath.toString());
			
			InvokeValueMethodNtroMessage message = MessageFactory.createMessage(InvokeValueMethodNtroMessage.class);
			message.setValuePath(valuePath);
			message.setMethodName(methodName);
			message.setArgs(args);
			
			try {
				
				RegisteredSockets.sendMessageToUserSockets(observatorUser, message);

			} catch (IOException e) {

				Log.fatalError("Unable to send message to user " + observatorUser.getId(), e);
			}
		}
	}
}
