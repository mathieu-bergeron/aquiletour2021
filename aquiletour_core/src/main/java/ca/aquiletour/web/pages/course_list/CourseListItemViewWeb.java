package ca.aquiletour.web.pages.course_list;


import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.course_list.models.CourseListItem;
import ca.aquiletour.core.pages.course_list.views.CourseListItemView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroView;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class CourseListItemViewWeb extends NtroViewWeb implements CourseListItemView {
	
	private HtmlElement courseTitleLink;
	private HtmlElement courseTitleSemesterId;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);

		courseTitleLink = this.getRootElement().find("#course-title-link").get(0);
		courseTitleSemesterId = this.getRootElement().find("#course-title-semester-id").get(0);

		MustNot.beNull(courseTitleLink);
		MustNot.beNull(courseTitleSemesterId);
	}

	@Override
	public void displayCourseListItem(CourseListItem courseListItem) {
		T.call(this);
		
		String href = "/" + Constants.COURSE_URL_SEGMENT + courseListItem.coursePath().toUrlPath();
		
		courseTitleLink.text(courseListItem.getCourseId());
		courseTitleLink.setAttribute("href", href);
		
		courseTitleSemesterId.text(courseListItem.getSemesterId());
	}
}
