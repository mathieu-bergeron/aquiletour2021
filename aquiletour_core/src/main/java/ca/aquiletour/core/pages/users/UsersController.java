package ca.aquiletour.core.pages.users;

import ca.aquiletour.core.pages.root.RootController;
import ca.aquiletour.core.pages.users.messages.AddUserHandler;
import ca.aquiletour.core.pages.users.messages.AddUserMessage;
import ca.aquiletour.core.pages.users.messages.DeleteUserHandler;
import ca.aquiletour.core.pages.users.messages.DeleteUserMessage;
import ca.aquiletour.core.pages.users.messages.ShowUsersHandler;
import ca.aquiletour.core.pages.users.messages.ShowUsersMessage;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.services.stores.LocalStore;

public class UsersController extends NtroController<RootController> {

	@Override
	protected void initialize() {

		setViewLoader(UsersView.class, "fr");
		
		setModelLoader(LocalStore.getLoader(UsersModel.class, "TODO"));
		
		addParentViewMessageHandler(ShowUsersMessage.class, new ShowUsersHandler());
		
		addModelMessageHandler(AddUserMessage.class, new AddUserHandler());

		addModelMessageHandler(DeleteUserMessage.class, new DeleteUserHandler());

		addSubViewLoader(UserView.class, "fr");
		
		addModelViewSubViewHandler(UserView.class, new UsersViewModel());
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
