package ca.aquiletour.web.pages.course_list;


import ca.aquiletour.core.AquiletourMain;
import static ca.ntro.assertions.Factory.that;
import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.course_list.models.CourseItem;
import ca.aquiletour.core.pages.course_list.models.TaskDescription;
import ca.aquiletour.core.pages.course_list.views.CourseItemView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;
import ca.ntro.web.mvc.NtroViewWeb;

public class CourseItemViewWeb extends NtroViewWeb implements CourseItemView {
	
	private HtmlElement courseTitleLink;
	private HtmlElement courseTitleSemesterId;
	private HtmlElement addGroupModalTitle;
	private HtmlElement groupList;
	private HtmlElement taskList;
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
		taskList = this.getRootElement().find("#task-list").get(0);
		groupContainer = this.getRootElement().find("#group-container").get(0);
		addSemesterIdToValue = this.getRootElement().find(".add-semester-id-to-value");
		addCourseIdToValue = this.getRootElement().find(".add-course-id-to-value");

		MustNot.beNull(courseTitleLink);
		MustNot.beNull(courseTitleSemesterId);
		MustNot.beNull(addGroupModalTitle);
		MustNot.beNull(groupList);
		MustNot.beNull(taskList);
		MustNot.beNull(groupContainer);

		Ntro.verify(that(addSemesterIdToValue.size() > 0).isTrue());
		Ntro.verify(that(addCourseIdToValue.size() > 0).isTrue());
	}

	@Override
	public void displayCourseDescription(CourseItem courseDescription) {
		T.call(this);
		
		String href = "/" + Constants.COURSE_URL_SEGMENT + 
		              "/" + Ntro.currentUser().getId() + 
		              "/" + courseDescription.getCourseId();
		
		if(!AquiletourMain.currentSemester().equals(courseDescription.getSemesterId())) {
			href += "?" + Constants.SEMESTER_URL_PARAM + "=" + courseDescription.getSemesterId();
		}
		
		if(courseDescription.getSemesterId().equals(Constants.DRAFTS_SEMESTER_ID)) {
			groupContainer.hide();
		}
		
		courseTitleLink.text(courseDescription.getCourseId());
		courseTitleLink.setAttribute("href", href);
		
		courseTitleSemesterId.text(courseDescription.getSemesterId());
		
		addGroupModalTitle.text("Ajouter un groupe au cours " + courseDescription.getCourseId());

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

	@Override
	public void appendTaskDescription(TaskDescription task) {
		T.call(this);

		HtmlElement taskLi = taskList.createElement("<li class=\"list-group-item\"></li>");
		taskList.appendElement(taskLi);

		HtmlElement taskLink = taskLi.createElement("<a href='#TODO'></a>");
		taskLi.appendElement(taskLink);

		taskLink.text(task.getTaskTitle());
	}

	@Override
	public void appendGroupId(String groupId) {
		T.call(this);

		HtmlElement groupLi = groupList.createElement("<li class=\"list-group-item\"></li>");
		groupList.appendElement(groupLi);

		HtmlElement groupAnchor = groupLi.createElement("<a href='#'></a>");
		groupLi.appendElement(groupAnchor);

		groupAnchor.text(groupId);
	}
}
