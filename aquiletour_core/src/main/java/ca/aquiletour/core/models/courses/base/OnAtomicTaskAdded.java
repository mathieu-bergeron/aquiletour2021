package ca.aquiletour.core.models.courses.base;

import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTask;

public interface OnAtomicTaskAdded {
	
	void onAtomicTaskAdded(Task task, AtomicTask atomicTask);

}
