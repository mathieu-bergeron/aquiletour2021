package ca.aquiletour.core.pages.dashboard;

import java.util.List;

import ca.aquiletour.core.pages.dashboard.values.CourseSummary;
import ca.ntro.core.models.properties.observable.list.ListObserver;
import ca.ntro.core.mvc.ModelViewSubViewHandler;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public class DashboardViewModel extends ModelViewSubViewHandler<DashboardModel, DashboardView> {

	@Override
	protected void handle(DashboardModel model, DashboardView view, ViewLoader subViewLoader) {
		T.call(this);

		model.getCourses().observe(new ListObserver<CourseSummary>() {

			@Override
			public void onValueChanged(List<CourseSummary> oldValue, List<CourseSummary> value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onValue(List<CourseSummary> value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onDeleted(List<CourseSummary> lastValue) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onItemAdded(int index, CourseSummary item) {
				T.call(this);
				
				CourseSummaryView courseView = (CourseSummaryView) subViewLoader.createView();
				courseView.displaySummary(item);
				
				view.appendCourse(courseView);
			}

			@Override
			public void onItemUpdated(int index, CourseSummary item) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onItemRemoved(int index, CourseSummary item) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	
	
}
