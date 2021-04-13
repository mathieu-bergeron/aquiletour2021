package ca.aquiletour.web.pages.dashboard;

import ca.aquiletour.core.pages.dashboards.DashboardCourseView;
import ca.aquiletour.core.pages.dashboards.DashboardView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public abstract class DashboardViewWeb extends NtroViewWeb implements DashboardView {

	private HtmlElement container;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);

		container = this.getRootElement().find("#courses-container").get(0);

		MustNot.beNull(container);
	}

	@Override
	public void appendCourse(String queueId, DashboardCourseView courseView) {
		T.call(this);

		ActiveCourseSummaryViewWeb courseViewWeb = (ActiveCourseSummaryViewWeb) courseView;
		
		HtmlElement courseViewRoot = courseViewWeb.getRootElement();
		
		courseViewRoot.setAttribute("id", queueId);

		container.appendElement(courseViewWeb.getRootElement());
	}

	@Override
	public void clearCourses() {

		HtmlElement container = this.getRootElement().find("#courses-container").get(0);
		
		MustNot.beNull(container);
		
		container.empty();
	}

	@Override
	public void deleteCourse(String queueId) {
		T.call(this);
		
		container.find("#" + queueId).get(0).deleteForever();
	}
}
