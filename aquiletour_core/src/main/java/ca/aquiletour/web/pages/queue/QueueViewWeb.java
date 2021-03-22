package ca.aquiletour.web.pages.queue;

import ca.aquiletour.core.pages.queue.AppointmentView;
import ca.aquiletour.core.pages.queue.QueueView;
import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public abstract class QueueViewWeb extends NtroViewWeb implements QueueView {

	private HtmlElement appointmentList;

	@Override
	public void initializeViewWeb(NtroContext<?> context) {
		T.call(this);

		appointmentList = this.getRootElement().find("#appointment-list").get(0);

		MustNot.beNull(appointmentList);
	}

	@Override
	public void insertAppointment(int index, AppointmentView appointmentView) {
		T.call(this);

		HtmlElement appointmentElement = ((AppointmentViewWeb) appointmentView).getRootElement();

		if(index >= 0 && index < appointmentList.children("*").size()) {

			HtmlElement anchorElement = appointmentList.children("*").get(index);
			System.out.println(anchorElement);
			appointmentElement.insertBefore(anchorElement);

		}else {

			appointmentList.appendElement(appointmentElement);
		}
	}

	@Override
	public void deleteAppointment(String appointmentId) {
		T.call(this);
		
		HtmlElement appointment = appointmentList.find("#appointment-" + appointmentId).get(0);
		
		appointment.deleteForever();
	}

	@Override
	public void clearQueue() {
		T.call(this);

		appointmentList.empty();
	}


}
