package ca.aquiletour.core.pages.semester_list.models;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.dates.SemesterWeek;
import ca.aquiletour.core.models.schedule.ScheduleItems;
import ca.aquiletour.core.models.schedule.ScheduleItem;
import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;

public class SemesterModel implements NtroModelValue {
	
	private String semesterId = Constants.DRAFTS_SEMESTER_ID;

	private ObservableCourseGroupList courseGroups = new ObservableCourseGroupList();

	private SemesterSchedule semesterSchedule = new SemesterSchedule();

	private TeacherSchedule teacherSchedule = new TeacherSchedule();
	
	public SemesterSchedule getSemesterSchedule() {
		return semesterSchedule;
	}

	public void setSemesterSchedule(SemesterSchedule semesterSchedule) {
		this.semesterSchedule = semesterSchedule;
	}

	public TeacherSchedule getTeacherSchedule() {
		return teacherSchedule;
	}

	public void setTeacherSchedule(TeacherSchedule teacherSchedule) {
		this.teacherSchedule = teacherSchedule;
	}

	public String getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(String semesterId) {
		this.semesterId = semesterId;
	}

	
	public ObservableCourseGroupList getCourseGroups() {
		return courseGroups;
	}

	public void setCourseGroups(ObservableCourseGroupList courseGroups) {
		this.courseGroups = courseGroups;
	}

	public void addWeek(SemesterWeek semesterWeek) {
		T.call(this);
		
		getSemesterSchedule().addWeek(semesterWeek);
	}

	public void addCourseGroup(String courseId, String groupId) {
		T.call(this);

		getCourseGroups().addItem(new CourseGroup(courseId, groupId));
	}

	public void addScheduleItem(ScheduleItem scheduleItem) {
		T.call(this);
		
		getTeacherSchedule().addScheduleItem(scheduleItem);
	}

	public String semesterSummary() {
		T.call(this);
		
		String summary = "0 semaines";
		

		return summary;
	}

	public String scheduleSummary() {
		T.call(this);
		
		String summary = "0 heures";

		return summary;
	}

	public String availabilitySummary() {
		T.call(this);
		
		String summary = "0 heures";
		
		return summary;
	}
}
