package ca.aquiletour.core.models.courses.status;

import ca.ntro.core.system.trace.T;

public class StatusBlocked extends TaskStatus {

	@Override
	public boolean isBlocked() {
		T.call(this);

		return true;
	}
	
	public String text() {
		return "Les préalables ne sont pas satisfaits";
	}

	public String href() {
		return "";
	}


}
