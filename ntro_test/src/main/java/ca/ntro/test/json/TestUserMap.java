package ca.ntro.test.json;

import java.util.HashMap;
import java.util.Map;

import ca.ntro.core.json.JsonSerializable;
import ca.ntro.core.models.StoredMap;
import ca.ntro.core.system.trace.T;

public class TestUserMap extends StoredMap<TestUser> {

	public TestUserMap() {
		super(new HashMap<>());
	}

	public TestUserMap(Map<String, TestUser> map) {
		super(map);
	}

	public boolean isUserValid(String userId, String authToken) {
		T.call(this);

		return valueOf(userId).isValid(authToken);
	}

}
