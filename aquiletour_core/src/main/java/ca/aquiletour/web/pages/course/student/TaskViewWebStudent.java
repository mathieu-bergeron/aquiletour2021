package ca.aquiletour.web.pages.course.student;

import ca.aquiletour.core.pages.course.models.Task;
import ca.aquiletour.core.pages.course.views.TaskView;
import ca.aquiletour.web.pages.course.TaskViewWeb;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;

public class TaskViewWebStudent extends TaskViewWeb implements TaskView {
	
	@Override
	protected void addTaskLi(String courseId, 
						     Task currentTask,
			                 Task task, 
			                 HtmlElement container, 
			                 String taskType) {
		T.call(this);

		HtmlElement taskLi = container.createElement("<li></li>");
		HtmlElement anchor = taskLi.createElement("<a></a>");
		taskLi.appendElement(anchor);
		container.appendElement(taskLi);

		anchor.text(task.getTitle());
		anchor.setAttribute("href", "/cours/" + courseId + task.id());
	}

	
}
