package ca.aquiletour.web.pages.course;

import ca.aquiletour.core.AquiletourMain;
import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.courses.CoursePath;
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
	public void initializeViewWeb(NtroContext<?,?> context) {
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
	public void displayTask(CoursePath coursePath, Task task) {
		T.call(this);
		
		taskTitleLink.html(task.getTitle());
		
		String href = "/" + Constants.COURSE_URL_SEGMENT  +
	                  "/" + coursePath.toUrlPath() + 
	                  task.id();
		
		if(!AquiletourMain.currentSemester().equals(coursePath.semesterId())) {
			href += "?" + Constants.SEMESTER_URL_PARAM + "=" + coursePath.semesterId();
		}
		
		taskTitleLink.setAttribute("href", href);
		
		task.forEachPreviousTask(pt -> {
			addTaskLi(coursePath.toId(), task, pt, previousTasksContainer, "PreviousTask");
		});

		task.forEachSubTask(st -> {
			addTaskLi(coursePath.toId(), task, st, subTasksContainer, "SubTask");
		});

		task.forEachNextTask(nt -> {
			addTaskLi(coursePath.toId(), task, nt, nextTasksContainer, "NextTask");
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
