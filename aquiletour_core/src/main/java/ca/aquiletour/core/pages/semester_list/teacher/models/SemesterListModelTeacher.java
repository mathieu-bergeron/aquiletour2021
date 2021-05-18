package ca.aquiletour.core.pages.semester_list.teacher.models;

import ca.aquiletour.core.models.schedule.ScheduleItem;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.aquiletour.core.pages.semester_list.models.SemesterListModel;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;

public class SemesterListModelTeacher extends SemesterListModel<SemesterModelTeacher> {

	public void addCourseGroup(String semesterId, String courseId, String groupId) {
		T.call(this);

		SemesterModelTeacher semester = semesterById(semesterId);
		
		if(semester != null) {
			
			semester.addCourseGroup(courseId, groupId);
			
		} else {
			
			Log.warning("Semester not found: " + semesterId);
		}
	}

	public void addScheduleItem(String semesterId, ScheduleItem scheduleItem) {
		T.call(this);

		SemesterModelTeacher semester = semesterById(semesterId);
		
		if(semester != null) {
			
			semester.addScheduleItem(scheduleItem);
			
		} else {
			
			Log.warning("Semester not found: " + semesterId);
		}
		
	}

	public TeacherSchedule teacherSchedule(String semesterId) {
		T.call(this);

		SemesterModelTeacher semester = semesterById(semesterId);
		TeacherSchedule schedule = null;
		
		if(semester != null) {
			
			schedule = semester.getTeacherSchedule();
			
		} else {
			
			Log.warning("Semester not found: " + semesterId);
		}
		
		return schedule;
	}

}
