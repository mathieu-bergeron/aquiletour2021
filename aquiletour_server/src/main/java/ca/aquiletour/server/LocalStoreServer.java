package ca.aquiletour.server;

import java.io.IOException;

import ca.ntro.core.NtroUser;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.log.Log;
import ca.ntro.jdk.services.LocalStoreFiles;
import ca.ntro.messages.MessageFactory;
import ca.ntro.messages.ntro_messages.UpdateModelNtroMessage;

public class LocalStoreServer extends LocalStoreFiles {

	@Override
	public void registerThatUserObservesModel(NtroUser user, NtroModel model) {
		// Every time an ObservableValue within the model is changed
		// We send a UpdateModelNtroMessage to every user that observes that
		// model
		
		System.out.println("registerThatUserObservesModel: " + user.getId() + " " + model.getClass().getSimpleName());
		
		UpdateModelNtroMessage message = MessageFactory.createMessage(UpdateModelNtroMessage.class);

		try {

			RegisteredSockets.sendMessageToUserSockets(user, message);

		} catch (IOException e) {

			Log.fatalError("Unable to send message to user " + user.getId(), e);
		}
		
		
	}
}
