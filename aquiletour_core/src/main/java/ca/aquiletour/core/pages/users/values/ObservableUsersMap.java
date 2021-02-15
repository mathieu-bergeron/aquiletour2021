package ca.aquiletour.core.pages.users.values;

import java.util.HashMap;
import java.util.Map;

import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.ntro.core.models.properties.NtroModelValue;
import ca.ntro.core.models.properties.observable.map.ObservableMap;
import ca.ntro.core.system.trace.T;

public class ObservableUsersMap extends ObservableMap<User> {

	public ObservableUsersMap() {
		super(new HashMap<>());
	}

	public ObservableUsersMap(Map<String, User> map) {
		super(map);
	}

	@Override
	protected Class<?> getValueType() {
		T.call(this);

		// FIXME
		return NtroModelValue.class;
	}

	public boolean isUserValid(String userId, String authToken) {
		T.call(this);
		
		boolean isUserValid = false;
		
		User user = getValue().get(userId);

		if(user != null) {
			
			isUserValid = user.isValid(authToken);
		}
		
		return isUserValid;
	}

}
