package ca.aquiletour.web.pages.dashboard;

import ca.aquiletour.core.pages.dashboards.teacher.TeacherCourseSummaryView;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class TeacherCourseSummaryViewWeb extends CourseSummaryViewWeb implements TeacherCourseSummaryView {

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displaySummary(CourseSummary course) {
		T.call(this);
		T.here();

		HtmlElement title = this.getRootElement().children("#course-title").get(0);
		HtmlElement courseId = this.getRootElement().children("#courseId").get(0);
		HtmlElement nbAppointment = this.getRootElement().children("#nbAppointment").get(0);
		HtmlElement makeAppointmentLink = this.getRootElement().children("#availableLink").get(0);
		HtmlElement myAppointment = this.getRootElement().children("#myAppointment").get(0);
		HtmlElement isQueueOpen = this.getRootElement().children("#isQueueOpen").get(0);
		
		
		MustNot.beNull(title);
		MustNot.beNull(courseId);
		MustNot.beNull(nbAppointment);
		MustNot.beNull(makeAppointmentLink);
		MustNot.beNull(myAppointment);
		MustNot.beNull(isQueueOpen);

		T.values(course.getTitle()); 
		
		title.appendHtml("TEACHER DASHBOARD");
		//courseId.appendHtml(course.getCourseId());
		T.values(course.getIsQueueOpen());
		nbAppointment.appendHtml(Integer.toString(course.getNumberOfAppointments()));
		if (course.getMyAppointment() != null) {
			myAppointment.appendHtml(course.getMyAppointment());
		}
		if(course.getIsQueueOpen()) {
			isQueueOpen.appendHtml("true");
		} else {
			isQueueOpen.appendHtml("false");
		}
		makeAppointmentLink.setAttribute("href","billetterie/" + course.getTitle() + "?makeAppointment");
	}


}
