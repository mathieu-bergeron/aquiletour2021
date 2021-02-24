package ca.aquiletour.web.pages.dashboard;

import ca.aquiletour.core.pages.dashboards.student.StudentCourseSummaryView;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class StudentCourseSummaryViewWeb extends CourseSummaryViewWeb implements StudentCourseSummaryView {

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
		HtmlElement makeAppointmentLink = this.getRootElement().children("#availableLink").get(0);
		HtmlElement teacherAvailable = this.getRootElement().children("#buttonAvailable").get(0);
		
		
		MustNot.beNull(title);
		MustNot.beNull(courseId);
		MustNot.beNull(makeAppointmentLink);
		MustNot.beNull(teacherAvailable);
		

		T.values(course.getTitle()); 
		
		title.appendHtml(course.getTitle());
		//courseId.appendHtml(course.getCourseId());
		if(course.getMyAppointment() != null && course.getIsQueueOpen() != null) {
			if(course.getMyAppointment()) {
				teacherAvailable.appendHtml("j'ai déjà un rendez-vous");
			} else if (course.getIsQueueOpen()) {
				teacherAvailable.appendHtml("prof disponible");
			} else {
				teacherAvailable.appendHtml("prof non-disponible");
			}
		}
		makeAppointmentLink.setAttribute("href","billetterie/" + course.getTitle() + "?makeAppointment");
	}

}
