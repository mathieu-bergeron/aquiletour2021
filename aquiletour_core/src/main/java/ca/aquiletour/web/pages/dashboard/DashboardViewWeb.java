package ca.aquiletour.web.pages.dashboard;

import ca.aquiletour.core.pages.dashboard.DashboardView;
import ca.aquiletour.core.pages.dashboard.values.Course;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class DashboardViewWeb extends NtroViewWeb implements DashboardView {

	@Override
	public void appendCourse(Course course) {
		T.call(this);

		HtmlElement container = this.getRootElement().children("#courses-container").get(0);
		
		MustNot.beNull(container);
		
		StringBuilder html = new StringBuilder();
		html.append("<div class='card border mb-3' style='max-width: 15rem;'>");
		html.append("<div class='card-header bg-transparent border'>");
		html.append(course.getTitle());
		html.append("<a id='availableLink' href='#'><span class='color' id='teacherAvailable' ></span></a>");
		html.append("</div>");
		html.append("<div class='card-body text'>");
		html.append("<h5 class='card-title'>");
		html.append(course.getSummary());
		html.append("</h5>");
		html.append("<div class='card-footer bg-transparent border'>");
		html.append(course.getDate());
		html.append("</div>");
		html.append("</div>");
		html.append("<button type='button' id='buttonAvailable' class='btn btn-primary'>Disponible</button>");
		html.append("</div>");

		container.appendHtml(html.toString());
	}

}
