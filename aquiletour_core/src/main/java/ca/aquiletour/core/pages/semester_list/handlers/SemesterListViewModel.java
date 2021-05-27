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

public abstract class SemesterListViewModel<SLM extends SemesterListModel<SM>, 
											SM extends SemesterModel,
                                            SLV extends SemesterListView, 
                                            SV extends SemesterView> 

       extends ModelViewSubViewHandler<SLM, SLV> {

	@SuppressWarnings("unchecked")
	@Override
	protected void handle(SLM model, SLV view, ViewLoader subViewLoader) {
		T.call(this);

		model.getSemesters().onItemAdded(new ItemAddedListener<SM>() {
			@Override
			public void onItemAdded(int index, SM item) {
				T.call(this);

				SV semesterView = (SV) subViewLoader.createView();
				
				observeSemester(view, semesterView, item, isCurrentSemester(model, item));
			}
		});
	}

	private boolean isCurrentSemester(SLM model, SM item) {
		T.call(this);

		return model.getCurrentSemesterId().getValue().equals(item.getSemesterId());
	}

	protected void observeSemester(SLV view, SV semesterView, SM semester, boolean isCurrentSemester) {
		T.call(this);

		displaySemester(semester, semesterView, isCurrentSemester);
		
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

	protected void displaySemester(SM semester, SV semesterView, boolean isCurrentSemester) {
		T.call(this);

		semesterView.displaySemester(semester, isCurrentSemester);
		semesterView.displayCalendarSummary(semester.semesterSummary());
	}
}
