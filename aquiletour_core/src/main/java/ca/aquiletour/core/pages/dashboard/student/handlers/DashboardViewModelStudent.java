package ca.aquiletour.core.pages.dashboard.student.handlers;

import ca.aquiletour.core.pages.dashboard.handlers.DashboardViewModel;
import ca.aquiletour.core.pages.dashboard.models.DashboardItem;
import ca.aquiletour.core.pages.dashboard.student.models.CurrentTaskStudent;
import ca.aquiletour.core.pages.dashboard.student.models.DashboardItemStudent;
import ca.aquiletour.core.pages.dashboard.student.models.DashboardModelStudent;
import ca.aquiletour.core.pages.dashboard.student.views.DashboardItemViewStudent;
import ca.aquiletour.core.pages.dashboard.student.views.DashboardViewStudent;
import ca.aquiletour.core.pages.dashboard.views.DashboardItemView;
import ca.ntro.core.models.listeners.ValueObserver;
import ca.ntro.core.system.trace.T;

public class   DashboardViewModelStudent 
	   extends DashboardViewModel<DashboardModelStudent, 
								  CurrentTaskStudent,
								  DashboardViewStudent> {

	protected void observeDashboardItem(DashboardItemStudent dashboardItem, 
			                            DashboardItemViewStudent subView) {
		T.call(this);
		
		super.observeDashboardItem(dashboardItem, subView);

		observeIsQueueOpen(dashboardItem, subView);
	}

	private void observeIsQueueOpen(DashboardItemStudent dashboardItem,
			                        DashboardItemViewStudent subView) {
		T.call(this);
		
		dashboardItem.getIsQueueOpen().removeObservers();
		dashboardItem.getIsQueueOpen().observe(new ValueObserver<Boolean>() {
			
			@Override
			public void onDeleted(Boolean lastValue) {
			}
			
			@Override
			public void onValue(Boolean value) {
				T.call(this);
				
				subView.updateAppointmentButtonsFor(dashboardItem.getCoursePath(), value);
			}
			
			@Override
			public void onValueChanged(Boolean oldValue, Boolean value) {
				T.call(this);

				subView.updateAppointmentButtonsFor(dashboardItem.getCoursePath(), value);
			}
		});
	}
}
