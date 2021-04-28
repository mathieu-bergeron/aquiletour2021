package ca.aquiletour.core.models.courses.student;

import ca.aquiletour.core.models.courses.base.CourseModel;
import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.ntro.core.system.trace.T;

public class CourseModelStudent extends CourseModel {

	private CompletionByTaskId completions = new CompletionByTaskId();

	@Override
	protected void updateGroupSchedules(SemesterSchedule semesterSchedule, TeacherSchedule teacherSchedule) {
		T.call(this);
		
	}

}
