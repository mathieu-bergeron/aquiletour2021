package ca.aquiletour.core.pages.semester_list.handlers;

import java.util.List;

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
				
				SemesterView semesterView = (SemesterView) subViewLoader.createView();
				
				semesterView.displaySemester(item);
				
				view.appendSemester(semesterView);
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

}
