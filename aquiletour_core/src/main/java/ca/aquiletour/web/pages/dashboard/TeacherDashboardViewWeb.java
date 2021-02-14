package ca.aquiletour.web.pages.dashboard;

import ca.aquiletour.core.pages.dashboards.teacher.TeacherDashboardView;
import ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseMessage;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.MessageFactory;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlEventListener;

public class TeacherDashboardViewWeb extends DashboardViewWeb implements TeacherDashboardView {


	@Override
	public void initialize() {
		super.initialize();
		T.call(this);

		HtmlElement addCourseButton = getRootElement().find("#add-course-submit-button").get(0);
		HtmlElement addCourseTitleInput = getRootElement().find("#add-course-title-input").get(0);
		HtmlElement addCourseSummaryInput = getRootElement().find("#add-course-summary-input").get(0);
		HtmlElement addCourseDateInput = getRootElement().find("#add-course-date-input").get(0);

		MustNot.beNull(addCourseButton);
		MustNot.beNull(addCourseTitleInput);
		MustNot.beNull(addCourseSummaryInput);
		MustNot.beNull(addCourseDateInput);

		addCourseButton.addEventListener("click", new HtmlEventListener() {
			@Override
			public void onEvent() {
				T.call(this);

				AddCourseMessage addCourseMessage = MessageFactory.getOutgoingMessage(AddCourseMessage.class);
				addCourseMessage.setCourse(new CourseSummary(addCourseTitleInput.getValue(),addCourseSummaryInput.getValue(),addCourseDateInput.getValue()));
				addCourseMessage.sendMessage();
			}
		});

	}
}
