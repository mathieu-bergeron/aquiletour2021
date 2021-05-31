package ca.aquiletour.web.pages.dashboard.student;

import ca.aquiletour.core.pages.dashboard.student.models.CurrentTaskStudent;
import ca.aquiletour.core.pages.dashboard.student.views.DashboardCourseViewStudent;
import ca.aquiletour.web.pages.dashboard.DashboardItemViewWeb;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;

public class DashboardItemViewWebStudent extends DashboardItemViewWeb<CurrentTaskStudent> implements DashboardCourseViewStudent {

	private String taskLiHtml = "                <div class=\"d-flex justify-content-start\">\n"
			+ "                    <a href=\"#\">Atelier 2</a>\n"
			+ "                    <div class=\"flex-fill\"></div>\n"
			+ "                    <form action=\"\" method=\"post\" id=\"make-appointment-form\">\n"
			+ "                        <input name=\"makeAppointment\" style=\"display:none;\"></input>\n"
			+ "                        <input type=\"submit\" class=\"btn btn-primary btn-sm\" value=\"Prendre rendez-vous\"></input>\n"
			+ "                    </form>\n"
			+ "                </div>\n"
			+ "";

	@Override
	protected HtmlElement createTaskLi(int index, CurrentTaskStudent currentTask) {
		T.call(this);
		
		// FIXME: must get at least teacherId for link to Queue
		
		HtmlElement taskLi = getRootElement().createElement("taskLiHtml");

		return taskLi;
	}


}
