package ca.aquiletour.core.models.user;

import ca.ntro.core.system.trace.T;

public class Guest extends User {
	
	public Guest() {
		super();
		T.call(this);
		
		setFirstname("Visiteur");
	}

	@Override
	public User toSessionUser() {
		Guest sessionUser = new Guest();
		
		copySessionOnlyInfo(sessionUser);
		
		return sessionUser;
	}

}
