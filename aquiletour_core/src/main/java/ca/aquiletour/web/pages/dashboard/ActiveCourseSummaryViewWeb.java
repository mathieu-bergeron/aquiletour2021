package ca.aquiletour.web.pages.dashboard;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.course.messages.ShowCourseMessage;
import ca.aquiletour.core.pages.dashboards.DashboardCourseView;
import ca.aquiletour.core.pages.dashboards.values.DashboardItem;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlEventListener;
import ca.ntro.web.mvc.NtroViewWeb;

public abstract class ActiveCourseSummaryViewWeb extends NtroViewWeb implements DashboardCourseView {

	private HtmlElement showCourseLink;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);

		showCourseLink = this.getRootElement().find("#show-course-link").get(0);

		MustNot.beNull(showCourseLink);

	}

	@Override
	public void displaySummary(DashboardItem course) {
		T.call(this);

		installShowCourseListener(course);
	}

	private void installShowCourseListener(DashboardItem course) {
		T.call(this);

		showCourseLink.setAttribute("href", "/" + Constants.COURSE_URL_SEGMENT + "/" + course.getCourseId());

		showCourseLink.addEventListener("click", new HtmlEventListener() {
			@Override
			public void onEvent() {
				T.call(this);

				ShowCourseMessage showCourseMessage = Ntro.messages().create(ShowCourseMessage.class);
				showCourseMessage.setCourseId(course.getCourseId());
				Ntro.messages().send(showCourseMessage);
			}
		});
	}
}
