package ca.aquiletour.core.models.courses.student;

import ca.aquiletour.core.models.courses.base.CourseModelBase;
import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.ntro.core.models.StoredString;
import ca.ntro.core.system.trace.T;

public class CourseStudent extends CourseModelBase {
	
	private StoredString groupId = new StoredString();
	private StudentCompletionsByTaskId completions = new StudentCompletionsByTaskId();

	@Override
	protected void updateGroupSchedules(SemesterSchedule semesterSchedule, TeacherSchedule teacherSchedule) {
		T.call(this);
		
	}

}
