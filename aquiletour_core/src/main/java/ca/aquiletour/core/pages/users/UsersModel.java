package ca.aquiletour.core.pages.users;

import java.util.ArrayList;
import java.util.Map;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.users.values.ObservableUserMap;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;

public class UsersModel extends NtroModel {

	private ObservableUserMap users = new ObservableUserMap();

	@Override
	public void initializeStoredValues() {
		// TODO Auto-generated method stub
		
	}
	
	public ObservableUserMap getUsers() {
		T.call(this);

		return users;
	}
	
	public ArrayList<User> getListOfAllUsers(){
		ArrayList<User> usersList = new ArrayList<User>();
		Map<String,User> usersMap = (Map<String, User>) users;
		for (Map.Entry<String, User> entry : usersMap.entrySet()) {
			usersList.add(entry.getValue());
		}
		return usersList;
		
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
		T.here();
		String userId = Integer.toString(users.size());
		user.setId(userId);
		users.addEntry(userId, user);
	}
	
	public void deleteUser(String userId) {
		T.call(this);
		
		users.removeEntry(userId);
	}

}
