package ca.aquiletour.core.models.users;

import ca.ntro.core.system.trace.T;

public class Guest extends User {
	
	public Guest() {
		super();
		T.call(this);
		
		setName("Visiteur");
	}

}
