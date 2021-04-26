package ca.aquiletour.core.models.courses.base;

import ca.aquiletour.core.models.courses.task_types.TaskType;
import ca.ntro.core.models.StoredProperty;
import ca.ntro.core.system.trace.T;

public class ObservableTaskType extends StoredProperty<TaskType> {
	
	public ObservableTaskType() {
		super(new TaskType());
		T.call(this);
	}
	
	public ObservableTaskType(TaskType type) {
		super(type);
		T.call(this);
	}

}
