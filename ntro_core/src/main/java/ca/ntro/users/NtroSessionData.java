package ca.ntro.users;

import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class NtroSessionData implements NtroModelValue {
	
	public NtroSessionData clone() {
		T.call(this);
		
		NtroSessionData clone = Ntro.factory().newInstance(getClass());
		
		clone.deepCopyOf(this);

		return clone;
	}

	protected void deepCopyOf(NtroSessionData toCopy) {
	}
}
