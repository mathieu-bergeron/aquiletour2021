package ca.aquiletour.core.pages.semester_list.views;

import ca.aquiletour.core.models.dates.CalendarWeek;
import ca.aquiletour.core.pages.semester_list.models.SemesterModel;
import ca.aquiletour.core.views.ItemView;

public interface SemesterView extends ItemView {

	void displaySemester(SemesterModel item, boolean isCurrentSemester);
	void appendSemesterWeek(CalendarWeek item);
	void displayCalendarSummary(String semesterSummaryText);

}
