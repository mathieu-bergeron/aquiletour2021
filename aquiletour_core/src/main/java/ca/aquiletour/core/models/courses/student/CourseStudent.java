package ca.aquiletour.core.models.courses.student;

import ca.aquiletour.core.models.courses.base.Course;
import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.ntro.core.models.StoredString;
import ca.ntro.core.system.trace.T;

public class CourseStudent extends Course {
	
	private StoredString groupId = new StoredString();
	private StudentCompletionsByTaskId completions = new StudentCompletionsByTaskId();

	@Override
	protected void updateGroupSchedules(SemesterSchedule semesterSchedule, TeacherSchedule teacherSchedule) {
		T.call(this);
		// FIMXE: should be only in CourseTeacher
	}


}
