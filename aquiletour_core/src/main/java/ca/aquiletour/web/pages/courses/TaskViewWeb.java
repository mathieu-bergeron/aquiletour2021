package ca.aquiletour.web.pages.courses;

import ca.aquiletour.core.pages.course.models.Task;
import ca.aquiletour.core.pages.course.views.TaskView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;
import ca.ntro.web.mvc.NtroViewWeb;

import static ca.ntro.assertions.Factory.that;

public class TaskViewWeb extends NtroViewWeb implements TaskView {
	
	private HtmlElements addTaskIdToValue;
	private HtmlElements addTaskIdToDataTarget;
	private HtmlElements addTaskIdToId;
	private HtmlElement taskTitleLink;
	private HtmlElement previousTasksContainer;
	private HtmlElement subTasksContainer;
	private HtmlElement nextTasksContainer;

	private String taskTitleHref;

	@Override
	public void initializeViewWeb(NtroContext<?> context) {
		T.call(this);
		
		addTaskIdToValue = getRootElement().find(".add-task-id-to-value");
		addTaskIdToDataTarget = getRootElement().find(".add-task-id-to-data-target");
		addTaskIdToId = getRootElement().find(".add-task-id-to-id");
		taskTitleLink = getRootElement().find("#task-title-link").get(0);
		previousTasksContainer = getRootElement().find("#previous-tasks-container").get(0);
		subTasksContainer = getRootElement().find("#subtasks-container").get(0);
		nextTasksContainer = getRootElement().find("#next-tasks-container").get(0);
		
		Ntro.verify(that(addTaskIdToValue.size() > 0).isTrue());
		Ntro.verify(that(addTaskIdToDataTarget.size() > 0).isTrue());
		Ntro.verify(that(addTaskIdToId.size() > 0).isTrue());
		Ntro.verify(that(taskTitleLink).isNotEqualTo(null));
		Ntro.verify(that(previousTasksContainer).isNotEqualTo(null));
		Ntro.verify(that(subTasksContainer).isNotEqualTo(null));
		Ntro.verify(that(nextTasksContainer).isNotEqualTo(null));
		
		
		taskTitleHref = taskTitleLink.getAttribute("href");
	}

	@Override
	public void displayTask(String courseId, Task task) {
		T.call(this);
		
		taskTitleLink.html(task.getTitle());
		taskTitleLink.setAttribute("href", taskTitleHref + courseId + "/" + task.id());

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
		
		task.forEachPreviousTask(pt -> {
			addTaskLi(courseId, pt, previousTasksContainer);
		});

		task.forEachSubTask(st -> {
			addTaskLi(courseId, st, subTasksContainer);
		});

		task.forEachNextTask(nt -> {
			addTaskLi(courseId, nt, nextTasksContainer);
		});
	}

	private void addTaskLi(String courseId, Task task, HtmlElement container) {
		T.call(this);

		HtmlElement taskLi = container.createElement("<li></li>");
		HtmlElement anchor = taskLi.createElement("<a></a>");
		taskLi.appendElement(anchor);
		container.appendElement(taskLi);

		anchor.text(task.getTitle());
		anchor.setAttribute("href", "/cours/" + courseId + task.id());
	}

}
