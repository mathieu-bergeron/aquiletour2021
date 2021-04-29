package ca.aquiletour.core.pages.dashboard.views;

import ca.aquiletour.core.pages.dashboard.models.DashboardItem;
import ca.ntro.core.mvc.NtroView;

public interface DashboardItemView extends NtroView {
	
	public void identifyDashboardItem(DashboardItem item);

}
