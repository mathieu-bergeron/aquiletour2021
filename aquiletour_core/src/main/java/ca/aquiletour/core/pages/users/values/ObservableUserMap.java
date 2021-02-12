package ca.aquiletour.core.pages.users.values;

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

	@Override
	protected Class<?> getValueType() {
		T.call(this);

		return NtroModelValue.class;
	}

	public boolean isUserValid(String userId, String authToken) {
		return getValue().get(userId).isValid(authToken);
	}

}
