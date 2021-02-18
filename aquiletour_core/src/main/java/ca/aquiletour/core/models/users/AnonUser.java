package ca.aquiletour.core.models.users;

import ca.ntro.core.system.trace.T;

public class AnonUser extends User {
	
	public AnonUser() {
		super();
		T.call(this);

		setId("__anon");
		setAuthToken("__anon");
	}

}
