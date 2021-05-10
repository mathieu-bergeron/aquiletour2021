package ca.aquiletour.core.pages.semester_list.views;

import ca.aquiletour.core.models.dates.CalendarWeek;
import ca.aquiletour.core.models.schedule.ScheduleItem;
import ca.aquiletour.core.pages.semester_list.models.CourseGroup;
import ca.aquiletour.core.pages.semester_list.models.SemesterModel;
import ca.ntro.core.mvc.NtroView;

public interface SemesterView extends NtroView {

	void displaySemester(SemesterModel item);
	void appendSemesterWeek(CalendarWeek item);
	void appendCourseGroupe(CourseGroup courseGroup);
	void appendScheduleItem(ScheduleItem item);
	void displaySemesterSummary(String semesterSummaryText);
	void displayScheduleSummary(String scheduleSummaryText);
	void displayAvailabilitySummary(String availabilitySummaryText);

}
