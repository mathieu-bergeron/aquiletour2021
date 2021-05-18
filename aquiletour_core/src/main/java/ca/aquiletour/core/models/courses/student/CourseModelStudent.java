package ca.aquiletour.core.models.courses.student;

import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTaskCompletion;
import ca.aquiletour.core.models.courses.base.CourseModel;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.ntro.core.Path;
import ca.ntro.core.models.StoredString;
import ca.ntro.core.system.trace.T;

public class CourseModelStudent extends CourseModel {
	
	private StoredString groupId = new StoredString();
	private StudentCompletionsByTaskId completions = new StudentCompletionsByTaskId();

	@Override
	protected void updateGroupSchedules(SemesterSchedule semesterSchedule, TeacherSchedule teacherSchedule) {
		T.call(this);
		// FIMXE: only in CourseTeacher?
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

	public void updateAtomicTaskCompletion(Path taskPath, String atomicTaskId, AtomicTaskCompletion newCompletion) {
		T.call(this);
		
		updateAtomicTaskCompletion(completions, 
				                   taskPath,
				                   atomicTaskId, 
				                   newCompletion);
	}
	

}
