package ca.aquiletour.core.models.courses.atomic_tasks.short_text;

import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTask;
import ca.ntro.core.system.trace.T;

public class DefaultAtomicTask extends AtomicTask {
	
	@Override
	public String toString() {
		return "Défaut";
	}

	@Override
	public boolean equals(Object other) {
		T.call(this);
		
		if(other == null) return false;
		if(this == other) return true;
		if(other instanceof DefaultAtomicTask) {
			return true;
		}
		return false;
	}
}
