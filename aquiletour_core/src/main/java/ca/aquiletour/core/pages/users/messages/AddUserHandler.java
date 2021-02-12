package ca.aquiletour.core.pages.users.messages;

import ca.aquiletour.core.pages.users.UsersModel;
import ca.ntro.core.mvc.ModelMessageHandler;
import ca.ntro.core.system.trace.T;

public class AddUserHandler extends ModelMessageHandler<UsersModel, AddUserMessage> {

	@Override
	protected void handle(UsersModel model, AddUserMessage message) {
		T.call(this);
		
		model.addUser(message.getUser());
		model.save();
	}
}
