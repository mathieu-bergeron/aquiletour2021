package ca.aquiletour.core.models.users;

import ca.ntro.core.system.trace.T;

public class Guest extends User {
	
	public Guest() {
		super();
		T.call(this);
		
		setName("Visiteur");
	}

	@Override
	public User toSessionUser() {
		Guest sessionUser = new Guest();
		
		copySessionOnlyInfo(sessionUser);
		
		return sessionUser;
	}

}
