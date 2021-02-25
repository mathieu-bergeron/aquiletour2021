package ca.aquiletour.web.pages.dashboard;

import ca.aquiletour.core.pages.dashboards.CourseSummaryView;
import ca.aquiletour.core.pages.dashboards.DashboardView;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public abstract class DashboardViewWeb extends NtroViewWeb implements DashboardView {
	
	@Override
	public void initialize() {

	}

	@Override
	public void appendCourse(CourseSummaryView courseView) {
		T.call(this);

		HtmlElement container = this.getRootElement().children("#courses-container").get(0);
		
		MustNot.beNull(container);
		
		CourseSummaryViewWeb courseViewWeb = (CourseSummaryViewWeb) courseView;
		
		container.appendElement(courseViewWeb.getRootElement());
	}
}
