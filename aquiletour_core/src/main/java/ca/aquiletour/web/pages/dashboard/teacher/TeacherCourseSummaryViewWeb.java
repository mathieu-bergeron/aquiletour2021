package ca.aquiletour.web.pages.dashboard.teacher;

import ca.aquiletour.core.pages.dashboards.teacher.TeacherCourseSummaryView;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.aquiletour.web.pages.dashboard.CourseSummaryViewWeb;
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
		//courseId.appendHtml(course.getCourseId());
		nbAppointment.appendHtml(Integer.toString(course.getNumberOfAppointments()));
		makeAppointmentLink.setAttribute("href","/billetterie/" + course.getTitle() + "?makeAppointment");
		deleteCourseLink.setAttribute("href", "/mescours/" + course.getTitle() + "?deleteCourse");
		if(course.getIsQueueOpen()) {
			closeQueue.setAttribute("href", "/billetterie/" + course.getTitle() + "?teacherClosesQueue");
			closeQueue.appendHtml("CLOSE QUEUE");
		} else {
			closeQueue.setAttribute("href", "/billetterie/" + course.getTitle());
			closeQueue.appendHtml("OPEN QUEUE");

		}
	}


}
