package ca.aquiletour.core.pages.dashboards;

import ca.aquiletour.core.pages.dashboards.values.DashboardItem;
import ca.ntro.core.mvc.NtroView;

public interface DashboardItemView extends NtroView {
	
	public void identifyDashboardItem(DashboardItem item);

}
