package ca.aquiletour.web.pages.dashboard.teacher;

import ca.aquiletour.core.pages.dashboards.teacher.TeacherDashboardView;
import ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseMessage;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.aquiletour.web.pages.dashboard.DashboardViewWeb;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlEventListener;

public class TeacherDashboardViewWeb extends DashboardViewWeb implements TeacherDashboardView {

	@Override
	public void initialize(NtroContext<?> context) {
		super.initialize(context);
		T.call(this);

		HtmlElement addCourseButton = getRootElement().find("#add-course-submit-button").get(0);
		HtmlElement addCourseTitleInput = getRootElement().find("#add-course-title-input").get(0);

		MustNot.beNull(addCourseButton);
		MustNot.beNull(addCourseTitleInput);

		addCourseButton.addEventListener("click", new HtmlEventListener() {
			@Override
			public void onEvent() {
				T.call(this);

				AddCourseMessage addCourseMessage = Ntro.messages().create(AddCourseMessage.class);
				addCourseMessage.setCourse(new CourseSummary(addCourseTitleInput.getValue(), addCourseTitleInput.getValue(),false,false,0));
				Ntro.messages().sendMessage(addCourseMessage);
			}
		});

	}

}
