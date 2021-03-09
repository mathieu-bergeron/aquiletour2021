package ca.ntro.test.json;

import java.util.HashMap;
import java.util.Map;

import ca.ntro.core.models.properties.NtroModelValue;
import ca.ntro.core.models.properties.observable.map.ObservableMap;
import ca.ntro.core.system.trace.T;

public class ObservableUserMap extends ObservableMap<User> {

	public ObservableUserMap() {
		super(new HashMap<>());
	}

	public ObservableUserMap(Map<String, User> map) {
		super(map);
	}

	public boolean isUserValid(String userId, String authToken) {
		return getValue().get(userId).isValid(authToken);
	}

}
