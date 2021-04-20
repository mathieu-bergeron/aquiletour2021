package ca.aquiletour.core.pages.semester_list.models;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.dates.SemesterWeek;
import ca.aquiletour.core.models.schedule.ObservableScheduleItems;
import ca.aquiletour.core.models.schedule.ScheduleItem;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;

public class SemesterModel implements NtroModel {
	
	private String semesterId = Constants.DRAFTS_SEMESTER_ID;
	private ObservableSemesterWeekList weeks = new ObservableSemesterWeekList();
	private ObservableCourseGroupList courseGroups = new ObservableCourseGroupList();
	private ObservableScheduleItems scheduleItems = new ObservableScheduleItems();

	public String getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(String semesterId) {
		this.semesterId = semesterId;
	}

	public ObservableSemesterWeekList getWeeks() {
		return weeks;
	}

	public void setWeeks(ObservableSemesterWeekList weeks) {
		this.weeks = weeks;
	}
	
	public ObservableCourseGroupList getCourseGroups() {
		return courseGroups;
	}

	public void setCourseGroups(ObservableCourseGroupList courseGroups) {
		this.courseGroups = courseGroups;
	}

	public ObservableScheduleItems getScheduleItems() {
		return scheduleItems;
	}

	public void setScheduleItems(ObservableScheduleItems scheduleItems) {
		this.scheduleItems = scheduleItems;
	}

	public void addWeek(SemesterWeek semesterWeek) {
		T.call(this);
		
		weeks.addItem(semesterWeek);
	}

	public void addCourseGroup(String courseId, String groupId) {
		T.call(this);

		getCourseGroups().addItem(new CourseGroup(courseId, groupId));
	}

	public void addScheduleItem(ScheduleItem scheduleItem) {
		T.call(this);
		
		getScheduleItems().addItem(scheduleItem);
	}
}
