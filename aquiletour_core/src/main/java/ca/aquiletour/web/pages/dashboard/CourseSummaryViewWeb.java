package ca.aquiletour.web.pages.dashboard;

import ca.aquiletour.core.pages.course.messages.ShowCourseMessage;
import ca.aquiletour.core.pages.dashboards.CourseSummaryView;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlEventListener;
import ca.ntro.web.mvc.NtroViewWeb;

public abstract class CourseSummaryViewWeb extends NtroViewWeb implements CourseSummaryView {

	private HtmlElement showCourseLink;

	private String showCourseHref;

	@Override
	public void initializeViewWeb(NtroContext<?> context) {
		T.call(this);

		showCourseLink = this.getRootElement().find("#show-course-link").get(0);

		MustNot.beNull(showCourseLink);

		showCourseHref = showCourseLink.getAttribute("href");

	}

	@Override
	public void displaySummary(CourseSummary course) {
		T.call(this);

		installShowCourseListener(course);
	}

	private void installShowCourseListener(CourseSummary course) {
		T.call(this);

		showCourseLink.setAttribute("href", showCourseHref + course.getCourseId());
		
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
