package ca.aquiletour.web.pages.course_list;

import ca.aquiletour.core.pages.course_list.models.CourseDescription;
import ca.aquiletour.core.pages.course_list.views.CourseDescriptionView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class CourseDescriptionViewWeb extends NtroViewWeb implements CourseDescriptionView {
	
	private HtmlElement title;

	@Override
	public void initializeViewWeb(NtroContext<?> context) {
		T.call(this);

		title = this.getRootElement().find("#course-title-link").get(0);

		MustNot.beNull(title);
		
	}

	@Override
	public void displayCourseDescription(CourseDescription item) {
		T.call(this);
		
		title.text(item.getCourseTitle());
	}

}
