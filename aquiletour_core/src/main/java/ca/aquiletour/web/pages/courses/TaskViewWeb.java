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
	private HtmlElement previousTaskContainer;

	private String taskTitleHref;

	@Override
	public void initializeViewWeb(NtroContext<?> context) {
		T.call(this);
		
		addTaskIdToValue = getRootElement().find(".add-task-id-to-value");
		addTaskIdToDataTarget = getRootElement().find(".add-task-id-to-data-target");
		addTaskIdToId = getRootElement().find(".add-task-id-to-id");
		taskTitleLink = getRootElement().find("#task-title-link").get(0);
		previousTaskContainer = getRootElement().find("#previous-tasks-container").get(0);
		
		Ntro.verify(that(addTaskIdToValue.size() > 0).isTrue());
		Ntro.verify(that(addTaskIdToDataTarget.size() > 0).isTrue());
		Ntro.verify(that(addTaskIdToId.size() > 0).isTrue());
		Ntro.verify(that(taskTitleLink).isNotEqualTo(null));
		Ntro.verify(that(previousTaskContainer).isNotEqualTo(null));
		
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
		
		task.forEachSubTask(st -> {
			HtmlElement taskLi = previousTaskContainer.createElement("<li></li>");
			HtmlElement anchor = taskLi.createElement("<a></a>");
			taskLi.appendElement(anchor);
			previousTaskContainer.appendElement(taskLi);

			anchor.text(st.getTitle());
			anchor.setAttribute("href", "/cours/" + courseId + st.id());
		});
	}

}
