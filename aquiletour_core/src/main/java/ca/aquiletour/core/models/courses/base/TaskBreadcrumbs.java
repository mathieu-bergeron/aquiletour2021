package ca.aquiletour.core.models.courses.base;

import java.util.List;

import ca.aquiletour.core.models.courses.base.functionnal.TaskForEach;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.models.functionnal.Break;
import ca.ntro.core.system.trace.T;

public class TaskBreadcrumbs implements NtroModel {
	
	private List<Task> trunk;
	
	public List<Task> getTrunk() {
		return trunk;
	}

	public void setTrunk(List<Task> trunk) {
		this.trunk = trunk;
	}
	
	public void forEachTask(TaskForEach lambda) {
		T.call(this);

		for(Task task : trunk) {
			try {

				lambda.execute(task);

			}catch(Break b) {
				break;
			}
		}
	}
}
