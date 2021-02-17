package ca.aquiletour.web.pages.dashboard;

import ca.aquiletour.core.pages.dashboards.CourseSummaryView;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class CourseSummaryViewWeb extends NtroViewWeb implements CourseSummaryView {

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displaySummary(CourseSummary course) {
		T.call(this);
		T.here();

		HtmlElement title = this.getRootElement().children("#course-title").get(0);
		HtmlElement summaryText = this.getRootElement().children("#summary-text").get(0);
		HtmlElement summaryDate = this.getRootElement().children("#summary-date").get(0);
		HtmlElement makeAppointmentLink = this.getRootElement().children("#availableLink").get(0);
		
		
		MustNot.beNull(title);
		MustNot.beNull(summaryText);
		MustNot.beNull(summaryDate);
		MustNot.beNull(makeAppointmentLink);

		T.values(course.getTitle(), /*course.getSummary(), course.getDate(),*/ course.getTitle()); //TODO for now replace courseId for title because the data is outdated
		
		title.appendHtml(course.getTitle());
//		summaryText.appendHtml(course.getSummary());
//		summaryDate.appendHtml(course.getDate());
		makeAppointmentLink.setAttribute("href","billetterie/" + course.getTitle() + "?makeAppointment");
	}

}
