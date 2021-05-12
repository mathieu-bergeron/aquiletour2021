package ca.aquiletour.core.pages.semester_list.teacher.handlers;

import ca.aquiletour.core.models.dates.CalendarWeek;
import ca.aquiletour.core.models.schedule.ScheduleItem;
import ca.aquiletour.core.pages.semester_list.handlers.SemesterListViewModel;
import ca.aquiletour.core.pages.semester_list.models.CourseGroup;
import ca.aquiletour.core.pages.semester_list.models.SemesterModel;
import ca.aquiletour.core.pages.semester_list.teacher.models.SemesterListModelTeacher;
import ca.aquiletour.core.pages.semester_list.teacher.models.SemesterModelTeacher;
import ca.aquiletour.core.pages.semester_list.teacher.views.SemesterListViewTeacher;
import ca.aquiletour.core.pages.semester_list.teacher.views.SemesterViewTeacher;
import ca.ntro.core.models.listeners.ItemAddedListener;
import ca.ntro.core.system.trace.T;

public class SemesterListViewModelTeacher extends SemesterListViewModel<SemesterListModelTeacher, 
																		SemesterModelTeacher,
                                                                        SemesterListViewTeacher, 
                                                                        SemesterViewTeacher> {

	@Override
	protected void observeSemester(SemesterListViewTeacher view, 
			                       SemesterViewTeacher semesterView, 
			                       SemesterModelTeacher semester,
			                       boolean isCurrentSemester) {
		T.call(this);

		super.observeSemester(view, semesterView, semester, isCurrentSemester);
		
		semesterView.enableCurrentSemesterSelector(!semester.getAdminControlled());
		
		semester.getCourseGroups().removeObservers();
		semester.getCourseGroups().onItemAdded(new ItemAddedListener<CourseGroup>() {
			@Override
			public void onItemAdded(int index, CourseGroup item) {
				T.call(this);

				semesterView.appendCourseGroupe(item);
			}
		});
		
		semester.getTeacherSchedule().getScheduleItems().removeObservers();
		semester.getTeacherSchedule().getScheduleItems().onItemAdded(new ItemAddedListener<ScheduleItem>() {
			@Override
			public void onItemAdded(int index, ScheduleItem item) {
				T.call(this);

				semesterView.appendScheduleItem(item);
				semesterView.displayScheduleSummary(semester.scheduleSummary());
			}
		});
	}

	@Override
	protected void displaySemester(SemesterModelTeacher semester, SemesterViewTeacher semesterView, boolean isCurrentSemester) {
		T.call(this);
		
		super.displaySemester(semester, semesterView, isCurrentSemester);

		semesterView.displayScheduleSummary(semester.scheduleSummary());
		semesterView.displayAvailabilitySummary(semester.availabilitySummary());
	}
}
