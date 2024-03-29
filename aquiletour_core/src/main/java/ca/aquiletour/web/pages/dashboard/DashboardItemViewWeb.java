package ca.aquiletour.web.pages.dashboard;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.paths.CoursePath;
import ca.aquiletour.core.pages.dashboard.models.CurrentTask;
import ca.aquiletour.core.pages.dashboard.views.DashboardItemView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public abstract class DashboardItemViewWeb<CT extends CurrentTask> extends NtroViewWeb implements DashboardItemView<CT> {

	private HtmlElement titleLink;
	private HtmlElement taskListUl;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);

		titleLink = this.getRootElement().find(".course-title-link").get(0);
		taskListUl = this.getRootElement().find(".task-list-ul").get(0);
		
		MustNot.beNull(titleLink);
		MustNot.beNull(taskListUl);
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
	public void insertTask(int index, CoursePath coursePath, CT currentTask) {
		T.call(this);
		
		HtmlElement taskLi = createTaskLi(index, coursePath, currentTask);

		taskListUl.appendElement(taskLi);
	}
	
	protected abstract HtmlElement createTaskLi(int index, CoursePath coursePath, CT currentTask);

	@Override
	public void updateTaskTitle(int index, String value) {
		T.call(this);

	}
}
