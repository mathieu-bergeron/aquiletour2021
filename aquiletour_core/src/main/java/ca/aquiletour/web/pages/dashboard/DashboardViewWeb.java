package ca.aquiletour.web.pages.dashboard;

import ca.aquiletour.core.pages.dashboard.CourseSummaryView;
import ca.aquiletour.core.pages.dashboard.DashboardView;
import ca.aquiletour.core.pages.dashboard.messages.AddCourseMessage;
import ca.aquiletour.core.pages.dashboard.values.CourseSummary;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.MessageFactory;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlEventListener;
import ca.ntro.web.mvc.NtroViewWeb;

public class DashboardViewWeb extends NtroViewWeb implements DashboardView {
	
	@Override
	public void initialize() {

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

	@Override
	public void appendCourse(CourseSummaryView courseView) {
		T.call(this);

		HtmlElement container = this.getRootElement().children("#courses-container").get(0);
		
		MustNot.beNull(container);
		
		CourseSummaryViewWeb courseViewWeb = (CourseSummaryViewWeb) courseView;
		
		container.appendElement(courseViewWeb.getRootElement());
	}
}
