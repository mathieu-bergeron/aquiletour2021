package ca.aquiletour.core.models.courses.base.functionnal;

import ca.aquiletour.core.models.courses.base.Task;
import ca.ntro.core.models.functionnal.Break;

public interface TaskForEach {
	
	void execute(Task task) throws Break;

}
