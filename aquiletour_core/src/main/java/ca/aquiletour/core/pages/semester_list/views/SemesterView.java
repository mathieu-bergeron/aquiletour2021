package ca.aquiletour.core.pages.semester_list.views;

import ca.aquiletour.core.models.dates.SemesterWeek;
import ca.aquiletour.core.models.schedule.ScheduleItem;
import ca.aquiletour.core.pages.semester_list.models.CourseGroup;
import ca.aquiletour.core.pages.semester_list.models.SemesterModel;
import ca.ntro.core.mvc.NtroView;

public interface SemesterView extends NtroView {

	void displaySemester(SemesterModel item);
	void appendSemesterWeek(SemesterWeek item);
	void appendCourseGroupe(CourseGroup courseGroup);
	void appendScheduleItem(ScheduleItem item);

}
