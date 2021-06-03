package ca.aquiletour.core.models.courses.student;

import static ca.aquiletour.core.models.courses.base.lambdas.VisitDirection.*;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTaskCompletion;
import ca.aquiletour.core.models.courses.base.CourseModel;
import ca.aquiletour.core.models.courses.base.lambdas.FindResults;
import ca.aquiletour.core.models.courses.base.lambdas.VisitDirection;
import ca.aquiletour.core.models.courses.teacher.EndTimeByTaskId;
import ca.aquiletour.core.models.dates.AquiletourDate;
import ca.aquiletour.core.models.dates.SemesterDate;
import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.aquiletour.core.pages.dashboard.student.models.CurrentTaskStudent;
import ca.ntro.core.Path;
import ca.ntro.core.models.StoredString;
import ca.ntro.core.system.trace.T;

public class CourseModelStudent extends CourseModel<CurrentTaskStudent> {
	
	private StoredString groupId = new StoredString();
	private StudentCompletionsByTaskId completions = new StudentCompletionsByTaskId();
	private EndTimeByTaskId endTimes = new EndTimeByTaskId();

	@Override
	protected void updateSchedules(SemesterSchedule semesterSchedule, TeacherSchedule teacherSchedule) {
		T.call(this);
		
		T.here();

		getTasks().forEachEntry((key, task) -> {

			SemesterDate date = task.resolveDate(getCoursePath().courseId(), 
					                             groupId.getValue(), 
					                             semesterSchedule, 
					                             teacherSchedule);

			if(date != null) {
				endTimes.putEntry(key, date);
			}
		});
	}

	@Override
	public AquiletourDate taskEndTimeForGroup(String groupId, String taskId) {
		T.call(this);
		
		AquiletourDate endTime = null;
		
		endTime = endTimes.valueOf(taskId);
		
		if(endTime == null) {
			endTime = super.taskEndTimeForGroup(groupId, taskId);
		}
		
		return endTime;
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

	public EndTimeByTaskId getEndTimes() {
		return endTimes;
	}

	public void setEndTimes(EndTimeByTaskId endTimes) {
		this.endTimes = endTimes;
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

	public void updateCompletions(Path taskPath, String atomicTaskId, AtomicTaskCompletion completion) {
		T.call(this);
		
		CompletionByAtomicTaskId atomicCompletions = getCompletions().valueOf(taskPath.toKey());
		if(atomicCompletions == null) {
			atomicCompletions = new CompletionByAtomicTaskId();
			getCompletions().putEntry(taskPath.toKey(), atomicCompletions);
		}
		
		if(completion != null) {

			atomicCompletions.putEntry(atomicTaskId, completion);

		}else {

			atomicCompletions.putEntry(atomicTaskId, new AtomicTaskCompletion());
		}
	}

}
