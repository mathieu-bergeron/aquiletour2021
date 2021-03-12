package ca.aquiletour.core.pages.users.messages;

import ca.aquiletour.core.pages.users.UsersModel;
import ca.ntro.core.mvc.ModelMessageHandler;
import ca.ntro.core.system.trace.T;
import ca.ntro.stores.LocalStore;

public class DeleteUserHandler extends ModelMessageHandler<UsersModel, DeleteUserMessage> {

	@Override
	protected void handle(UsersModel model, DeleteUserMessage message) {
		T.call(this);
		
		model.deleteUser(message.getUserId());
		LocalStore.save(model);
	}
}
