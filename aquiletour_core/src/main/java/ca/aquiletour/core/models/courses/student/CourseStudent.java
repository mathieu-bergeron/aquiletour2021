package ca.aquiletour.core.models.courses.student;

import ca.aquiletour.core.models.courses.base.Course;
import ca.aquiletour.core.models.courses.teacher.CourseTeacher;
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

	public StoredString getGroupId() {
		return groupId;
	}

	public void setGroupId(StoredString groupId) {
		this.groupId = groupId;
	}

	public StudentCompletionsByTaskId getCompletions() {
		return completions;
	}

	public void setCompletions(StudentCompletionsByTaskId completions) {
		this.completions = completions;
	}



}
