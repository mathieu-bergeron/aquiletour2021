package ca.aquiletour.core.models.courses.base.functionnal;

import ca.aquiletour.core.models.courses.base.Task;
import ca.ntro.core.models.functionnal.Break;

public interface TaskReducer<ACC extends Object> {

	ACC reduce(Task task, ACC accumulator) throws Break;

}
