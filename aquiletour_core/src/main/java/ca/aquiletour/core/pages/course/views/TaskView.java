package ca.aquiletour.core.pages.course.views;

import ca.aquiletour.core.models.courses.base.Task;
import ca.ntro.core.mvc.NtroView;

public interface TaskView extends NtroView {
	
	void displayTask(String courseId, Task task);
	void clear();

}
