package ca.aquiletour.core.pages.dashboards;

import java.util.List;

import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.ntro.core.models.listeners.ListObserver;
import ca.ntro.core.models.listeners.ValueObserver;
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
				
				item.getNumberOfAppointments().observe(new ValueObserver<Integer>() {

					@Override
					public void onValue(Integer value) {
						T.call(this);

						courseView.displayNumberOfAppointments(value);
					}

					@Override
					public void onDeleted(Integer lastValue) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onValueChanged(Integer oldValue, Integer value) {
						T.call(this);
						
						T.values(value);

						courseView.displayNumberOfAppointments(value);
					}
				});
				
				item.getNumberOfStudents().observe(new ValueObserver<Integer>() {
					@Override
					public void onValue(Integer value) {
						T.call(this);
						
						courseView.displayNumberOfStudents(value);
					}

					@Override
					public void onDeleted(Integer lastValue) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onValueChanged(Integer oldValue, Integer value) {
						T.call(this);
						
						courseView.displayNumberOfStudents(value);
					}
				});
				
				item.getMyAppointment().observe(new ValueObserver<Boolean>() {
					@Override
					public void onDeleted(Boolean lastValue) {
					}
					
					@Override
					public void onValue(Boolean value) {
						courseView.displayStatus(item.getCourseId(), value, item.getIsQueueOpen().getValue());
					}
					
					@Override
					public void onValueChanged(Boolean oldValue, Boolean value) {
						courseView.displayStatus(item.getCourseId(), value, item.getIsQueueOpen().getValue());
					}
				});
				
				item.getIsQueueOpen().observe(new ValueObserver<Boolean>() {

					@Override
					public void onDeleted(Boolean lastValue) {
					}

					@Override
					public void onValue(Boolean value) {
						courseView.displayStatus(item.getCourseId(), item.getMyAppointment().getValue(), value);
					}

					@Override
					public void onValueChanged(Boolean oldValue, Boolean value) {
						courseView.displayStatus(item.getCourseId(), item.getMyAppointment().getValue(), value);
					}
				});
				
				view.appendCourse(item.getCourseId(), courseView);
			}

			@Override
			public void onItemUpdated(int index, CourseSummary item) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onItemRemoved(int index, CourseSummary item) {
				T.call(this);

				view.deleteCourse(item.getCourseId());
			}

			@Override
			public void onClearItems() {
				T.call(this);
				view.clearCourses();
			}
		});
	}
	
	
	
}
