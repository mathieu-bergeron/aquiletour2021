package ca.aquiletour.web.pages.dashboard.teacher;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.teacher.TeacherCourseSummaryView;
import ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseMessage;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.aquiletour.core.pages.queue.teacher.messages.ShowTeacherQueueMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherClosesQueueMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherUsesQueueMessage;
import ca.aquiletour.web.pages.dashboard.CourseSummaryViewWeb;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlEventListener;
import ca.ntro.web.mvc.NtroViewWeb;

public class TeacherCourseSummaryViewWeb extends CourseSummaryViewWeb implements TeacherCourseSummaryView {

	@Override
	public void initializeViewWeb(NtroContext<?> context) {
	}

	@Override
	public void displayStatus(boolean myAppointment, boolean isQueueOpen) {
		HtmlElement closeQueue = this.getRootElement().find("#closeQueue").get(0);
		MustNot.beNull(closeQueue);
		
		if (isQueueOpen) {
			closeQueue.setAttribute("href", "/billetterie/" + "3C6" + "?teacherClosesQueue");
			closeQueue.html("CLOSE QUEUE");
		} else {
			closeQueue.setAttribute("href", "/billetterie/" + "3C6");
			closeQueue.html("OPEN QUEUE");

		}
		closeQueue.addEventListener("click", new HtmlEventListener() {
			@Override
			public void onEvent() {
				T.call(this);
				T.here();

				if (isQueueOpen) {
					T.here();
					TeacherClosesQueueMessage teacherClosesQueueMessage = Ntro.messages().create(TeacherClosesQueueMessage.class);
					teacherClosesQueueMessage.setCourseId("3C6");
					Ntro.messages().send(teacherClosesQueueMessage);

				} else {
					T.here();
					TeacherUsesQueueMessage teacherUsesQueueMessage = Ntro.messages().create(TeacherUsesQueueMessage.class);
					teacherUsesQueueMessage.setCourseId("3C6");
					Ntro.messages().send(teacherUsesQueueMessage);
				}
			}
		});
	}

	@Override
	public void displaySummary(CourseSummary course) {
		T.call(this);
		T.here();

		HtmlElement title = this.getRootElement().find("#course-title").get(0);
		HtmlElement courseId = this.getRootElement().find("#courseId").get(0);
		HtmlElement nbAppointment = this.getRootElement().find("#nbAppointment").get(0);
		HtmlElement makeAppointmentLink = this.getRootElement().find("#availableLink").get(0);
		HtmlElement deleteCourseLink = this.getRootElement().find("#deleteLink").get(0);
		HtmlElement closeQueue = this.getRootElement().find("#closeQueue").get(0);

		MustNot.beNull(title);
		MustNot.beNull(courseId);
		MustNot.beNull(nbAppointment);
		MustNot.beNull(makeAppointmentLink);
		MustNot.beNull(closeQueue);

		title.appendHtml(course.getTitle());
		// courseId.appendHtml(course.getCourseId());

		nbAppointment.appendHtml(Integer.toString(course.getNumberOfAppointments()));
		makeAppointmentLink.setAttribute("href", "/billetterie/" + course.getTitle());
		deleteCourseLink.setAttribute("href", "/mescours/" + course.getTitle() + "?deleteCourse");

		makeAppointmentLink.addEventListener("click", new HtmlEventListener() {
			@Override
			public void onEvent() {
				T.here();
				ShowTeacherQueueMessage showTeacherQueueMessage = Ntro.messages().create(ShowTeacherQueueMessage.class);
				showTeacherQueueMessage.setCourseId(course.getCourseId());

				Ntro.messages().send(showTeacherQueueMessage);

				TeacherUsesQueueMessage teacherUsesQueueMessage = Ntro.messages().create(TeacherUsesQueueMessage.class);
				// FIXME
				teacherUsesQueueMessage.setCourseId("3C6");
				Ntro.messages().send(teacherUsesQueueMessage);
			}
		});

		

		
	}
}
