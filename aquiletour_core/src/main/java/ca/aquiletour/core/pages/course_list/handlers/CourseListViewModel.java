package ca.aquiletour.core.pages.course_list.handlers;

import java.util.List;

import ca.aquiletour.core.pages.course_list.models.CourseListModel;
import ca.aquiletour.core.pages.course_list.views.CourseListView;
import ca.ntro.core.models.listeners.ListObserver;
import ca.ntro.core.mvc.ModelViewSubViewHandler;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public class CourseListViewModel extends ModelViewSubViewHandler<CourseListModel, CourseListView> {

	@Override
	protected void handle(CourseListModel model, CourseListView view, ViewLoader subViewLoader) {
		T.call(this);
		
		view.insertIntoSemesterDropdown(0, "brouillons");

		model.getSemesters().observe(new ListObserver<String>() {
			
			@Override
			public void onItemRemoved(int index, String item) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onItemUpdated(int index, String item) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onItemAdded(int index, String item) {
				T.call(this);

				view.insertIntoSemesterDropdown(index, item);
			}
			
			@Override
			public void onDeleted(List<String> lastValue) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValue(List<String> value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValueChanged(List<String> oldValue, List<String> value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onClearItems() {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
}
