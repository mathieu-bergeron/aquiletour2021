package ca.aquiletour.web.pages.dashboard;

import ca.aquiletour.core.pages.dashboard.DashboardView;
import ca.aquiletour.core.pages.dashboard.values.CourseSummary;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;
import ca.ntro.web.mvc.ViewLoaderWeb;

public class DashboardViewWeb extends NtroViewWeb implements DashboardView {
	
	private ViewLoaderWeb courseSummaryViewLoader;

	@Override
	public void setCourseSummaryViewLoader(ViewLoader courseSummaryViewLoader) {
		T.call(this);
		
		this.courseSummaryViewLoader = (ViewLoaderWeb) courseSummaryViewLoader;
	}
	
	@Override
	public void appendCourse(CourseSummary courseSummary) {
		T.call(this);

		HtmlElement container = this.getRootElement().children("#courses-container").get(0);
		
		MustNot.beNull(container);
		
		CourseSummaryViewWeb courseSummaryView = (CourseSummaryViewWeb) courseSummaryViewLoader.getView();
		
		courseSummaryView.displaySummary(courseSummary);
		
		container.appendElement(courseSummaryView.getRootElement());
	}


}
