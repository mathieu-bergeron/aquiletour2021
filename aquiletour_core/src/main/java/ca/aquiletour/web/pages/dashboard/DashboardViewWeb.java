package ca.aquiletour.web.pages.dashboard;

import ca.aquiletour.core.pages.dashboard.CourseSummaryView;
import ca.aquiletour.core.pages.dashboard.DashboardView;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class DashboardViewWeb extends NtroViewWeb implements DashboardView {
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
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
