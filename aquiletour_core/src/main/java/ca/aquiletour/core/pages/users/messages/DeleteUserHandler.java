package ca.aquiletour.core.pages.users.messages;

import ca.aquiletour.core.pages.users.UsersModel;
import ca.ntro.core.mvc.ModelMessageHandler;
import ca.ntro.core.services.stores.LocalStore;
import ca.ntro.core.system.trace.T;

public class DeleteUserHandler extends ModelMessageHandler<UsersModel, DeleteUserMessage> {

	@Override
	protected void handle(UsersModel model, DeleteUserMessage message) {
		T.call(this);
		
		model.deleteUser(message.getUserId());
		LocalStore.save(model);
	}
}
