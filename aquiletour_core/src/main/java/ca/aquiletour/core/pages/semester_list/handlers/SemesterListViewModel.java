package ca.aquiletour.core.pages.semester_list.handlers;


import ca.aquiletour.core.models.dates.CalendarWeek;
import ca.aquiletour.core.pages.semester_list.models.SemesterListModel;
import ca.aquiletour.core.pages.semester_list.models.SemesterModel;
import ca.aquiletour.core.pages.semester_list.views.SemesterListView;
import ca.aquiletour.core.pages.semester_list.views.SemesterView;
import ca.ntro.core.models.listeners.ItemAddedListener;
import ca.ntro.core.mvc.ModelViewSubViewHandler;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public abstract class SemesterListViewModel<SLM extends SemesterListModel, 
                                            SLV extends SemesterListView, 
                                            SV extends SemesterView> 

       extends ModelViewSubViewHandler<SLM, SLV> {

	@Override
	protected void handle(SLM model, SLV view, ViewLoader subViewLoader) {
		T.call(this);

		model.getSemesters().onItemAdded(new ItemAddedListener<SemesterModel>() {
			@Override
			public void onItemAdded(int index, SemesterModel item) {
				T.call(this);

				@SuppressWarnings("unchecked")
				SV semesterView = (SV) subViewLoader.createView();

				observeSemester(view, semesterView, item);
			}
		});
	}

	protected void observeSemester(SLV view, SV semesterView, SemesterModel semester) {
		T.call(this);

		displaySemester(semester, semesterView);
		
		view.appendSemester(semesterView);
		
		semester.getSemesterSchedule().getWeeks().removeObservers();
		semester.getSemesterSchedule().getWeeks().onItemAdded(new ItemAddedListener<CalendarWeek>() {
			@Override
			public void onItemAdded(int index, CalendarWeek item) {
				T.call(this);

				semesterView.appendSemesterWeek(item);
				semesterView.displayCalendarSummary(semester.semesterSummary());
			}
		});
	}

	protected void displaySemester(SemesterModel semester, SV semesterView) {
		T.call(this);

		semesterView.displaySemester(semester);
		semesterView.displayCalendarSummary(semester.semesterSummary());
	}
}
