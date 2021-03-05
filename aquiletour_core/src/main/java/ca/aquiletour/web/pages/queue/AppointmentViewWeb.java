package ca.aquiletour.web.pages.queue;

import ca.aquiletour.core.pages.queue.AppointmentView;
import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;
import ca.ntro.web.mvc.NtroViewWeb;

public class AppointmentViewWeb extends NtroViewWeb implements AppointmentView {

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayAppointement(Appointment appointment) {
		T.call(this);

		HtmlElement studentId = this.getRootElement().children("#studentId").get(0);
		HtmlElement studentSurname = this.getRootElement().children("#studentSurname").get(0);
		HtmlElement studentName = this.getRootElement().children("#studentName").get(0);
		HtmlElements ids = this.getRootElement().find(".appointmentId");
		for(int i = 0; i < ids.size(); i++) {
			HtmlElement id = ids.get(i);
			id.appendHtml(appointment.getId());
			id.setAttribute("value", appointment.getId());
		}

		MustNot.beNull(studentId);
		MustNot.beNull(studentSurname);
		MustNot.beNull(studentName);
		studentId.appendHtml(appointment.getStudentId());
		studentSurname.appendHtml(appointment.getStudentSurname());
		studentName.appendHtml(appointment.getStudentName());
		
		getRootElement().setAttribute("id", "appointment-" + appointment.getId());
	}



}
