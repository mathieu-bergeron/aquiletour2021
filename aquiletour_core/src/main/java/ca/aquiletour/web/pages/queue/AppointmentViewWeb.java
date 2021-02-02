package ca.aquiletour.web.pages.queue;

import ca.aquiletour.core.pages.dashboard.CourseSummaryView;
import ca.aquiletour.core.pages.dashboard.values.CourseSummary;
import ca.aquiletour.core.pages.queue.AppointmentView;
import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class AppointmentViewWeb extends NtroViewWeb implements AppointmentView {

	@Override
	public void displayAppointement(Appointment appointment) {
		// TODO Auto-generated method stub
		T.call(this);

		HtmlElement time = this.getRootElement().children("#"/*TODO*/).get(0);
		
		MustNot.beNull(time);

		
		time.appendHtml(appointment.getTime());
	}
}
