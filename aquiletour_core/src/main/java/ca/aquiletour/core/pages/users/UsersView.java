package ca.aquiletour.core.pages.users;

import ca.aquiletour.core.pages.users.values.User;
import ca.ntro.core.mvc.NtroView;

public interface UsersView extends NtroView {

	void appendUser(User user, UserView userView);
	void deleteUser(String userId);

}
