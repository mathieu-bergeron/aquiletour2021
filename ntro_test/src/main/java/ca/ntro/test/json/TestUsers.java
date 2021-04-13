package ca.ntro.test.json;

import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;

public class TestUsers implements NtroModel {

	private TestUserMap users = new TestUserMap();

	public TestUserMap getUsers() {
		T.call(this);

		return users;
	}

	public void setUsers(TestUserMap users) {
		T.call(this);

		this.users = users;
	}

	public void addUser(TestUser user) {
		T.call(this);

		String userId = Integer.toString(users.size());
		user.setId(userId);
		users.addEntry(userId, user);
	}
	
	public void deleteUser(String userId) {
		T.call(this);
		
		users.removeEntry(userId);
	}

}
