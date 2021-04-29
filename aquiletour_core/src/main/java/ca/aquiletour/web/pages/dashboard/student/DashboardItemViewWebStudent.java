package ca.aquiletour.web.pages.dashboard.student;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.dashboard.models.DashboardItem;
import ca.aquiletour.core.pages.dashboard.student.views.DashboardCourseViewStudent;
import ca.aquiletour.web.pages.dashboard.DashboardItemViewWeb;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;

public class DashboardItemViewWebStudent extends DashboardItemViewWeb implements DashboardCourseViewStudent {

	private HtmlElement makeAppointmentForm;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);
		super.initializeViewWeb(context);

		makeAppointmentForm = this.getRootElement().find("#make-appointment-form").get(0);

		MustNot.beNull(makeAppointmentForm);
    }

	@Override
	public void identifyDashboardItem(DashboardItem item) {
		T.call(this);
		super.identifyDashboardItem(item);
		
		makeAppointmentForm.setAttribute("action", "/" + Constants.QUEUE_URL_SEGMENT + "/" + item.getCoursePath().teacherId());
	}
}
