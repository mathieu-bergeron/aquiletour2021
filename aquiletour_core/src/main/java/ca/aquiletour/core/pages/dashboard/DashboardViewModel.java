package ca.aquiletour.core.pages.dashboard;

import java.util.List;

import ca.aquiletour.core.pages.dashboard.values.Course;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.models.ViewModel;
import ca.ntro.core.models.properties.observable.list.ListObserver;
import ca.ntro.core.mvc.view.NtroView;
import ca.ntro.core.system.trace.T;

public class DashboardViewModel implements ViewModel {

	@Override
	public void observeAndDisplay(NtroModel model, NtroView view) {
		T.call(this);
		
		DashboardModel dashboardModel = (DashboardModel) model;
		DashboardView dashboardView = (DashboardView) view;
		
		dashboardModel.getCourses().observe(new ListObserver<String>() {

			@Override
			public void onValueChanged(List<String> oldValue, List<String> value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onValue(List<String> value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onDeleted(List<String> lastValue) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onItemAdded(int index, String item) {
				T.call(this);
				
				dashboardView.appendCourse(item);
			}

			@Override
			public void onItemUpdated(int index, String item) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onItemRemoved(int index, String item) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
