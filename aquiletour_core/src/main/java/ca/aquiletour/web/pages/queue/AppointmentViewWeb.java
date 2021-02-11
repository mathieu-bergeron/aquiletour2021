package ca.aquiletour.web.pages.queue;

import ca.aquiletour.core.pages.queue.AppointmentView;
import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;
import ca.ntro.web.mvc.NtroViewWeb;

public class AppointmentViewWeb extends NtroViewWeb implements AppointmentView {

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayAppointement(Appointment appointment) {
		T.call(this);

		HtmlElement time = this.getRootElement().children("#time").get(0);
		HtmlElements ids = this.getRootElement().children(".appointmentId");
		for(int i = 0; i < ids.size(); i++) {
			HtmlElement id = ids.get(i);
			id.appendHtml(appointment.getAppointmentId());
			id.value(appointment.getAppointmentId());
			id.setAttribute("value", appointment.getAppointmentId());
		}

		MustNot.beNull(time);
		time.appendHtml(appointment.getTime());
		
		getRootElement().setAttribute("id", "appointment-" + appointment.getAppointmentId());
	}



}
