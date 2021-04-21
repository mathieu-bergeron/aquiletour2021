package ca.aquiletour.core.models.courses.teacher;

import ca.aquiletour.core.models.courses.base.CourseModel;
import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.aquiletour.core.pages.course_list.models.ObservableGroupIdList;
import ca.aquiletour.core.pages.course_list.models.ObservableSemesterIdList;


public class CourseModelTeacher extends CourseModel {

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

	public void resolveDates(SemesterSchedule semesterSchedule, TeacherSchedule teacherSchedule) {
		// TODO
	}

}
