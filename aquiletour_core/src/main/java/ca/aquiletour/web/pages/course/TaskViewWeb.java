package ca.aquiletour.web.pages.course;

import ca.aquiletour.core.AquiletourMain;
import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.pages.course.views.TaskView;
import ca.ntro.core.mvc.NtroContext;
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
		
		taskTitleLink.html(task.getTitle().getValue());
		
		String href = "/" + Constants.COURSE_URL_SEGMENT  +
	                  coursePath.toUrlPath() + 
	                  task.id();
		
		if(!AquiletourMain.currentSemester().equals(coursePath.semesterId())) {
			href += "?" + Constants.SEMESTER_URL_PARAM + "=" + coursePath.semesterId();
		}
		
		taskTitleLink.setAttribute("href", href);
	}
}
