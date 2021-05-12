package ca.aquiletour.core.pages.semester_list.teacher.views;

import ca.aquiletour.core.models.schedule.ScheduleItem;
import ca.aquiletour.core.pages.semester_list.models.CourseGroup;
import ca.aquiletour.core.pages.semester_list.views.SemesterView;

public interface SemesterViewTeacher extends SemesterView {

	void appendCourseGroupe(CourseGroup courseGroup);
	void appendScheduleItem(ScheduleItem item);
	void displayScheduleSummary(String scheduleSummaryText);
	void displayAvailabilitySummary(String availabilitySummaryText);
	void enableCurrentSemesterSelector(boolean enabled);

}
