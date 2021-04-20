package ca.aquiletour.core.pages.semester_list.handlers;


import ca.aquiletour.core.models.dates.SemesterWeek;
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

public class SemesterListViewModel extends ModelViewSubViewHandler<SemesterListModel, SemesterListView> {

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

		semesterView.displaySemester(semester);
		
		view.appendSemester(semesterView);
		
		semester.getWeeks().onItemAdded(new ItemAddedListener<SemesterWeek>() {
			@Override
			public void onItemAdded(int index, SemesterWeek item) {
				T.call(this);

				semesterView.appendSemesterWeek(item);
			}
		});
		
		semester.getCourseGroups().onItemAdded(new ItemAddedListener<CourseGroup>() {
			@Override
			public void onItemAdded(int index, CourseGroup item) {
				T.call(this);

				semesterView.appendCourseGroupe(item);
			}
		});
		
		semester.getScheduleItems().onItemAdded(new ItemAddedListener<ScheduleItem>() {
			@Override
			public void onItemAdded(int index, ScheduleItem item) {
				T.call(this);

				semesterView.appendScheduleItem(item);
			}
		});
	}
}
