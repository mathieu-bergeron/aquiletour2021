package ca.aquiletour.core.models.courses.base.lambdas;

import ca.aquiletour.core.models.courses.base.Task;
import ca.ntro.core.models.lambdas.Break;

public interface TaskReducer<ACC extends Object> {

	ACC reduce(int distance, Task task, ACC accumulator) throws Break;

}
