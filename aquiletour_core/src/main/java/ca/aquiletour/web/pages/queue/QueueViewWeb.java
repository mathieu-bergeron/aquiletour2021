package ca.aquiletour.web.pages.queue;

import ca.aquiletour.core.pages.queue.AppointmentView;
import ca.aquiletour.core.pages.queue.QueueView;
import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class QueueViewWeb extends NtroViewWeb implements QueueView {

	@Override
	public void initializeViewWeb(NtroContext<?> context) {
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
		
		System.out.println("deleteAppointment: " + appointmentId);

		HtmlElement container = this.getRootElement().find("#appointment-list").get(0);
		
		HtmlElement appointment = container.find("#appointment-" + appointmentId).get(0);
		
		appointment.remove();
	}

	@Override
	public void insertAppointment(int appointmentId, Appointment appointment, AppointmentView appointmentView) {
		// TODO Auto-generated method stub
		//TODO cherche dans les childrens le bon appointment (apres index)
		//TODO container.insertAfter
		HtmlElement container = this.getRootElement().children("#appointment-list").get(0);

		String selector = "#appointment-" + appointmentId;

		HtmlElement appointmentElement = container.children(selector).get(0);

		AppointmentViewWeb appointmentViewWeb = (AppointmentViewWeb) appointmentView;

		appointmentElement.insertAfter(appointmentViewWeb.getRootElement());
	}

	@Override
	public void clearQueue() {

		HtmlElement container = this.getRootElement().find("#appointment-list").get(0);

		MustNot.beNull(container);
		
		container.empty();
	}

}
