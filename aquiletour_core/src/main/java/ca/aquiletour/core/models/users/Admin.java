package ca.aquiletour.core.models.users;

import ca.ntro.core.system.trace.T;

public class Admin extends User {
	
	public Admin() {
		super();
		T.call(this);

		setId("__root");
		setAuthToken("__root"); // FIXME: read this from file. It should only exists on the server
	}

}
