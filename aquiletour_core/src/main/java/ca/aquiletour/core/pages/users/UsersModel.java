package ca.aquiletour.core.pages.users;

import ca.aquiletour.core.pages.users.values.ObservableUsersMap;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;

public class UsersModel extends NtroModel {

	private ObservableUsersMap users = new ObservableUsersMap();

	@Override
	public void initializeStoredValues() {
		T.call(this);
	}

	public ObservableUsersMap getUsers() {
		T.call(this);

		return users;
	}

	public void setUsers(ObservableUsersMap users) {
		T.call(this);

		this.users = users;
	}

	public boolean isUserValid(String userId, String authToken) {
		T.call(this);
		
		return users.isUserValid(userId, authToken);
	}
	
		
	
	
	
}
