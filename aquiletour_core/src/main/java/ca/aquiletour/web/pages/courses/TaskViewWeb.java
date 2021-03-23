package ca.aquiletour.web.pages.courses;

import ca.aquiletour.core.pages.course.models.Task;
import ca.aquiletour.core.pages.course.views.TaskView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.mvc.NtroViewWeb;

public class TaskViewWeb extends NtroViewWeb implements TaskView {


	@Override
	public void initializeViewWeb(NtroContext<?> context) {
		T.call(this);

	}

	@Override
	public void displayTask(Task task) {
		T.call(this);
	}

}
