package ca.aquiletour.core.pages.semester_list.handlers;


import ca.aquiletour.core.models.dates.CalendarWeek;
import ca.aquiletour.core.models.schedule.ScheduleItem;
import ca.aquiletour.core.pages.semester_list.models.CourseGroup;
import ca.aquiletour.core.pages.semester_list.models.SemesterListModel;
import ca.aquiletour.core.pages.semester_list.models.SemesterModel;
import ca.aquiletour.core.pages.semester_list.views.SemesterListView;
import ca.aquiletour.core.pages.semester_list.views.SemesterView;
import ca.ntro.core.models.listeners.ItemAddedListener;
import ca.ntro.core.mvc.ModelViewSubViewHandler;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public abstract class SemesterListViewModel extends ModelViewSubViewHandler<SemesterListModel, SemesterListView> {

	@Override
	protected void handle(SemesterListModel model, SemesterListView view, ViewLoader subViewLoader) {
		T.call(this);

		model.getSemesters().onItemAdded(new ItemAddedListener<SemesterModel>() {
			@Override
			public void onItemAdded(int index, SemesterModel item) {
				T.call(this);

				observeSemester(view, subViewLoader, item);
			}
		});
	}

	private void observeSemester(SemesterListView view, ViewLoader subViewLoader, SemesterModel semester) {
		T.call(this);

		SemesterView semesterView = (SemesterView) subViewLoader.createView();

		displaySemester(semester, semesterView);
		
		view.appendSemester(semesterView);
		
		semester.getSemesterSchedule().getWeeks().removeObservers();
		semester.getSemesterSchedule().getWeeks().onItemAdded(new ItemAddedListener<CalendarWeek>() {
			@Override
			public void onItemAdded(int index, CalendarWeek item) {
				T.call(this);

				semesterView.appendSemesterWeek(item);
				semesterView.displaySemesterSummary(semester.semesterSummary());
			}
		});
		
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

	private void displaySemester(SemesterModel semester, SemesterView semesterView) {
		T.call(this);

		semesterView.displaySemester(semester);
		semesterView.displaySemesterSummary(semester.semesterSummary());
		semesterView.displayScheduleSummary(semester.scheduleSummary());
		semesterView.displayAvailabilitySummary(semester.availabilitySummary());
	}
}
