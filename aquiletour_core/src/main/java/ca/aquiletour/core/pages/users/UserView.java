package ca.aquiletour.core.pages.users;

import ca.aquiletour.core.models.users.User;
import ca.ntro.core.mvc.NtroView;

public interface UserView extends NtroView {
	
	void displayUser(User user);

}
