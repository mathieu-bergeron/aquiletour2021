package ca.aquiletour.web.pages.courses;

import ca.aquiletour.core.pages.course.models.TaskBreadcrumbs;
import ca.aquiletour.core.pages.course.views.CourseView;
import ca.aquiletour.core.pages.course.views.TaskView;
import ca.ntro.core.Path;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class CourseViewWeb extends NtroViewWeb implements CourseView {

	private HtmlElement taskContainer;

	@Override
	public void initializeViewWeb(NtroContext<?> context) {
		T.call(this);

		taskContainer = this.getRootElement().find("#tasks-container").get(0);

		MustNot.beNull(taskContainer);
	}

	@Override
	public void insertTask(int index, TaskView taskView) {
		T.call(this);
		
		HtmlElement taskElement = ((TaskViewWeb) taskView).getRootElement();

		if(index >= 0 && index < taskContainer.children("*").size()) {

			HtmlElement anchorElement = taskContainer.children("*").get(index);
			taskContainer.insertBefore(anchorElement);

		}else {

			taskContainer.appendElement(taskElement);
		}
	}

	@Override
	public void displayBreadcrumbs(TaskBreadcrumbs breadcrumps) {
		T.call(this);
		
	}

}
