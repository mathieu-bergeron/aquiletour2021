package ca.aquiletour.core.models.courses.atomic_tasks;

import ca.aquiletour.core.models.courses.base.AtomicTask;
import ca.ntro.core.system.trace.T;

public class ShortTextTask extends AtomicTask {
	
	@Override
	public String toString() {
		return "Texte court";
	}
	
	@Override
	public boolean equals(Object other) {
		T.call(this);
		
		if(other == null) return false;
		if(this == other) return true;
		if(other instanceof ShortTextTask) {
			return true;
		}
		return false;
	}

}
