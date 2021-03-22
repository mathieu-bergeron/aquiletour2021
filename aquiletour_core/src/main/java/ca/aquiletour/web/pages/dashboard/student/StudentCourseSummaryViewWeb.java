package ca.aquiletour.web.pages.dashboard.student;

import ca.aquiletour.core.pages.dashboards.student.StudentCourseSummaryView;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.aquiletour.core.pages.queue.student.messages.AddAppointmentMessage;
import ca.aquiletour.core.pages.queue.student.messages.ShowStudentQueueMessage;
import ca.aquiletour.web.pages.dashboard.CourseSummaryViewWeb;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlEventListener;

public class StudentCourseSummaryViewWeb extends CourseSummaryViewWeb implements StudentCourseSummaryView {

	HtmlElement title;
	HtmlElement queueStatus;
	HtmlElement makeAppointmentButton;
	
	String makeAppointmentHref;

	@Override
	public void initializeViewWeb(NtroContext<?> context) {
		T.call(this);

		title = this.getRootElement().find("#course-title").get(0);
		queueStatus = this.getRootElement().find("#queue-status").get(0);
		makeAppointmentButton = this.getRootElement().find("#make-appointment-link").get(0);

		MustNot.beNull(queueStatus);
		MustNot.beNull(title);
		MustNot.beNull(makeAppointmentButton);
		
		makeAppointmentHref = makeAppointmentButton.getAttribute("href");
		
		addListeners(context);
    }

	private void addListeners(NtroContext<?> context) {
		T.call(this);
	}

	@Override
	public void displayStatus(String queueId, boolean doesStudentHaveAppointment, boolean isTeacherAvailable) {
		T.call(this);
		
		if(doesStudentHaveAppointment) {

			queueStatus.html("");

			HtmlElement openQueueLink = queueStatus.createElement("<a href='/billetterie/"+queueId+"'>J'ai déjà un rendez-vous</>");
			queueStatus.appendElement(openQueueLink);
			
			openQueueLink.addEventListener("click", new HtmlEventListener() {
				@Override
				public void onEvent() {
					ShowStudentQueueMessage showStudentQueueMessage = Ntro.messages().create(ShowStudentQueueMessage.class);
					showStudentQueueMessage.setCourseId(queueId);
					Ntro.messages().send(showStudentQueueMessage);
				}
			});

			makeAppointmentButton.hide();
		}else if(isTeacherAvailable) {
			queueStatus.html("Prof disponible");
			makeAppointmentButton.show();
		}else {
			queueStatus.html("Prof non-disponible");
			makeAppointmentButton.hide();
		}
	}

	@Override
	public void displaySummary(CourseSummary course) {
		T.call(this);

		title.appendHtml(course.getTitle());

		makeAppointmentButton.setAttribute("href", makeAppointmentHref + course.getTitle() + "?makeAppointment");
		
		makeAppointmentButton.addEventListener("click", new HtmlEventListener() {
			@Override
			public void onEvent() {
				AddAppointmentMessage  addAppointmentMessage = Ntro.messages().create(AddAppointmentMessage.class);
				addAppointmentMessage.setCourseId(course.getCourseId());
				Ntro.messages().send(addAppointmentMessage);
				
				ShowStudentQueueMessage showStudentQueueMessage = Ntro.messages().create(ShowStudentQueueMessage.class);
				showStudentQueueMessage.setCourseId(course.getCourseId());
				Ntro.messages().send(showStudentQueueMessage);
			}
		});
	}

	@Override
	public void displayNumberOfAppointments(int numberOfAppointments) {
		// XXX: not supported
	}

	@Override
	public void displayNumberOfStudents(int numberOfStudents) {
		// XXX: not supported
	}

}
