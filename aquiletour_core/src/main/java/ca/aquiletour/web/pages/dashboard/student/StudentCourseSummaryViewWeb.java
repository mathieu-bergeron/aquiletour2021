package ca.aquiletour.web.pages.dashboard.student;

import ca.aquiletour.core.pages.dashboards.student.StudentCourseSummaryView;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.aquiletour.core.pages.queue.student.messages.AddAppointmentMessage;
import ca.aquiletour.core.pages.queue.student.messages.ShowStudentQueueMessage;
import ca.aquiletour.core.pages.queues.messages.ShowQueuesMessage;
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
	HtmlElement makeAppointmentLink;
	
	String makeAppointmentHref;

	@Override
	public void initializeViewWeb(NtroContext<?> context) {
		T.call(this);

		title = this.getRootElement().find("#course-title").get(0);
		queueStatus = this.getRootElement().find("#queue-status").get(0);
		makeAppointmentLink = this.getRootElement().find("#make-appointment-link").get(0);

		MustNot.beNull(queueStatus);
		MustNot.beNull(title);
		MustNot.beNull(makeAppointmentLink);
		
		makeAppointmentHref = makeAppointmentLink.getAttribute("href");
		
		addListeners(context);
    }

	private void addListeners(NtroContext<?> context) {
		T.call(this);
	}

	@Override
	public void displayStatus(String queueId, boolean doesStudentHaveAppointment, boolean isTeacherAvailable) {
		T.call(this);
		
		if(doesStudentHaveAppointment) {
			HtmlElement openQueueLink = makeAppointmentLink.createElement("<a href='/billetterie/"+queueId+"'>J'ai déjà un rendez-vous</>");

			queueStatus.clearChildren();
			queueStatus.appendElement(openQueueLink);
			
			openQueueLink.addEventListener("click", new HtmlEventListener() {
				@Override
				public void onEvent() {
					ShowStudentQueueMessage showStudentQueueMessage = Ntro.messages().create(ShowStudentQueueMessage.class);
					showStudentQueueMessage.setCourseId(queueId);
					Ntro.messages().send(showStudentQueueMessage);
				}
			});

			makeAppointmentLink.hide();
		}else if(isTeacherAvailable) {
			queueStatus.html("Prof disponible");
			makeAppointmentLink.show();
		}else {
			queueStatus.html("Prof non-disponible");
			makeAppointmentLink.hide();
		}
	}

	@Override
	public void displaySummary(CourseSummary course) {
		T.call(this);

		title.appendHtml(course.getTitle());

		makeAppointmentLink.setAttribute("href", makeAppointmentHref + course.getTitle() + "?makeAppointment");
		
		makeAppointmentLink.addEventListener("click", new HtmlEventListener() {
			@Override
			public void onEvent() {
				AddAppointmentMessage  addAppointmentMessage = Ntro.messages().create(AddAppointmentMessage.class);
				addAppointmentMessage.setCourseId(course.getCourseId());
				Ntro.messages().send(addAppointmentMessage);
			}
		});
	}
}
