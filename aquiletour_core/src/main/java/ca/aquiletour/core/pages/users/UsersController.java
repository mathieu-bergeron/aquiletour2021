package ca.aquiletour.core.pages.users;

import ca.aquiletour.core.pages.root.RootController;
import ca.aquiletour.core.pages.users.messages.ShowUsersHandler;
import ca.aquiletour.core.pages.users.messages.ShowUsersMessage;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;

public class UsersController extends NtroController<RootController> {

	@Override
	protected void onCreate(NtroContext<?> context) {

		setViewLoader(UsersView.class, "fr");
		
		setModelLoader(UsersModel.class, "TODO", "allUsers");
		
		addParentViewMessageHandler(ShowUsersMessage.class, new ShowUsersHandler());
		
		addSubViewLoader(UserView.class, "fr");
		
		addModelViewSubViewHandler(UserView.class, new UsersViewModel());
	}

	@Override
	protected void onChangeContext(NtroContext<?> previousContext, NtroContext<?> context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}


}
