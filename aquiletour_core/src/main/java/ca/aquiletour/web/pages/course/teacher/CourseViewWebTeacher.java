package ca.aquiletour.web.pages.course.teacher;

import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.dates.AquiletourDate;
import ca.aquiletour.core.models.dates.CourseDateScheduleItem;
import ca.aquiletour.core.pages.course.teacher.views.CourseViewTeacher;
import ca.aquiletour.web.pages.course.CourseViewWeb;
import ca.aquiletour.web.widgets.BootstrapDropdown;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;

public class CourseViewWebTeacher extends CourseViewWeb implements CourseViewTeacher {

	private HtmlElement taskTitleInput;
	private HtmlElement taskDescriptionInput;
	private HtmlElement taskIdInput;
	private HtmlElement taskEndTimeWeek;
	private HtmlElement taskEndTimeScheduleItem;

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

		taskIdInput = this.getRootElement().find("#task-id-input").get(0);
		taskTitleInput = this.getRootElement().find("#task-title-input").get(0);
		taskDescriptionInput = this.getRootElement().find("#task-description-input").get(0);
		taskEndTimeWeek = this.getRootElement().find("#task-endtime-week").get(0);
		taskEndTimeScheduleItem = this.getRootElement().find("#task-endtime-schedule-item").get(0);
		
		MustNot.beNull(semesterDropdownHead);
		MustNot.beNull(semesterDropdownTail);
		MustNot.beNull(groupDropdownHead);
		MustNot.beNull(groupDropdownTail);
		MustNot.beNull(taskEndTimeWeek);
		MustNot.beNull(taskEndTimeScheduleItem);
		MustNot.beNull(taskIdInput);
		MustNot.beNull(taskTitleInput);
		MustNot.beNull(taskDescriptionInput);

		semesterDropdown = new BootstrapDropdown(semesterDropdownHead, semesterDropdownTail);
		groupDropdown = new BootstrapDropdown(groupDropdownHead, groupDropdownTail);
	}

	@Override
	public void insertIntoSemesterDropdown(int index, String semesterId, String href, String text) {
		T.call(this);
		
		semesterDropdown.insert(index, semesterId, href, text);
	}

	@Override
	public void appendToSemesterDropdown(String semesterId, String href, String text) {
		T.call(this);

		semesterDropdown.append(semesterId, href, text);
	}

	@Override
	public void selectSemester(String semesterId) {
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
		
		taskIdInput.value(task.id());
	}

	@Override
	public void displayTaskTitle(String title) {
		T.call(this);
		super.displayTaskTitle(title);

		taskTitleInput.value(title);
	}

	@Override
	public void displayTaskDescription(String description) {
		T.call(this);
		
		taskDescriptionInput.text(description);
	}

	@Override
	public void displayTaskEndTime(AquiletourDate endTime) {
		T.call(this);
		
		if(endTime instanceof CourseDateScheduleItem) {

			CourseDateScheduleItem endTimeScheduleItem = (CourseDateScheduleItem) endTime;
			
			taskEndTimeWeek.children("*").forEach(e -> {
				if(String.valueOf(endTimeScheduleItem.getSemesterWeek()).equals(e.getAttribute("name"))){
					e.setAttribute("selected", "true");
				}else {
					e.removeAttribute("selected");
				}
			});
			
			taskEndTimeScheduleItem.children("*").forEach(e->{
				if(endTimeScheduleItem.getScheduleItemId().equals(e.getAttribute("name"))){
					e.setAttribute("selected", "true");
				}else {
					e.removeAttribute("selected");
				}
			});
		}
	}

}
