package ca.aquiletour.web.pages.queue;

import ca.aquiletour.core.AquiletourMain;
import ca.aquiletour.core.pages.queue.models.Appointment;
import ca.aquiletour.core.pages.queue.views.AppointmentView;
import ca.aquiletour.core.pages.queue.views.QueueView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public abstract class QueueViewWeb extends NtroViewWeb implements QueueView {

	private HtmlElement appointmentList;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);

		appointmentList = this.getRootElement().find("#appointment-list").get(0);

		MustNot.beNull(appointmentList);
	}

	@Override
	public void insertOrUpdateAppointment(int index, Appointment appointment, AppointmentView appointmentView) {
		T.call(this);
		
		AppointmentViewWeb appointmentViewWeb = (AppointmentViewWeb) appointmentView;
		
		HtmlElement appointmentElement = getRootElement().find("#" + Appointment.htmlId(appointment)).get(0);

		if(appointmentElement != null) {

			updateAppointment(appointment, appointmentViewWeb, appointmentElement);


		} else {

			appointmentElement = ((AppointmentViewWeb) appointmentView).getRootElement();

			insertAppointment(index, appointmentElement);
		}
	}

	private void insertAppointment(int index, HtmlElement appointmentElement) {
		T.call(this);

		if(index >= 0 && index < appointmentList.children("*").size()) {

			HtmlElement anchorElement = appointmentList.children("*").get(index);
			appointmentElement.insertBefore(anchorElement);

		}else {

			appointmentList.appendElement(appointmentElement);
		}
	}

	private void updateAppointment(Appointment appointment, 
			                       AppointmentViewWeb appointmentViewWeb, 
			                       HtmlElement appointmentElement) {
		T.call(this);

		NtroContext<?,?> context = AquiletourMain.createNtroContext();

		appointmentViewWeb.setRootElement(appointmentElement);
		appointmentViewWeb.initializeViewWeb(context);
		
		appointmentViewWeb.updateAppointment(appointment);
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
