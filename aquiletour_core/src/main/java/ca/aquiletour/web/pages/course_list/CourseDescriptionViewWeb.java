package ca.aquiletour.web.pages.course_list;


import ca.aquiletour.core.AquiletourMain;
import static ca.ntro.assertions.Factory.that;
import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.course_list.models.CourseDescription;
import ca.aquiletour.core.pages.course_list.views.CourseDescriptionView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;
import ca.ntro.web.mvc.NtroViewWeb;

public class CourseDescriptionViewWeb extends NtroViewWeb implements CourseDescriptionView {
	
	private HtmlElement courseTitleLink;
	private HtmlElement courseTitleSemesterId;
	private HtmlElement addGroupModalTitle;
	private HtmlElement groupList;
	private HtmlElement groupContainer;
	private HtmlElements addSemesterIdToValue;
	private HtmlElements addCourseIdToValue;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);

		courseTitleLink = this.getRootElement().find("#course-title-link").get(0);
		courseTitleSemesterId = this.getRootElement().find("#course-title-semester-id").get(0);
		addGroupModalTitle = this.getRootElement().find("#add-group-modal-title").get(0);
		groupList = this.getRootElement().find("#group-list").get(0);
		groupContainer = this.getRootElement().find("#group-container").get(0);
		addSemesterIdToValue = this.getRootElement().find(".add-semester-id-to-value");
		addCourseIdToValue = this.getRootElement().find(".add-course-id-to-value");

		MustNot.beNull(courseTitleLink);
		MustNot.beNull(courseTitleSemesterId);
		MustNot.beNull(addGroupModalTitle);
		MustNot.beNull(groupList);
		MustNot.beNull(groupContainer);

		Ntro.verify(that(addSemesterIdToValue.size() > 0).isTrue());
		Ntro.verify(that(addCourseIdToValue.size() > 0).isTrue());
	}

	@Override
	public void displayCourseDescription(CourseDescription courseDescription) {
		T.call(this);
		
		String href = "/" + Constants.COURSE_URL_SEGMENT + 
		              "/" + Ntro.currentUser().getId() + 
		              "/" + courseDescription.getCourseId();
		
		if(!AquiletourMain.currentSemester().equals(courseDescription.getSemesterId())) {
			href += "?" + Constants.SEMESTER_URL_PARAM + "=" + courseDescription.getSemesterId();
		}
		
		if(courseDescription.getSemesterId().equals(Constants.COURSE_DRAFTS)) {
			groupContainer.hide();
		}
		
		courseTitleLink.text(courseDescription.getCourseId());
		courseTitleLink.setAttribute("href", href);
		
		courseTitleSemesterId.text(courseDescription.getSemesterId());
		
		addGroupModalTitle.text("Ajouter un groupe au cours " + courseDescription.getCourseId());
		
		for(String groupId : courseDescription.getGroupIds().getValue()) {
			HtmlElement groupLi = groupList.createElement("<li class=\"list-group-item\"></li>");
			groupList.appendElement(groupLi);

			HtmlElement groupAnchor = groupLi.createElement("<a href='#'></a>");
			groupLi.appendElement(groupAnchor);

			groupAnchor.text(groupId);
		}
		
		addSemesterIdToValue.forEach(e -> {
			String value = e.getAttribute("value");
			value += courseDescription.getSemesterId();
			e.setAttribute("value", value);
		});

		addCourseIdToValue.forEach(e -> {
			String value = e.getAttribute("value");
			value += courseDescription.getCourseId();
			e.setAttribute("value", value);
		});
	}

}
