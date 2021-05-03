package ca.aquiletour.web.pages.course_list.student;

import ca.aquiletour.core.pages.course_list.models.CourseListItem;
import ca.aquiletour.core.pages.course_list.models.TaskDescription;
import ca.aquiletour.core.pages.course_list.teacher.views.CourseListItemViewTeacher;
import static ca.ntro.assertions.Factory.that;
import ca.aquiletour.web.pages.course_list.CourseListItemViewWeb;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;

public class CourseListItemViewWebTeacher extends CourseListItemViewWeb implements CourseListItemViewTeacher {

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

		super.initializeViewWeb(context);
		
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
	public void displayCourseListItem(CourseListItem courseListItem) {
		T.call(this);
		
		super.displayCourseListItem(courseListItem);
		
		addGroupModalTitle.text("Ajouter un groupe au cours " + courseListItem.getCourseId());
		
		addSemesterIdToValue.appendToAttribute("value", courseListItem.getSemesterId());
		addCourseIdToValue.appendToAttribute("value", courseListItem.getCourseId());
		addCourseIdToId.appendToAttribute("id", courseListItem.getCourseId());
		addCourseIdToForm.appendToAttribute("form", courseListItem.getCourseId());
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
