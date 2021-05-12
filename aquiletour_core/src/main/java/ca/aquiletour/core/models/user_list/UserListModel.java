package ca.aquiletour.core.models.user_list;

import java.util.Set;

import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;

public class UserListModel implements NtroModel {

	private UserIdMap userIdMap = new UserIdMap();

	public UserIdMap getUserIdMap() {
		return userIdMap;
	}

	public void setUserIdMap(UserIdMap userIdMap) {
		this.userIdMap = userIdMap;
	}

	public Set<String> userIds(){
		return userIdMap.getValue().keySet();
	}

	public void addUserId(String userId) {
		T.call(this);
		
		userIdMap.putEntry(userId, true);
	}
}
