package ca.aquiletour.core.pages.semester_list.handlers;

import java.util.List;

import ca.aquiletour.core.models.dates.SemesterWeek;
import ca.aquiletour.core.models.schedule.ScheduleItem;
import ca.aquiletour.core.pages.semester_list.models.CourseGroup;
import ca.aquiletour.core.pages.semester_list.models.SemesterListModel;
import ca.aquiletour.core.pages.semester_list.models.SemesterModel;
import ca.aquiletour.core.pages.semester_list.views.SemesterListView;
import ca.aquiletour.core.pages.semester_list.views.SemesterView;
import ca.ntro.core.models.listeners.ListObserver;
import ca.ntro.core.mvc.ModelViewSubViewHandler;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public class SemesterListViewModel extends ModelViewSubViewHandler<SemesterListModel, SemesterListView> {

	@Override
	protected void handle(SemesterListModel model, SemesterListView view, ViewLoader subViewLoader) {
		T.call(this);
		
		model.getSemesters().observe(new ListObserver<SemesterModel>() {
			
			@Override
			public void onItemRemoved(int index, SemesterModel item) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onItemUpdated(int index, SemesterModel item) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onItemAdded(int index, SemesterModel item) {
				T.call(this);
				
				observeSemester(view, subViewLoader, item);
			}

			
			@Override
			public void onDeleted(List<SemesterModel> lastValue) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValue(List<SemesterModel> value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValueChanged(List<SemesterModel> oldValue, List<SemesterModel> value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onClearItems() {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	private void observeSemester(SemesterListView view, ViewLoader subViewLoader, SemesterModel semester) {
		T.call(this);

		SemesterView semesterView = (SemesterView) subViewLoader.createView();

		semesterView.displaySemester(semester);
		
		view.appendSemester(semesterView);
		
		semester.getWeeks().observe(new ListObserver<SemesterWeek>() {

			@Override
			public void onValueChanged(List<SemesterWeek> oldValue, List<SemesterWeek> value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onValue(List<SemesterWeek> value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onDeleted(List<SemesterWeek> lastValue) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onItemAdded(int index, SemesterWeek item) {
				T.call(this);
				
				semesterView.appendSemesterWeek(item);
			}

			@Override
			public void onItemUpdated(int index, SemesterWeek item) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onItemRemoved(int index, SemesterWeek item) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onClearItems() {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		semester.getCourseGroups().observe(new ListObserver<CourseGroup>() {

			@Override
			public void onValueChanged(List<CourseGroup> oldValue, List<CourseGroup> value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onValue(List<CourseGroup> value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onDeleted(List<CourseGroup> lastValue) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onItemAdded(int index, CourseGroup item) {
				T.call(this);
				
				semesterView.appendCourseGroupe(item);
			}

			@Override
			public void onItemUpdated(int index, CourseGroup item) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onItemRemoved(int index, CourseGroup item) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onClearItems() {
				// TODO Auto-generated method stub
				
			}
		});
		
		semester.getScheduleItems().observe(new ListObserver<ScheduleItem>() {

			@Override
			public void onValueChanged(List<ScheduleItem> oldValue, List<ScheduleItem> value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onValue(List<ScheduleItem> value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onDeleted(List<ScheduleItem> lastValue) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onItemAdded(int index, ScheduleItem item) {
				T.call(this);
				
				semesterView.appendScheduleItem(item);
			}

			@Override
			public void onItemUpdated(int index, ScheduleItem item) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onItemRemoved(int index, ScheduleItem item) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onClearItems() {
				// TODO Auto-generated method stub
				
			}
		});
		
		
	}

}
