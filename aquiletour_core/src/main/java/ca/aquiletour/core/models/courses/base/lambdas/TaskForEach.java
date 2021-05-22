package ca.aquiletour.core.models.courses.base.lambdas;

import ca.aquiletour.core.models.courses.base.Task;
import ca.ntro.core.models.lambdas.Break;

public interface TaskForEach {
	
	void execute(Task task) throws Break;

}
