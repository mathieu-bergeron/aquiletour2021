package ca.aquiletour.web.pages.queue;

import ca.aquiletour.core.pages.queue.AppointmentView;
import ca.aquiletour.core.pages.queue.QueueView;
import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class QueueViewWeb extends NtroViewWeb implements QueueView {

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void appendAppointement(Appointment appointment, AppointmentView appointmentView) {
		T.call(this);
		
		HtmlElement container = this.getRootElement().find("#appointment-list").get(0);
		
		MustNot.beNull(container);
		
		AppointmentViewWeb appointmentViewWeb = (AppointmentViewWeb) appointmentView;
		
		container.appendElement(appointmentViewWeb.getRootElement());
	}

	@Override
	public void deleteAppointment(String appointmentId) {
		T.call(this);

		HtmlElement container = this.getRootElement().find("#appointment-list").get(0);

		String selector = "#appointment-" + appointmentId;

		HtmlElement appointmentElement = container.find(selector).get(0);
		
		appointmentElement.remove();

	}

}
