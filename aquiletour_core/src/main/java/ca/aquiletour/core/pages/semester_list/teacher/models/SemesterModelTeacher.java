package ca.aquiletour.core.pages.semester_list.teacher.models;

import ca.aquiletour.core.models.schedule.ScheduleItem;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.aquiletour.core.pages.semester_list.models.CourseGroup;
import ca.aquiletour.core.pages.semester_list.models.ObservableCourseGroupList;
import ca.aquiletour.core.pages.semester_list.models.SemesterModel;
import ca.ntro.core.system.trace.T;

public class SemesterModelTeacher extends SemesterModel {

	private boolean adminControlled = false;

	private ObservableCourseGroupList courseGroups = new ObservableCourseGroupList();

	private TeacherSchedule teacherSchedule = new TeacherSchedule();

	public TeacherSchedule getTeacherSchedule() {
		return teacherSchedule;
	}

	public void setTeacherSchedule(TeacherSchedule teacherSchedule) {
		this.teacherSchedule = teacherSchedule;
	}

	public ObservableCourseGroupList getCourseGroups() {
		return courseGroups;
	}

	public void setCourseGroups(ObservableCourseGroupList courseGroups) {
		this.courseGroups = courseGroups;
	}

	public String scheduleSummary() {
		T.call(this);
		
		return teacherSchedule.summary();
	}

	public String availabilitySummary() {
		T.call(this);
		
		String summary = "0 heures";
		
		return summary;
	}

	public void addCourseGroup(String courseId, String groupId) {
		T.call(this);

		getCourseGroups().addItem(new CourseGroup(courseId, groupId));
	}

	public void addScheduleItem(ScheduleItem scheduleItem) {
		T.call(this);
		
		getTeacherSchedule().addScheduleItem(scheduleItem);
	}

	public boolean getAdminControlled() {
		return adminControlled;
	}

	public void setAdminControlled(boolean adminControlled) {
		this.adminControlled = adminControlled;
	}
}
