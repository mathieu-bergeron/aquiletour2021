package ca.aquiletour.web.pages.dashboard.teacher;

import ca.aquiletour.core.pages.dashboard.teacher.models.CurrentTaskTeacher;
import ca.aquiletour.core.pages.dashboard.teacher.views.DashboardCourseViewTeacher;
import ca.aquiletour.web.pages.dashboard.DashboardItemViewWeb;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;

public class DashboardItemViewWebTeacher extends DashboardItemViewWeb<CurrentTaskTeacher> implements DashboardCourseViewTeacher {
	
	private String taskLiHtml = "<li class=\"list-group-item\">\n"
			+ "<div class=\"d-flex\">\n"
			+ "<a href=\"#\">Atelier 2</a>\n"
			+ "<div class=\"flex-fill\"></div>\n"
			+ "<div>\n"
			+ "                        (12 étudiant.es)\n"
			+ "</div>\n"
			+ "</div>\n"
			+ "</li>\n";

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);
		super.initializeViewWeb(context);

		
	}

	@Override
	protected HtmlElement createTaskLi(int index, CurrentTaskTeacher currentTask) {
		T.call(this);
		
		HtmlElement taskLi = getRootElement().createElement(taskLiHtml);
		
		return taskLi;
	}


}
