package ca.aquiletour.core.models.courses.model;

import ca.aquiletour.core.models.courses.base.CourseModelBase;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.dates.AquiletourDate;
import ca.aquiletour.core.models.dates.SemesterDate;
import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.aquiletour.core.pages.course_list.models.ObservableGroupIdList;
import ca.aquiletour.core.pages.course_list.models.ObservableSemesterIdList;
import ca.ntro.core.system.trace.T;

public class CourseModel extends CourseModelBase {

	private ObservableSemesterIdList otherSemesters = new ObservableSemesterIdList();
	private ObservableGroupIdList groups = new ObservableGroupIdList();

	private TaskDatesByGroupId scheduledDates = new TaskDatesByGroupId();
	private TaskDatesByGroupId overridenDates = new TaskDatesByGroupId();

	private CompletionsByGroup groupCompletions = new CompletionsByGroup();
	
	public ObservableSemesterIdList getOtherSemesters() {
		return otherSemesters;
	}

	public void setOtherSemesters(ObservableSemesterIdList otherSemesters) {
		this.otherSemesters = otherSemesters;
	}
	
	public ObservableGroupIdList getGroups() {
		return groups;
	}

	public void setGroups(ObservableGroupIdList groups) {
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

	public CompletionsByGroup getGroupCompletions() {
		return groupCompletions;
	}

	public void setGroupCompletions(CompletionsByGroup groupCompletions) {
		this.groupCompletions = groupCompletions;
	}

	public void addGroup(String groupId) {
		T.call(this);

		getGroups().addItem(groupId);
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
		
		for(String groupId : groups.getValue()) {
			updateGroupSchedule(getCoursePath().courseId(), 
					            groupId, 
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
}
