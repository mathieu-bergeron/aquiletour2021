package ca.aquiletour.core.pages.course.views;

import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.paths.CoursePath;
import ca.ntro.core.mvc.NtroView;

public interface TaskView extends NtroView {
	
	void displayTask(CoursePath coursePath, Task task);
}
