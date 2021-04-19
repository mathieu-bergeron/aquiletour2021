package ca.aquiletour.core.pages.semester_list.models;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.dates.SemesterWeek;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;

public class SemesterModel implements NtroModel {
	
	private String semesterId = Constants.COURSE_DRAFTS;
	private ObservableSemesterWeekList weeks = new ObservableSemesterWeekList();
	private ObservableCourseGroupList courseGroups = new ObservableCourseGroupList();

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

	public void addWeek(SemesterWeek semesterWeek) {
		T.call(this);
		
		weeks.addItem(semesterWeek);
	}

	public void addCourseGroup(String courseId, String groupId) {
		T.call(this);

		getCourseGroups().addItem(courseId + "/" + groupId);
	}
}
