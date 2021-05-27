package ca.aquiletour.core.models.courses.base.lambdas;

import ca.aquiletour.core.models.courses.base.Task;

public interface TaskMatcher {

	boolean match(Task task);

}
