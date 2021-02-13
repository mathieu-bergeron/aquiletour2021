package ca.aquiletour.core.pages.queues;

import ca.aquiletour.core.pages.users.values.ObservableUserMap;
import ca.aquiletour.core.pages.users.values.User;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;

public class QueuesModel extends NtroModel {

	private ObservableUserMap users = new ObservableUserMap();

	@Override
	public void initializeStoredValues() {
		// TODO Auto-generated method stub
		
	}

	public ObservableUserMap getUsers() {
		T.call(this);

		return users;
	}

	public void setUsers(ObservableUserMap users) {
		T.call(this);

		this.users = users;
	}

	public boolean isUserValid(String userId, String authToken) {
		T.call(this);
		
		return users.isUserValid(userId, authToken);
	}

	public void addUser(User user) {
		T.call(this);

		String userId = Integer.toString(users.size());
		user.setUserId(userId);
		users.addEntry(userId, user);
	}
	
	public void deleteUser(String userId) {
		T.call(this);
		
		users.removeEntry(userId);
	}

}
