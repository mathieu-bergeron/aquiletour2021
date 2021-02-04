package ca.aquiletour.web.pages.queue;

import ca.aquiletour.core.pages.queue.QueueView;
import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.ntro.core.mvc.view.ViewLoader;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;
import ca.ntro.web.mvc.ViewLoaderWeb;

public class QueueViewWeb extends NtroViewWeb implements QueueView {
	
	private ViewLoaderWeb appointmentViewLoader;



	@Override
	public void appendAppointement(Appointment appointment) {
		T.call(this);
		T.here();
		
		HtmlElement container = this.getRootElement().children("#appointments-container").get(0);
		
		MustNot.beNull(container);
		
		AppointmentViewWeb appointmentView = (AppointmentViewWeb) appointmentViewLoader.createView();
		
		appointmentView.displayAppointement(appointment);
		
		container.appendElement(appointmentView.getRootElement());
	}

	@Override
	public void deleteAppointment(String appointmentId) {
		T.call(this);
		
	}

	@Override
	public void setAppointmentViewLoader(ViewLoader appointmentViewLoader) {
		// TODO Auto-generated method stub
		T.call(this);
		T.here();
		this.appointmentViewLoader = (ViewLoaderWeb) appointmentViewLoader;
	}


}
