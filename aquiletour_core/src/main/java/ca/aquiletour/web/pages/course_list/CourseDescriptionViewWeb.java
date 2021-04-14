package ca.aquiletour.web.pages.course_list;

import ca.aquiletour.core.AquiletourMain;
import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.course_list.models.CourseDescription;
import ca.aquiletour.core.pages.course_list.views.CourseDescriptionView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class CourseDescriptionViewWeb extends NtroViewWeb implements CourseDescriptionView {
	
	private HtmlElement title;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);

		title = this.getRootElement().find("#course-title-link").get(0);

		MustNot.beNull(title);
		
	}

	@Override
	public void displayCourseDescription(CourseDescription courseDescription) {
		T.call(this);
		
		String href = "/" + Constants.COURSE_URL_SEGMENT + 
		              "/" + Ntro.currentUser().getId() + 
		              "/" + courseDescription.getCourseId();
		
		if(!AquiletourMain.currentSemester().equals(courseDescription.getSemesterId())) {
			href += "?" + Constants.SEMESTER_URL_PARAM + "=" + courseDescription.getSemesterId();
		}
		
		title.text(courseDescription.getCourseTitle());
		title.setAttribute("href", href);
	}

}
