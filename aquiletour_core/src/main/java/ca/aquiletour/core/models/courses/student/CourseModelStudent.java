package ca.aquiletour.core.models.courses.student;

import static ca.aquiletour.core.models.courses.base.lambdas.VisitDirection.*;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTaskCompletion;
import ca.aquiletour.core.models.courses.base.CourseModel;
import ca.aquiletour.core.models.courses.base.lambdas.FindResults;
import ca.aquiletour.core.models.courses.base.lambdas.VisitDirection;
import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.aquiletour.core.pages.dashboard.student.models.CurrentTaskStudent;
import ca.ntro.core.Path;
import ca.ntro.core.models.StoredString;
import ca.ntro.core.system.trace.T;

public class CourseModelStudent extends CourseModel<CurrentTaskStudent> {
	
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

	public void removeAtomicTaskCompletion(Path taskPath, String atomicTaskId) {
		T.call(this);
		
		removeAtomicTaskCompletion(completions, 
				                   taskPath,
				                   atomicTaskId);
	}

	public AtomicTaskCompletion atomicTaskCompletion(Path taskPath, String atomicTaskId) {
		T.call(this);

		return atomicTaskCompletion(completions, 
				                    taskPath,
				                    atomicTaskId);
	}

	public void updateGroupId(String groupId) {
		T.call(this);
		
		getGroupId().set(groupId);
	}

	@Override
	public List<CurrentTaskStudent> currentTasks() {
		T.call(this);
		
		FindResults findResults = rootTask().findAll(new VisitDirection[]{SUB, NEXT}, true, (task) -> {
			return task.status(getCompletions()).isTodo();
		});
		
		findResults.getResults().sort((result1, result2) -> {
			return Integer.compare(result1.getMinDistance(), result2.getMinDistance());
		});
		
		List<CurrentTaskStudent> currentTasks = new ArrayList<>();
		findResults.getResults().forEach(r -> {
			currentTasks.add(new CurrentTaskStudent(r.getTask()));
		});
		
		return currentTasks;
	}

}
