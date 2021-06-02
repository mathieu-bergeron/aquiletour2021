package ca.aquiletour.web.pages.course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.courses.base.TaskBreadcrumbs;
import ca.aquiletour.core.models.dates.AquiletourDate;
import ca.aquiletour.core.pages.course.views.CourseView;
import ca.aquiletour.core.pages.course.views.TaskView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public abstract class CourseViewWeb extends NtroViewWeb implements CourseView {

	private HtmlElement taskTitle;
	private HtmlElement subTaskContainer;
	private HtmlElement breadcrumbsContainer;
	private HtmlElement previousTaskContainer;
	private HtmlElement previousTaskList;
	private HtmlElement nextTaskList;
	private HtmlElement nextTaskContainer;


	private HtmlElement uneditableDescription;
	private HtmlElement uneditableEndtime;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);

		subTaskContainer = this.getRootElement().find("#subtask-container").get(0);
		breadcrumbsContainer = this.getRootElement().find("#breadcrumbs-container").get(0);
		taskTitle = this.getRootElement().find("#task-title").get(0);
		previousTaskContainer = this.getRootElement().find("#previous-task-container").get(0);
		nextTaskContainer= this.getRootElement().find("#next-task-container").get(0);
		previousTaskList = this.getRootElement().find("#previous-task-list").get(0);
		nextTaskList = this.getRootElement().find("#next-task-list").get(0);
		uneditableEndtime = this.getRootElement().find("#uneditable-endtime").get(0);
		uneditableDescription = this.getRootElement().find("#uneditable-description").get(0);


		MustNot.beNull(subTaskContainer);
		MustNot.beNull(breadcrumbsContainer);
		MustNot.beNull(taskTitle);
		MustNot.beNull(previousTaskList);
		MustNot.beNull(previousTaskContainer);
		MustNot.beNull(nextTaskList);
		MustNot.beNull(nextTaskContainer);
		MustNot.beNull(uneditableEndtime);
		MustNot.beNull(uneditableDescription);
		
		previousTaskContainer.hide();
		nextTaskContainer.hide();
	}
	
	protected HtmlElement uneditableEndTime() {
		T.call(this);
		
		return uneditableEndtime;
	}

	@Override
	public void showUneditableComponents(boolean show) {
		T.call(this);
		
		if(show) {

			uneditableEndtime.show();
			uneditableDescription.show();

		}else {

			uneditableEndtime.hide();
			uneditableDescription.hide();
		}
	}

	@Override
	public void insertSubtask(int index, TaskView taskView) {
		T.call(this);
		
		HtmlElement taskElement = ((TaskViewWeb) taskView).getRootElement();

		if(index >= 0 && index < subTaskContainer.children("*").size()) {

			HtmlElement anchorElement = subTaskContainer.children("*").get(index);
			subTaskContainer.insertBefore(anchorElement);

		}else {

			subTaskContainer.appendElement(taskElement);
		}
	}

	@Override
	public void displayBreadcrumbs(CoursePath coursePath, TaskBreadcrumbs breadcrumps) {
		T.call(this);
		
		breadcrumbsContainer.deleteChildrenForever();
		
		breadcrumps.forEachTask(t -> {

			HtmlElement taskLi = taskLi(coursePath, "breadcrumb-item", t);

			breadcrumbsContainer.appendElement(taskLi);

			if(t.parent() != null) {
				taskLi.setAttribute("siblings", siblingsJson(coursePath, t).replace("\"", "\\\""));
			}
		});
	}
	
	private String siblingsJson(CoursePath coursePath, Task task) {
		T.call(this);

		List<Map<String, String>> siblings = new ArrayList<>();
		
		task.forEachSibling(t -> {
			Map<String, String> sibling = new HashMap<>();
			
			sibling.put("text", t.getTitle().getValue());
			sibling.put("href", Constants.COURSE_URL_SEGMENT + coursePath.toUrlPath() + t.id());
			
			siblings.add(sibling);
		});
		
		return Ntro.jsonService().toString(siblings);
	}


	@Override
	public void clearSubtasks() {
		T.call(this);
		
		subTaskContainer.deleteChildrenForever();
	}

	@Override
	public void appendSubtask(TaskView taskView) {
		T.call(this);

		HtmlElement taskElement = ((TaskViewWeb) taskView).getRootElement();
		
		subTaskContainer.appendElement(taskElement);
	}

	@Override
	public void clearPreviousTasks() {
		T.call(this);
		
		previousTaskList.deleteChildrenForever();
	}

	@Override
	public void appendPreviousTask(CoursePath coursePath, Task previousTask) {
		T.call(this);
		
		previousTaskList.appendElement(taskLi(coursePath, "list-group-item", previousTask));
	}
	
	private HtmlElement taskLi(CoursePath coursePath, String styleClass, Task task) {
		T.call(this);

		HtmlElement taskLi = taskTitle.createElement("<li></li>");
		taskLi.setAttribute("class", styleClass);

		HtmlElement anchor = taskLi.createElement("<a></a>");
		taskLi.appendElement(anchor);

		anchor.text(task.getTitle().getValue());
		anchor.setAttribute("href", "/" + Constants.COURSE_URL_SEGMENT + coursePath.toUrlPath() + task.id());
		
		return taskLi;
	}

	@Override
	public void clearNextTasks() {
		T.call(this);
		
		nextTaskList.deleteChildrenForever();
	}

	@Override
	public void appendNextTask(CoursePath coursePath, Task nextTask) {
		T.call(this);
		
		nextTaskList.appendElement(taskLi(coursePath, "list-group-item", nextTask));
	}

	@Override
	public void hidePreviousTasks() {
		T.call(this);
		
		previousTaskContainer.hide();
	}

	@Override
	public void showPreviousTasks() {
		T.call(this);
		
		previousTaskContainer.show();
	}

	@Override
	public void hideNextTasks() {
		T.call(this);
		
		nextTaskContainer.hide();
	}

	@Override
	public void showNextTasks() {
		T.call(this);
		
		nextTaskContainer.show();
	}

	@Override
	public void displayTaskTitle(String title, boolean editable) {
		T.call(this);

		taskTitle.text(title);
	}

	@Override
	public void updateTaskDescription(String description, boolean editable) {
		T.call(this);

		uneditableDescription.text(description);
	}

	@Override
	public void updateTaskEndTime(AquiletourDate endTime, boolean editable) {
		T.call(this);

		uneditableEndtime.text(endTime.toString());
	}

	@Override
	public void displayTaskDescription(boolean shouldDisplay) {
		T.call(this);

		if(shouldDisplay) {
			uneditableDescription.show();
		}else {
			uneditableDescription.hide();
		}
	}

	@Override
	public void displayTaskEndTime(boolean shouldDisplay) {
		T.call(this);

		if(shouldDisplay) {
			uneditableEndtime.show();
		
		}else {
			uneditableEndtime.hide();
		}
	}
}
