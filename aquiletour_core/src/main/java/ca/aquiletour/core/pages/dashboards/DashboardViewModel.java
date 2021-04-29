package ca.aquiletour.core.pages.dashboards;


import ca.aquiletour.core.pages.dashboards.values.DashboardItem;
import ca.ntro.core.models.listeners.ItemAddedListener;
import ca.ntro.core.mvc.ModelViewSubViewHandler;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public class DashboardViewModel extends ModelViewSubViewHandler<DashboardModel, DashboardView> {

	@Override
	protected void handle(DashboardModel model, DashboardView view, ViewLoader subViewLoader) {
		T.call(this);
		
		model.getDashboardItems().onItemAdded(new ItemAddedListener<DashboardItem>() {
			@Override
			public void onItemAdded(int index, DashboardItem item) {
				T.call(this);
				
				DashboardItemView subView = (DashboardItemView) subViewLoader.createView();
				
				subView.identifyDashboardItem(item);
				
				view.appendDashboardItem(subView);
			}
		});
	}
}
