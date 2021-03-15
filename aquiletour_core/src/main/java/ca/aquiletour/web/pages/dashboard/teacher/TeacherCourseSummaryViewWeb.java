package ca.aquiletour.web.pages.dashboard.teacher;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.teacher.TeacherCourseSummaryView;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.aquiletour.core.pages.queue.teacher.messages.ShowTeacherQueueMessage;
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
	public void initialize(NtroContext<?> context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayStatus(boolean myAppointment, boolean teacherAvailable) {
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
		
		
		MustNot.beNull(title);
		MustNot.beNull(courseId);
		MustNot.beNull(nbAppointment);
		MustNot.beNull(makeAppointmentLink);


		
		title.appendHtml(course.getTitle());
		//courseId.appendHtml(course.getCourseId());

		nbAppointment.appendHtml(Integer.toString(course.getNumberOfAppointments()));
		makeAppointmentLink.setAttribute("href","/billetterie/" + course.getTitle() + "?makeAppointment");
		deleteCourseLink.setAttribute("href", "/mescours/" + course.getTitle() + "?deleteCourse");

		// FIXME: the browser will remove the event listener
		//        when the element is removed from DOM
		makeAppointmentLink.addEventListener("click", new HtmlEventListener() {
			@Override
			public void onEvent() {
				
				ShowTeacherQueueMessage showTeacherQueueMessage = Ntro.messages().create(ShowTeacherQueueMessage.class);
				showTeacherQueueMessage.setCourseId(course.getCourseId());

				Ntro.messages().send(showTeacherQueueMessage);
				
				TeacherUsesQueueMessage teacherUsesQueueMessage = Ntro.messages().create(TeacherUsesQueueMessage.class);
				// FIXME
				teacherUsesQueueMessage.setCourseId("3C6");
				teacherUsesQueueMessage.setTeacher((User)Ntro.userService().currentUser());
				Ntro.messages().send(teacherUsesQueueMessage);
			}
		});
	}




}
