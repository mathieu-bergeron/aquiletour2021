package ca.aquiletour.core.models.courses.atomic_tasks;

import ca.ntro.core.system.trace.T;

public class GitExerciseTask extends AtomicTask {
	
	@Override
	public String toString() {
		return "Remise Git";
	}

	@Override
	public boolean equals(Object other) {
		T.call(this);
		
		if(other == null) return false;
		if(this == other) return true;
		if(other instanceof GitExerciseTask) {
			return true;
		}
		return false;
	}


}
