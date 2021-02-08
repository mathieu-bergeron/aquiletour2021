package ca.aquiletour.web.pages.queue;

import ca.aquiletour.core.pages.queue.AppointmentView;
import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
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
		HtmlElement appointmentId = this.getRootElement().children("#appointmentId").get(0);
		HtmlElement close = this.getRootElement().children("#close").get(0);
		MustNot.beNull(time);
		MustNot.beNull(appointmentId);
		MustNot.beNull(close);
		
		time.appendHtml(appointment.getTime());
		appointmentId.appendHtml(appointment.getAppointmentId());
		close.setAttribute("href", "queue?deleteAppointment=" + appointment.getAppointmentId());
		
		getRootElement().setAttribute("id", "appointment-" + appointment.getAppointmentId());
	}



}
