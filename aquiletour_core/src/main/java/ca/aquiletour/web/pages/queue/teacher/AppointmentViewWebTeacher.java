package ca.aquiletour.web.pages.queue.teacher;

import ca.aquiletour.core.pages.queue.models.Appointment;
import ca.aquiletour.core.pages.queue.views.AppointmentView;
import ca.aquiletour.web.pages.queue.AppointmentViewWeb;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;

public class AppointmentViewWebTeacher extends AppointmentViewWeb implements AppointmentView {

	private HtmlElement deleteAppointmentButton;
	private HtmlElement chatButton;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);
		super.initializeViewWeb(context);

		deleteAppointmentButton = this.getRootElement().find("#delete-appointment-button").get(0);

		chatButton = this.getRootElement().find(".chat-button").get(0);

		MustNot.beNull(deleteAppointmentButton);
		MustNot.beNull(chatButton);

		chatButton.hide();
	}

	@Override
	public void displayAppointement(String queueId, 
			                        String userId, 
			                        String appointmentViewId,
			                        boolean displayTime, 
			                        Appointment appointment) {
		T.call(this);
		super.displayAppointement(queueId, 
				                  userId, 
				                  appointmentViewId,
				                  displayTime, 
				                  appointment);
	}
}
