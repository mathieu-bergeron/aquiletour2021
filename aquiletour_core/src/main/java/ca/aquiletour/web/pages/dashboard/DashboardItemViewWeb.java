package ca.aquiletour.web.pages.dashboard;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.pages.dashboard.models.CurrentTask;
import ca.aquiletour.core.pages.dashboard.views.DashboardItemView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public abstract class DashboardItemViewWeb<CT extends CurrentTask> extends NtroViewWeb implements DashboardItemView<CT> {

	private HtmlElement titleLink;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);

		titleLink = this.getRootElement().find("#course-title-link").get(0);
		
		MustNot.beNull(titleLink);
	}

	@Override
	public void identifyCourse(CoursePath coursePath) {
		T.call(this);
		
		titleLink.setAttribute("href", "/" + 
									    Constants.COURSE_URL_SEGMENT +
									    coursePath.toUrlPath());
	}

	@Override
	public void updateCourseTitle(String courseTitle) {
		T.call(this);

		titleLink.text(courseTitle);
	}

	@Override
	public void insertTask(int index, CT currentTask) {
		T.call(this);
		
	}

	@Override
	public void updateTaskTitle(int index, String value) {
		T.call(this);

	}
}
