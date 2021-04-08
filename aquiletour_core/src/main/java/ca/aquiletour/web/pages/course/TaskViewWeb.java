package ca.aquiletour.web.pages.course;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.pages.course.views.TaskView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;
import ca.ntro.web.mvc.NtroViewWeb;

public class TaskViewWeb extends NtroViewWeb implements TaskView {
	
	private HtmlElement taskTitleLink;
	private HtmlElement previousTasksContainer;
	private HtmlElement subTasksContainer;
	private HtmlElement nextTasksContainer;

	@Override
	public void initializeViewWeb(NtroContext<?> context) {
		T.call(this);

		taskTitleLink = getRootElement().find("#task-title-link").get(0);
		previousTasksContainer = getRootElement().find("#previous-tasks-container").get(0);
		subTasksContainer = getRootElement().find("#subtasks-container").get(0);
		nextTasksContainer = getRootElement().find("#next-tasks-container").get(0);
		
		
		MustNot.beNull(taskTitleLink);
		MustNot.beNull(previousTasksContainer);
		MustNot.beNull(subTasksContainer);
		MustNot.beNull(nextTasksContainer);
	}

	@Override
	public void displayTask(String courseId, Task task) {
		T.call(this);
		
		taskTitleLink.html(task.getTitle());
		taskTitleLink.setAttribute("href", "/" + Constants.COURSE_URL_SEGMENT 
	                                     	+ "/" + courseId 
	                                     	+ task.id()
	                                     	+ "?" + Constants.USER_URL_PARAM + "=" + Ntro.userService().user().getId()
	                                     	+ "&" + Constants.SEMESTER_URL_PARAM + "=" + "H2021");
		
		task.forEachPreviousTask(pt -> {
			addTaskLi(courseId, task, pt, previousTasksContainer, "PreviousTask");
		});

		task.forEachSubTask(st -> {
			addTaskLi(courseId, task, st, subTasksContainer, "SubTask");
		});

		task.forEachNextTask(nt -> {
			addTaskLi(courseId, task, nt, nextTasksContainer, "NextTask");
		});
	}

	protected void addTaskLi(String courseId, 
						     Task currentTask,
			                 Task task, 
			                 HtmlElement container, 
			                 String taskType) {
		T.call(this);

		HtmlElement taskLi = container.createElement("<li></li>");
		HtmlElement anchor = taskLi.createElement("<a></a>");
		HtmlElement deleteAnchor = taskLi.createElement("<a>⊟</a>");
		taskLi.appendElement(anchor);
		taskLi.appendHtml("&nbsp;&nbsp;");
		taskLi.appendElement(deleteAnchor);
		container.appendElement(taskLi);

		anchor.text(task.getTitle());
		anchor.setAttribute("href", "/cours/" + courseId + task.id());
		
		deleteAnchor.setAttribute("href", "?remove" + taskType + "=" + task.getPath().toFileName() + "&from=" + currentTask.getPath().toFileName());
	}

	@Override
	public void clear() {
		T.call(this);
		
		previousTasksContainer.deleteChildrenForever();
		subTasksContainer.deleteChildrenForever();
		nextTasksContainer.deleteChildrenForever();
		
	}

}
