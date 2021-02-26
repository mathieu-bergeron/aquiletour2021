package ca.aquiletour.core.models.users;

import ca.ntro.core.system.trace.T;

public class SuperUser extends User {
	
	public SuperUser() {
		super();
		T.call(this);

		setId("__root");
		setAuthToken("__root"); // FIXME: read this from file. It should only exists on the server
	}

}
