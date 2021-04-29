package ca.aquiletour.web.pages.course_list;


import ca.aquiletour.core.AquiletourMain;
import static ca.ntro.assertions.Factory.that;
import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.course_list.models.CourseListItem;
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
	private HtmlElement tasksSummary;
	private HtmlElement groupsSummary;
	private HtmlElement queueLink;
	private HtmlElement queueCheckbox;

	private HtmlElements addSemesterIdToValue;
	private HtmlElements addCourseIdToValue;
	private HtmlElements addCourseIdToId;
	private HtmlElements addCourseIdToForm;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);

		courseTitleLink = this.getRootElement().find("#course-title-link").get(0);
		courseTitleSemesterId = this.getRootElement().find("#course-title-semester-id").get(0);
		addGroupModalTitle = this.getRootElement().find("#add-group-modal-title").get(0);
		groupList = this.getRootElement().find("#group-list").get(0);
		taskList = this.getRootElement().find("#task-list").get(0);
		groupContainer = this.getRootElement().find("#group-container").get(0);
		tasksSummary = this.getRootElement().find("#tasks-summary").get(0);
		groupsSummary = this.getRootElement().find("#groups-summary").get(0);
		queueLink = this.getRootElement().find("#queue-link").get(0);
		queueCheckbox = this.getRootElement().find(".aquiletour-checkbox").get(0);

		addSemesterIdToValue = this.getRootElement().find(".add-semester-id-to-value");
		addCourseIdToValue = this.getRootElement().find(".add-course-id-to-value");
		addCourseIdToId = this.getRootElement().find(".add-course-id-to-id");
		addCourseIdToForm = this.getRootElement().find(".add-course-id-to-form");

		MustNot.beNull(courseTitleLink);
		MustNot.beNull(courseTitleSemesterId);
		MustNot.beNull(addGroupModalTitle);
		MustNot.beNull(groupList);
		MustNot.beNull(taskList);
		MustNot.beNull(groupContainer);
		MustNot.beNull(tasksSummary);
		MustNot.beNull(groupsSummary);
		MustNot.beNull(queueLink);
		MustNot.beNull(queueCheckbox);

		Ntro.verify(that(addSemesterIdToValue.size() > 0).isTrue());
		Ntro.verify(that(addCourseIdToValue.size() > 0).isTrue());
		Ntro.verify(that(addCourseIdToId.size() > 0).isTrue());
		Ntro.verify(that(addCourseIdToForm.size() > 0).isTrue());
	}

	@Override
	public void displayCourseDescription(CourseListItem courseItem) {
		T.call(this);
		
		String href = "/" + Constants.COURSE_URL_SEGMENT + 
		              "/" + Ntro.currentUser().getId() + 
		              "/" + courseItem.getCourseId();
		
		if(!AquiletourMain.currentSemester().equals(courseItem.getSemesterId())) {
			href += "?" + Constants.SEMESTER_URL_PARAM + "=" + courseItem.getSemesterId();
		}
		
		if(courseItem.getSemesterId().equals(Constants.DRAFTS_SEMESTER_ID)) {
			groupContainer.hide();
		}
		
		courseTitleLink.text(courseItem.getCourseId());
		courseTitleLink.setAttribute("href", href);
		
		courseTitleSemesterId.text(courseItem.getSemesterId());
		
		addGroupModalTitle.text("Ajouter un groupe au cours " + courseItem.getCourseId());
		
		addSemesterIdToValue.appendToAttribute("value", courseItem.getSemesterId());
		addCourseIdToValue.appendToAttribute("value", courseItem.getCourseId());
		addCourseIdToId.appendToAttribute("id", courseItem.getCourseId());
		addCourseIdToForm.appendToAttribute("form", courseItem.getCourseId());
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

	@Override
	public void displayTasksSummary(String tasksSummaryText) {
		T.call(this);
		
		tasksSummary.text(tasksSummaryText);
	}

	@Override
	public void displayGroupsSummary(String groupsSummaryText) {
		T.call(this);
		
		groupsSummary.text(groupsSummaryText);
	}

	@Override
	public void displayQueueLink(boolean queueOpen, String text, String href) {
		T.call(this);
		
		if(queueOpen) {
			
			queueCheckbox.setAttribute("checked","true");
			
		}else {
			
			queueCheckbox.removeAttribute("checked");
		}
		
		queueLink.text(text);
		queueLink.setAttribute("href", href);
	}
}
