package ca.aquiletour.web.pages.dashboard;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.dashboards.DashboardItemView;
import ca.aquiletour.core.pages.dashboards.values.DashboardItem;
import ca.ntro.core.models.listeners.ValueObserver;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public abstract class DashboardItemViewWeb extends NtroViewWeb implements DashboardItemView {

	private HtmlElement titleLink;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);

		titleLink = this.getRootElement().find("#course-title-link").get(0);
		
		MustNot.beNull(titleLink);
	}

	@Override
	public void identifyDashboardItem(DashboardItem item) {
		T.call(this);
		
		titleLink.setAttribute("href", "/" + 
									    Constants.COURSE_URL_SEGMENT +
									    item.getCoursePath().toUrlPath());
		
		item.getCourseTitle().removeObservers();
		item.getCourseTitle().observe(new ValueObserver<String>() {
			
			@Override
			public void onDeleted(String lastValue) {
			}
			
			@Override
			public void onValue(String value) {
				T.call(this);
				
				titleLink.text(value);
			}
			
			@Override
			public void onValueChanged(String oldValue, String value) {
				T.call(this);
				
				titleLink.text(value);
			}
		});
	}
}
