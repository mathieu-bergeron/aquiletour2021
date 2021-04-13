package ca.aquiletour.web.pages.queue.student;

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

public class StudentAppointmentViewWeb extends AppointmentViewWeb implements AppointmentView {

	private HtmlElement deleteAppointmentButton;
	private HtmlElement deleteAppointmentForm;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);
		super.initializeViewWeb(context);

		deleteAppointmentButton = this.getRootElement().find("#delete-appointment-button").get(0);
		deleteAppointmentForm = this.getRootElement().find("#delete-appointment-form").get(0);

		MustNot.beNull(deleteAppointmentButton);
		MustNot.beNull(deleteAppointmentForm);
		
		deleteAppointmentForm.hide();
	}

	@Override
	public void displayAppointement(String queueId, String userId, Appointment appointment) {
		T.call(this);
		super.displayAppointement(queueId, userId, appointment);
		
		if(appointment.getStudentId().equals(userId)) {
			deleteAppointmentForm.show();
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
}
