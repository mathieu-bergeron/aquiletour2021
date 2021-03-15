package ca.aquiletour.web.pages.dashboard;

import ca.aquiletour.core.pages.dashboards.CourseSummaryView;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public abstract class CourseSummaryViewWeb extends NtroViewWeb implements CourseSummaryView {

	@Override
	public void initialize(NtroContext<?> context) {
		
	}

	@Override
	public void displaySummary(CourseSummary course) {
		T.call(this);

		HtmlElement title = this.getRootElement().find("#course-title").get(0);
		HtmlElement courseId = this.getRootElement().find("#courseId").get(0);
		HtmlElement nbAppointment = this.getRootElement().find("#nbAppointment").get(0);
		HtmlElement makeAppointmentLink = this.getRootElement().find("#availableLink").get(0);
		HtmlElement myAppointment = this.getRootElement().find("#myAppointment").get(0);
		HtmlElement isQueueOpen = this.getRootElement().find("#isQueueOpen").get(0);
		
		MustNot.beNull(title);
		MustNot.beNull(courseId);
		MustNot.beNull(nbAppointment);
		MustNot.beNull(makeAppointmentLink);
		MustNot.beNull(myAppointment);
		MustNot.beNull(isQueueOpen);
	}


}
