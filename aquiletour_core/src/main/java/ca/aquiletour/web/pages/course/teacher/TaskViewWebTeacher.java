package ca.aquiletour.web.pages.course.teacher;


import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.pages.course.teacher.views.TaskViewTeacher;
import ca.aquiletour.web.pages.course.TaskViewWeb;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;

import static ca.ntro.assertions.Factory.that;

public class TaskViewWebTeacher extends TaskViewWeb implements TaskViewTeacher {

	/*
	private HtmlElements addTaskIdToValue;
	private HtmlElements addTaskIdToDataTarget;
	private HtmlElements addTaskIdToId;
	private HtmlElement deleteTaskLink;
	*/

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);
		super.initializeViewWeb(context);
		
		
		/*
		addTaskIdToValue = getRootElement().find(".add-task-id-to-value");
		addTaskIdToDataTarget = getRootElement().find(".add-task-id-to-data-target");
		addTaskIdToId = getRootElement().find(".add-task-id-to-id");

		deleteTaskLink = getRootElement().find("#delete-task-link").get(0);

		Ntro.verify(that(addTaskIdToValue.size() > 0).isTrue());
		Ntro.verify(that(addTaskIdToDataTarget.size() > 0).isTrue());
		Ntro.verify(that(addTaskIdToId.size() > 0).isTrue());

		MustNot.beNull(deleteTaskLink);
		*/

	}

	@Override
	public void displayTask(CoursePath coursePath, Task task) {
		T.call(this);
		super.displayTask(coursePath, task);
		
		/*
		deleteTaskLink.setAttribute("href", "?delete=" + task.getPath().toFileName());
		
		addTaskIdToValue.forEach(e -> e.value(task.id()));

		addTaskIdToDataTarget.forEach(e -> {
			String dataTarget = e.getAttribute("data-target");
			dataTarget += task.getPath().toFileName();
			e.setAttribute("data-target", dataTarget);
		});

		addTaskIdToId.forEach(e -> {
			String id = e.getAttribute("id");
			id += task.getPath().toFileName();
			e.setAttribute("id", id);
		});
		
		*/
	}
}
