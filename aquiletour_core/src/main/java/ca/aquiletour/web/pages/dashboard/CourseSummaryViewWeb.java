package ca.aquiletour.web.pages.dashboard;

import ca.aquiletour.core.pages.dashboard.CourseSummaryView;
import ca.aquiletour.core.pages.dashboard.values.CourseSummary;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class CourseSummaryViewWeb extends NtroViewWeb implements CourseSummaryView {

	@Override
	public void displaySummary(CourseSummary course) {
		T.call(this);

		HtmlElement title = this.getRootElement().children("#course-title").get(0);
		HtmlElement summaryText = this.getRootElement().children("#summary-text").get(0);
		HtmlElement summaryDate = this.getRootElement().children("#summary-date").get(0);
		
		MustNot.beNull(title);
		MustNot.beNull(summaryText);
		MustNot.beNull(summaryDate);
		
		title.appendHtml(course.getTitle());
		summaryText.appendHtml(course.getSummary());
		summaryDate.appendHtml(course.getDate());
	}
}
