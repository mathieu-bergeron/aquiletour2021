package ca.aquiletour.core.pages.dashboard.views;

import ca.ntro.core.mvc.NtroView;

public interface DashboardView extends NtroView  {

	void appendDashboardItem(DashboardItemView itemView);

}
