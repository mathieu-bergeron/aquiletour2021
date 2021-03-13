package ca.aquiletour.core.pages.users;

import ca.aquiletour.core.pages.root.RootController;
import ca.aquiletour.core.pages.users.messages.AddUserHandler;
import ca.aquiletour.core.pages.users.messages.AddUserMessage;
import ca.aquiletour.core.pages.users.messages.DeleteUserHandler;
import ca.aquiletour.core.pages.users.messages.DeleteUserMessage;
import ca.aquiletour.core.pages.users.messages.ShowUsersHandler;
import ca.aquiletour.core.pages.users.messages.ShowUsersMessage;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;

public class UsersController extends NtroController<RootController> {

	@Override
	protected void onCreate() {

		setViewLoader(UsersView.class, "fr");
		
		setModelLoader(UsersModel.class, "TODO", "allUsers");
		
		addParentViewMessageHandler(ShowUsersMessage.class, new ShowUsersHandler());
		
		addModelMessageHandler(AddUserMessage.class, new AddUserHandler());

		addModelMessageHandler(DeleteUserMessage.class, new DeleteUserHandler());

		addSubViewLoader(UserView.class, "fr");
		
		addModelViewSubViewHandler(UserView.class, new UsersViewModel());
	}

	@Override
	protected void onChangeContext(NtroContext<?> previousContext) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}


}
