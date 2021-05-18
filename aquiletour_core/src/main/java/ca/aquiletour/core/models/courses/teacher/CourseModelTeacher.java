package ca.aquiletour.core.models.courses.teacher;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTaskCompletion;
import ca.aquiletour.core.models.courses.base.CourseModel;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.courses.group_description.GroupDescriptions;
import ca.aquiletour.core.models.courses.student.CompletionByAtomicTaskId;
import ca.aquiletour.core.models.courses.student.StudentCompletionsByTaskId;
import ca.aquiletour.core.models.dates.AquiletourDate;
import ca.aquiletour.core.models.dates.SemesterDate;
import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.dashboard.student.models.CurrentTaskStudent;
import ca.aquiletour.core.pages.dashboard.teacher.models.CurrentTaskTeacher;
import ca.ntro.core.Path;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;

public class CourseModelTeacher extends CourseModel {

	private GroupDescriptions groups = new GroupDescriptions();

	private TaskDatesByGroupId scheduledDates = new TaskDatesByGroupId();
	private TaskDatesByGroupId overridenDates = new TaskDatesByGroupId();
	
	private StudentCompletionsByStudentId completions = new StudentCompletionsByStudentId();
	
	public GroupDescriptions getGroups() {
		return groups;
	}

	public void setGroups(GroupDescriptions groups) {
		this.groups = groups;
	}

	public TaskDatesByGroupId getScheduledDates() {
		return scheduledDates;
	}

	public void setScheduledDates(TaskDatesByGroupId scheduledDates) {
		this.scheduledDates = scheduledDates;
	}

	public TaskDatesByGroupId getOverridenDates() {
		return overridenDates;
	}

	public void setOverridenDates(TaskDatesByGroupId overridenDates) {
		this.overridenDates = overridenDates;
	}
	
	
	public StudentCompletionsByStudentId getCompletions() {
		return completions;
	}

	public void setCompletions(StudentCompletionsByStudentId completions) {
		this.completions = completions;
	}

	public void addGroup(String groupId, List<User> studentsToAdd) {
		T.call(this);
		
		GroupDescription group = new GroupDescription();

		getGroups().addItem(group);
		
		group.setGroupId(groupId);
		group.addStudents(studentsToAdd);
	}
	
	
	@Override
	public AquiletourDate taskEndTimeForGroup(String groupId, String taskId) {
		T.call(this);
		
		AquiletourDate endTime = null;
		
		endTime = taskEndTimeIn(groupId, taskId, overridenDates);

		if(endTime == null) {

			endTime = taskEndTimeIn(groupId, taskId, scheduledDates);
		}

		if(endTime == null) {
			
			endTime = super.taskEndTimeForGroup(groupId, taskId);
		}
		
		return endTime;
	}

	private AquiletourDate taskEndTimeIn(String groupId, String taskId, TaskDatesByGroupId datesByGroupId) {
		T.call(this);
		
		AquiletourDate endTime = null;
		
		EndTimeByTaskId datesByTaskId = datesByGroupId.valueOf(groupId);
		
		if(datesByTaskId != null) {
			
			endTime = datesByTaskId.valueOf(taskId);
		}

		return endTime;
	}

	@Override
	protected void updateGroupSchedules(SemesterSchedule semesterSchedule, TeacherSchedule teacherSchedule) {
		T.call(this);

		scheduledDates.clear();
		
		for(GroupDescription group : groups.getValue()) {
			updateGroupSchedule(getCoursePath().courseId(), 
					            group.getGroupId(), 
					            semesterSchedule, 
					            teacherSchedule);
		}
	}

	protected void updateGroupSchedule(String courseId, 
			                           String groupId, 
			                           SemesterSchedule semesterSchedule, 
			                           TeacherSchedule teacherSchedule) {
		T.call(this);

		for(Task task : getTasks().getValue().values()) {

			SemesterDate date = task.resolveDate(courseId, groupId, semesterSchedule, teacherSchedule);

			if(date != null) {

				EndTimeByTaskId taskDates = scheduledDates.valueOf(groupId);

				if(taskDates == null) {
					taskDates = new EndTimeByTaskId();
					scheduledDates.putEntry(groupId, taskDates);
				}

				taskDates.putEntry(task.id(), date);
			}
		}
	}

	public void taskCompletedByStudent(Path taskPath, String atomicTaskId, String studentId) {
		T.call(this);
		
		String taskId = pathToId(taskPath);
		
		StudentCompletionsByTaskId studentCompletions = getCompletions().valueOf(studentId);
		
		if(studentCompletions == null) {
			studentCompletions = new StudentCompletionsByTaskId();
			getCompletions().putEntry(studentId, studentCompletions);
		}
		
		String groupId = groupIdForStudent(studentId);
		
		if(groupId != null) {

			CompletionByAtomicTaskId studentTaskCompletions = studentCompletions.valueOf(taskId);
			if(studentTaskCompletions == null) {
				studentTaskCompletions = new CompletionByAtomicTaskId();
				studentCompletions.putEntry(taskId, studentTaskCompletions);
			}
			
			if(atomicTaskId != null && !atomicTaskId.isEmpty()) {

				studentTaskCompletions.putEntry(atomicTaskId, new AtomicTaskCompletion(studentId, groupId));

			}else {

				Log.warning("atomicTaskId is null or empty");
			}

		}else {
			
			Log.warning("groupId not found for studentId " + studentId);
		}
	}
	
	public String groupIdForStudent(String studentId) {
		T.call(this);
		
		String groupId = null;
		
		for(GroupDescription group : groups.getValue()) {
			if(group.getStudents().contains(studentId)) {
				groupId = group.getGroupId();
				break;
			}
		}
		
		return groupId;
	}

	public List<CurrentTaskStudent> currentTasksStudent(String studentId) {
		T.call(this);
		
		List<CurrentTaskStudent> currentTasks = new ArrayList<>();
		
		/* TODO:
		 * 
		 * visit the graph
		 * stop when a task is not completed (and memorize it)
		 * 
		 */
		
		return currentTasks;
	}

	private int numberOfStudents() {
		
		int numberOfStudents = 0;
		
		for(GroupDescription group : getGroups().getValue()) {
			numberOfStudents += group.getStudents().size();
		}
		
		return numberOfStudents;
	}

	public List<CurrentTaskTeacher> currentTasksTeacher() {
		T.call(this);
		
		List<CurrentTaskTeacher> currentTasks = new ArrayList<>();
		
		return currentTasks;
	}

	public void updateAtomicTaskCompletion(Path taskPath, String studentId, String atomicTaskId, AtomicTaskCompletion completionToAdd) {
		T.call(this);

		StudentCompletionsByTaskId studentCompletions = completions.valueOf(studentId);
		if(studentCompletions == null) {
			studentCompletions = new StudentCompletionsByTaskId();
			completions.putEntry(studentId, studentCompletions);
		}

		updateAtomicTaskCompletion(studentCompletions, 
				                taskPath, 
				                atomicTaskId, 
				                completionToAdd);
	}

	public void removeAtomicTaskCompletion(Path taskPath, String studentId, String atomicTaskId) {
		T.call(this);

		StudentCompletionsByTaskId studentCompletions = completions.valueOf(studentId);
		if(studentCompletions != null) {
			removeAtomicTaskCompletion(studentCompletions, 
									taskPath, 
									atomicTaskId);
		}
	}
}
