package ca.aquiletour.web.pages.dashboard.student;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.student.StudentCourseSummaryView;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.aquiletour.core.pages.queue.student.messages.AddAppointmentMessage;
import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.aquiletour.web.pages.dashboard.CourseSummaryViewWeb;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlEventListener;

public class StudentCourseSummaryViewWeb extends CourseSummaryViewWeb implements StudentCourseSummaryView {

	public void initializeWebView() {
		// TODO Auto-generated method stub
    }

	@Override
	public void displayStatus(boolean doesStudentHaveAppointment, boolean isTeacherAvailable) {
		
		HtmlElement teacherAvailable = this.getRootElement().find("#buttonAvailable").get(0);

		MustNot.beNull(teacherAvailable);

		if(doesStudentHaveAppointment) {
			teacherAvailable.html("J'ai déjà un rendez-vous");
		}else if(isTeacherAvailable) {
			teacherAvailable.html("Prof disponible");
		}else {
			teacherAvailable.html("Prof non-disponible");
		}
		

	}

	@Override
	public void displaySummary(CourseSummary course) {
		T.call(this);

		HtmlElement title = this.getRootElement().find("#course-title").get(0);
		HtmlElement courseId = this.getRootElement().find("#courseId").get(0);
		HtmlElement makeAppointmentLink = this.getRootElement().find("#availableLink").get(0);

		MustNot.beNull(title);
		MustNot.beNull(courseId);
		MustNot.beNull(makeAppointmentLink);
		
		title.appendHtml(course.getTitle());
		//courseId.appendHtml(course.getCourseId());

		makeAppointmentLink.setAttribute("href","billetterie/" + course.getTitle() + "?makeAppointment");
		
		makeAppointmentLink.addEventListener("click", new HtmlEventListener() {
			@Override
			public void onEvent() {
				AddAppointmentMessage  addAppointmentMessage = Ntro.messages().create(AddAppointmentMessage.class);

				User user = addAppointmentMessage.getUser();

				Appointment appointment = new Appointment();
				appointment.setStudentId(user.getId());
				appointment.setStudentName(user.getName());
				appointment.setStudentSurname(user.getSurname());
				
				addAppointmentMessage.setAppointment(appointment);
				addAppointmentMessage.setCourseId(course.getCourseId());

				Ntro.messages().send(addAppointmentMessage);
			}
		});
	}

}