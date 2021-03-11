package ca.aquiletour.core.pages.users.values;

import java.util.HashMap;
import java.util.Map;

import ca.aquiletour.core.models.users.User;
import ca.ntro.core.models.ObservableMap;
import ca.ntro.core.system.trace.T;

public class ObservableUserMap extends ObservableMap<User> {

	public ObservableUserMap() {
		super(new HashMap<>());
	}

	public ObservableUserMap(Map<String, User> map) {
		super(map);
	}

	public boolean isUserValid(String userId, String authToken) {
		T.call(this);

		return valueOf(userId).isValid(authToken);
	}

}
