package ca.aquiletour.core.models.courses.model;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.models.courses.base.CourseModelBase;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.courses.group_description.GroupDescriptionLists;
import ca.aquiletour.core.models.courses.student.TaskCompletion;
import ca.aquiletour.core.models.dates.AquiletourDate;
import ca.aquiletour.core.models.dates.SemesterDate;
import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.course_list.models.ObservableSemesterIdList;
import ca.aquiletour.core.pages.dashboard.student.models.CurrentTaskStudent;
import ca.aquiletour.core.pages.dashboard.teacher.models.CurrentTaskTeacher;
import ca.ntro.core.Path;
import ca.ntro.core.system.trace.T;

public class Course extends CourseModelBase {

	private ObservableSemesterIdList otherSemesters = new ObservableSemesterIdList();
	private CourseIdList otherCourses = new CourseIdList();

	private GroupDescriptionLists groups = new GroupDescriptionLists();

	private TaskDatesByGroupId scheduledDates = new TaskDatesByGroupId();
	private TaskDatesByGroupId overridenDates = new TaskDatesByGroupId();
	
	private CompletionsByTaskId completions = new CompletionsByTaskId();
	
	public ObservableSemesterIdList getOtherSemesters() {
		return otherSemesters;
	}

	public void setOtherSemesters(ObservableSemesterIdList otherSemesters) {
		this.otherSemesters = otherSemesters;
	}

	public GroupDescriptionLists getGroups() {
		return groups;
	}

	public void setGroups(GroupDescriptionLists groups) {
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
	
	
	public CompletionsByTaskId getCompletions() {
		return completions;
	}

	public void setCompletions(CompletionsByTaskId completions) {
		this.completions = completions;
	}
	
	public CourseIdList getOtherCourses() {
		return otherCourses;
	}

	public void setOtherCourses(CourseIdList otherCourses) {
		this.otherCourses = otherCourses;
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

	public void taskCompletedByStudent(Path taskPath, String studentId) {
		T.call(this);
		
		String taskId = pathToId(taskPath);
		
		CompletionByStudentId studentCompletions = getCompletions().valueOf(taskId);
		
		if(studentCompletions == null) {
			studentCompletions = new CompletionByStudentId();
			getCompletions().putEntry(taskId, studentCompletions);
		}
		
		String groupId = groupIdForStudent(studentId);

		studentCompletions.putEntry(studentId, new TaskCompletion(studentId, groupId));
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
		
		// FIXME: we need to visit the graph
		for(Task task : getTasks().getValue().values()) {


			CompletionByStudentId completions = getCompletions().valueOf(task.id());
			if(completions == null
					|| (completions != null && completions.containsKey(studentId))){

				CurrentTaskStudent currentTaskStudent = new CurrentTaskStudent();
				currentTaskStudent.setTaskPath(task.getPath());
				currentTaskStudent.updateEndTime(task.getEndTime().getValue());
				currentTasks.add(currentTaskStudent);
			}
		}
		
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
		
		for(Task task : getTasks().getValue().values()) {
			
			CompletionByStudentId completions = getCompletions().valueOf(task.id());
			
			int numberOfIncompletes = numberOfStudents();
			if(completions != null) {
				numberOfIncompletes -= completions.size();
			}
				
			CurrentTaskTeacher currentTaskTeacher = new CurrentTaskTeacher();
			currentTaskTeacher.setTaskPath(task.getPath());
			currentTaskTeacher.updateNumberOfStudents(numberOfIncompletes); 

			currentTasks.add(currentTaskTeacher);
		}
		
		return currentTasks;
	}
}
