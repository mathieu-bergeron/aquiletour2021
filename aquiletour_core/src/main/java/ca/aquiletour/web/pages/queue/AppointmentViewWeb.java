package ca.aquiletour.web.pages.queue;

import ca.aquiletour.core.pages.queue.AppointmentView;
import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class AppointmentViewWeb extends NtroViewWeb implements AppointmentView {

	private HtmlElement studentName;
	private HtmlElement appointmentIdInput;
	
	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);

		studentName = this.getRootElement().find("#student-name").get(0);
		appointmentIdInput = this.getRootElement().find("#appointment-id-input").get(0);

		MustNot.beNull(studentName);
		MustNot.beNull(appointmentIdInput);
	}

	@Override
	public void displayAppointement(String queueId, String userId, Appointment appointment) {
		String userName = appointment.getStudentName();
		if(appointment.getStudentSurname().length() > 0) {
			userName += " " + appointment.getStudentSurname();
		}
		
		studentName.text(userName);
		
		appointmentIdInput.value(appointment.getId());

		getRootElement().setAttribute("id", "appointment-" + appointment.getId());
	}
}
