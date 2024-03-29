package ca.aquiletour.web.pages.course;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.paths.CoursePath;
import ca.aquiletour.core.pages.course.views.TaskView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroView;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class TaskViewWeb extends NtroViewWeb implements TaskView {
	
	private HtmlElement taskTitleLink;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);

		taskTitleLink = getRootElement().find("#subtask-title-link").get(0);
		
		MustNot.beNull(taskTitleLink);
	}

	@Override
	public void displayTask(CoursePath coursePath, Task task) {
		T.call(this);
		
		taskTitleLink.text(task.getTitle().getValue());
		
		String href = "/" + Constants.COURSE_URL_SEGMENT  +
	                  coursePath.toUrlPath() + 
	                  task.id();
		
		taskTitleLink.setAttribute("href", href);
	}
}
