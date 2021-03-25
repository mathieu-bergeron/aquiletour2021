package ca.aquiletour.web.pages.courses;

import ca.aquiletour.core.pages.course.models.TaskBreadcrumbs;
import ca.aquiletour.core.pages.course.views.CourseView;
import ca.aquiletour.core.pages.course.views.TaskView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class CourseViewWeb extends NtroViewWeb implements CourseView {

	private HtmlElement taskIdInput;
	private HtmlElement taskContainer;
	private HtmlElement breadcrumbsContainer;

	@Override
	public void initializeViewWeb(NtroContext<?> context) {
		T.call(this);

		taskContainer = this.getRootElement().find("#tasks-container").get(0);
		breadcrumbsContainer = this.getRootElement().find("#breadcrumbs-container").get(0);
		taskIdInput = this.getRootElement().find("#task-id-input").get(0);

		MustNot.beNull(taskContainer);
		MustNot.beNull(breadcrumbsContainer);
		MustNot.beNull(taskIdInput);
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
	public void displayBreadcrumbs(String courseId, TaskBreadcrumbs breadcrumps) {
		T.call(this);
		
		breadcrumps.forEachTask(t -> {
			HtmlElement taskLi = breadcrumbsContainer.createElement("<li></li>");
			HtmlElement anchor = taskLi.createElement("<a></a>");
			taskLi.appendElement(anchor);
			breadcrumbsContainer.appendElement(taskLi);

			anchor.text(t.getTitle());
			anchor.setAttribute("href", "/cours/" + courseId + t.id());
		});
	}

	@Override
	public void identifyCurrentTask(String id) {
		T.call(this);
		
		taskIdInput.value(id);
	}

	@Override
	public void clearTasks() {
		T.call(this);
		
		taskContainer.deleteChildrenForever();
	}

	@Override
	public void appendTask(TaskView taskView) {
		T.call(this);

		HtmlElement taskElement = ((TaskViewWeb) taskView).getRootElement();
		
		taskContainer.appendElement(taskElement);
	}

}
