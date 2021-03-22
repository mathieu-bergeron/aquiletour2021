package ca.aquiletour.web.pages.queue.teacher;

import ca.aquiletour.core.pages.queue.AppointmentView;
import ca.aquiletour.core.pages.queue.teacher.messages.DeleteAppointmentMessage;
import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.aquiletour.web.pages.queue.AppointmentViewWeb;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlEventListener;

public class TeacherAppointmentViewWeb extends AppointmentViewWeb implements AppointmentView {

	private HtmlElement deleteAppointmentButton;

	@Override
	public void initializeViewWeb(NtroContext<?> context) {
		T.call(this);
		super.initializeViewWeb(context);

		deleteAppointmentButton = this.getRootElement().find("#delete-appointment-button").get(0);

		MustNot.beNull(deleteAppointmentButton);

	}

	@Override
	public void displayAppointement(String queueId, String userId, Appointment appointment) {
		T.call(this);
		super.displayAppointement(queueId, userId, appointment);

		deleteAppointmentButton.addEventListener("click", new HtmlEventListener() {
			@Override
			public void onEvent() {
				DeleteAppointmentMessage deleteAppointmentMessage = Ntro.messages().create(DeleteAppointmentMessage.class);
				deleteAppointmentMessage.setCourseId(queueId);
				deleteAppointmentMessage.setAppointmentId(appointment.getId());
				Ntro.messages().send(deleteAppointmentMessage);
			}
		});
	}
}
