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
import ca.ntro.web.dom.HtmlElements;
import ca.ntro.web.dom.HtmlEventListener;
import ca.ntro.web.mvc.NtroViewWeb;

public class TeacherAppointmentViewWeb extends AppointmentViewWeb implements AppointmentView {

	@Override
	public void initializeViewWeb(NtroContext<?> context) {

	}

	@Override
	public void displayAppointement(Appointment appointment) {
		T.call(this);

		HtmlElement studentId = this.getRootElement().find("#studentId").get(0);
		HtmlElement studentSurname = this.getRootElement().find("#studentSurname").get(0);
		HtmlElement studentName = this.getRootElement().find("#studentName").get(0);
		HtmlElements ids = this.getRootElement().find(".appointmentId");
		HtmlElement closeButton = this.getRootElement().find(".btn-close").get(0);

		MustNot.beNull(studentId);
		MustNot.beNull(studentSurname);
		MustNot.beNull(studentName);
		MustNot.beNull(closeButton);

		for(int i = 0; i < ids.size(); i++) {
			HtmlElement id = ids.get(i);
			id.appendHtml(appointment.getId());
			id.setAttribute("value", appointment.getId());
		}

		studentId.appendHtml(appointment.getStudentId());
		studentSurname.appendHtml(appointment.getStudentSurname());
		studentName.appendHtml(appointment.getStudentName());

		getRootElement().setAttribute("id", "appointment-" + appointment.getId());

		closeButton.addEventListener("click", new HtmlEventListener() {
			@Override
			public void onEvent() {
				DeleteAppointmentMessage deleteAppointmentMessage = Ntro.messages().create(DeleteAppointmentMessage.class);
				// FIXME
				deleteAppointmentMessage.setCourseId("3C6");
				deleteAppointmentMessage.setAppointmentId(appointment.getId());
				Ntro.messages().send(deleteAppointmentMessage);
			}
		});
	}



}
