package ca.aquiletour.web.pages.dashboard;

import ca.aquiletour.core.pages.dashboards.DashboardItemView;
import ca.aquiletour.core.pages.dashboards.DashboardView;
import ca.aquiletour.core.pages.dashboards.values.DashboardItem;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public abstract class DashboardViewWeb extends NtroViewWeb implements DashboardView {

	private HtmlElement dashboardItemContainer;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);

		dashboardItemContainer = this.getRootElement().find("#dashboard-item-container").get(0);

		MustNot.beNull(dashboardItemContainer);
	}

	@Override
	public void appendDashboardItem(DashboardItemView itemView) {
		T.call(this);
		
		DashboardItemViewWeb subView = (DashboardItemViewWeb) itemView;

		dashboardItemContainer.appendElement(subView.getRootElement());
	}
}
