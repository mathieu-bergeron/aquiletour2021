package ca.aquiletour.web.pages.course.teacher;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTask;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.dates.AquiletourDate;
import ca.aquiletour.core.models.dates.CourseDateScheduleItem;
import ca.aquiletour.core.models.paths.CoursePath;
import ca.aquiletour.core.pages.course.teacher.views.CourseViewTeacher;
import ca.aquiletour.web.pages.course.CourseViewWeb;
import ca.aquiletour.web.widgets.BootstrapDropdown;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;

public class CourseViewWebTeacher extends CourseViewWeb implements CourseViewTeacher {

	private HtmlElement taskTitleInput;
	private HtmlElement taskDescriptionInput;
	private HtmlElement taskEndTimeWeek;
	private HtmlElement taskEndTimeScheduleItem;
	private HtmlElement taskEndTimeStartOrEnd;
	private HtmlElement weekOfContainer;
	private HtmlElement weekOfElement;
	private HtmlElement saveButtonContainer;
	private HtmlElement editableEndtime;
	private HtmlElement editableDescription;
	private HtmlElement editableTitle;

	private HtmlElement completionsList;
	private HtmlElement completionsContainer;

	private HtmlElement entryTasksContainer;
	private HtmlElement exitTasksContainer;

	private HtmlElement downloadCourseLogLink;

	private HtmlElements addTaskIdToValue;

	private BootstrapDropdown semesterDropdown;
	private BootstrapDropdown groupDropdown;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);
		super.initializeViewWeb(context);

		HtmlElement semesterDropdownHead = getRootElement().find("#semester-dropdown-head-courseview").get(0);
		HtmlElement semesterDropdownTail = getRootElement().find("#semester-dropdown-tail-courseview").get(0);
		HtmlElement groupDropdownHead = getRootElement().find("#group-dropdown-head-courseview").get(0);
		HtmlElement groupDropdownTail = getRootElement().find("#group-dropdown-tail-courseview").get(0);

		taskTitleInput = this.getRootElement().find("#task-title-input").get(0);
		taskDescriptionInput = this.getRootElement().find("#task-description-input").get(0);
		taskEndTimeWeek = this.getRootElement().find("#task-endtime-week").get(0);
		taskEndTimeScheduleItem = this.getRootElement().find("#task-endtime-schedule-item").get(0);
		taskEndTimeStartOrEnd = this.getRootElement().find("#task-endtime-start-or-end").get(0);
		saveButtonContainer = this.getRootElement().find("#save-button-container").get(0);
		weekOfContainer = this.getRootElement().find("#week-of-container").get(0);
		weekOfElement = this.getRootElement().find("#week-of").get(0);
		editableEndtime = this.getRootElement().find("#editable-endtime").get(0);
		editableDescription = this.getRootElement().find("#editable-description").get(0);
		editableTitle = this.getRootElement().find("#editable-title").get(0);

		completionsList = this.getRootElement().find("#completions-list").get(0);
		completionsContainer = this.getRootElement().find("#completions-container").get(0);


		entryTasksContainer = this.getRootElement().find("#entry-tasks-container").get(0);
		exitTasksContainer = this.getRootElement().find("#exit-tasks-container").get(0);

		downloadCourseLogLink = this.getRootElement().find("#download-course-log-link").get(0);
		
		addTaskIdToValue = this.getRootElement().find(".add-task-id-to-value");

		MustNot.beNull(semesterDropdownHead);
		MustNot.beNull(semesterDropdownTail);
		MustNot.beNull(groupDropdownHead);
		MustNot.beNull(groupDropdownTail);
		MustNot.beNull(taskEndTimeWeek);
		MustNot.beNull(taskEndTimeScheduleItem);
		MustNot.beNull(taskEndTimeStartOrEnd);
		MustNot.beNull(taskTitleInput);
		MustNot.beNull(taskDescriptionInput);
		MustNot.beNull(saveButtonContainer);
		MustNot.beNull(weekOfContainer);
		MustNot.beNull(weekOfElement);
		MustNot.beNull(editableEndtime);
		MustNot.beNull(editableDescription);
		MustNot.beNull(completionsList);
		MustNot.beNull(completionsContainer);
		MustNot.beNull(editableTitle);
		MustNot.beNull(entryTasksContainer);
		MustNot.beNull(exitTasksContainer);
		MustNot.beNull(downloadCourseLogLink);

		semesterDropdown = new BootstrapDropdown(semesterDropdownHead, semesterDropdownTail);
		groupDropdown = new BootstrapDropdown(groupDropdownHead, groupDropdownTail);
	}

	@Override
	public void displayCourseStructureView(boolean shouldDisplay) {
		T.call(this);
		
		if(shouldDisplay) {
			
			editableDescription.show();
			editableEndtime.show();
			saveButtonContainer.show();
			editableTitle.show();
			completionsContainer.hide();
			
		} else {

			editableDescription.hide();
			editableEndtime.hide();
			saveButtonContainer.hide();
			editableTitle.hide();
			completionsContainer.show();
		}
	}

	@Override
	public void insertIntoCategoryDropdown(int index, String semesterId, String href, String text) {
		T.call(this);
		
		semesterDropdown.insert(index, semesterId, href, text);
	}

	@Override
	public void appendToCategoryDropdown(String semesterId, String href, String text) {
		T.call(this);

		semesterDropdown.append(semesterId, href, text);
	}

	@Override
	public void selectCategory(String semesterId) {
		T.call(this);
		
		semesterDropdown.select(semesterId);
	}



	@Override
	public void insertIntoGroupDropdown(int index, String groupId, String href, String text) {
		T.call(this);
		
		groupDropdown.insert(index, groupId, href, text);
	}

	@Override
	public void appendToGroupDropdown(String groupId, String href, String text) {
		T.call(this);

		groupDropdown.append(groupId, href, text);
	}


	@Override
	public void selectGroup(String groupId) {
		T.call(this);
		
		groupDropdown.select(groupId);
	}

	@Override
	public void identifyCurrentTask(CoursePath coursePath, Task task) {
		T.call(this);
		
		addTaskIdToValue.appendToAttribute("value", task.id());
		
		downloadCourseLogLink.setAttribute("href", "/" + Constants.COURSE_LOG_URL_SEGMENT + coursePath.toUrlPath());
	}

	@Override
	public void displayTaskTitle(String title, boolean editable) {
		T.call(this);
		super.displayTaskTitle(title, editable);

		taskTitleInput.value(title);
	}

	@Override
	public void updateTaskDescription(String description, boolean editable) {
		T.call(this);
		
		if(!editable) {
			
			super.updateTaskDescription(description, editable);
			
		}else {

			taskDescriptionInput.text(description);

		}
	}

	@Override
	public void updateTaskEndTime(AquiletourDate endTime, boolean editable) {
		T.call(this);
		
		if(!editable) {

			super.updateTaskEndTime(endTime, editable);

		}else if(endTime instanceof CourseDateScheduleItem) {
			
			displayEditableCourseDate((CourseDateScheduleItem) endTime);
		}
	}

	private void displayEditableCourseDate(CourseDateScheduleItem endTime) {
		T.call(this);
		
		NtroDate weekOf = endTime.getWeekOf();
		if(weekOf.isDefined()) {
			weekOfContainer.show();
			weekOfElement.text(weekOf.format("d MMM"));
			
		}else {
			weekOfContainer.hide();
		}
		
		taskEndTimeWeek.children("*").forEach(e -> {
			if(String.valueOf(endTime.getSemesterWeek()).equals(e.getAttribute("name"))){
				e.setAttribute("selected", "true");
			}else {
				e.removeAttribute("selected");
			}
		});
		
		taskEndTimeScheduleItem.children("*").forEach(e->{
			if(endTime.getScheduleItemId().equals(e.getAttribute("name"))){
				e.setAttribute("selected", "true");
			}else {
				e.removeAttribute("selected");
			}
		});

		taskEndTimeStartOrEnd.children("*").forEach(e->{
			if(endTime.getStartOrEnd().equals(e.getAttribute("name"))){
				e.setAttribute("selected", "true");
			}else {
				e.removeAttribute("selected");
			}
		});
	}

	@Override
	public void appendCompletion(String studentId) {
		T.call(this);
		
		String list = completionsList.text();
		
		if(list == null || list.isEmpty()) {
			
			list = studentId;
			
		}else {
			
			list += ", " + studentId;
			
		}

		completionsList.text(list);
	}

	@Override
	public void appendEntryTask(AtomicTask task) {
		T.call(this);
		
		appendAtomicTask(task, entryTasksContainer);
	}

	@Override
	public void appendExitTask(AtomicTask task) {
		T.call(this);

		appendAtomicTask(task, exitTasksContainer);
	}

	private void appendAtomicTask(AtomicTask task, HtmlElement tasksContainer) {
		T.call(this);
		
		tasksContainer.addClass("border");

		String tasksText = tasksContainer.text();
		
		if(tasksText == null || tasksText.isEmpty()) {
			
			tasksContainer.text(task.toString());

		}else {

			tasksContainer.text(tasksText + ", " + task.toString());
		}
	}

	@Override
	public void clearEntryTasks() {
		T.call(this);

		entryTasksContainer.deleteChildrenForever();
		entryTasksContainer.text("");
		entryTasksContainer.removeClass("border");
	}

	@Override
	public void clearExitTasks() {
		T.call(this);

		exitTasksContainer.deleteChildrenForever();
		exitTasksContainer.text("");
		exitTasksContainer.removeClass("border");
	}

	@Override
	public void clearStudentStatuses() {
		T.call(this);

		completionsList.deleteChildrenForever();
		completionsList.text("");
	}

	@Override
	public void updateCategory(String categoryId, String href, String text) {
		T.call(this);
		
		// TODO
	}



}
