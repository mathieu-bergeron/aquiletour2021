package ca.aquiletour.web.pages.queue.teacher;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.session.SessionData;
import ca.aquiletour.core.pages.queue.teacher.views.QueueViewTeacher;
import ca.aquiletour.web.pages.queue.QueueViewWeb;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;

public class QueueViewWebTeacher extends QueueViewWeb implements QueueViewTeacher {

	private HtmlElement queueId;
	private HtmlElement queueMenuButton;
	private HtmlElement queueMenuCoursesContainer;
	private HtmlElement queueMenuCourseTemplate;
	private HtmlElement queueMenuGroupTemplate;

	private HtmlElements addSemesterIdToValue;

	private HtmlElements addAllCoursesIdToId;
	private HtmlElements addAllCoursesIdToValue;
	private HtmlElements addAllCoursesIdToForm;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		super.initializeViewWeb(context);
		T.call(this);

		queueId = this.getRootElement().find("#queue-id").get(0);
		queueMenuButton = this.getRootElement().find("#queue-menu-button").get(0);
		queueMenuCoursesContainer = this.getRootElement().find("#queue-menu-courses-container").get(0);

		queueMenuCourseTemplate = this.getRootElement().find(".queue-menu-course-template").get(0);
		queueMenuGroupTemplate = this.getRootElement().find(".queue-menu-group-template").get(0);

		MustNot.beNull(queueId);
		MustNot.beNull(queueMenuButton);
		MustNot.beNull(queueMenuCoursesContainer);
		MustNot.beNull(queueMenuCourseTemplate);
		MustNot.beNull(queueMenuGroupTemplate);

		queueMenuCourseTemplate.hide();
		queueMenuGroupTemplate.hide(); 

		addSemesterIdToValue = this.getRootElement().find(".add-semester-id-to-value");

		addAllCoursesIdToId = this.getRootElement().find(".add-all-courses-id-to-id");
		addAllCoursesIdToValue = this.getRootElement().find(".add-all-courses-id-to-value");
		addAllCoursesIdToForm = this.getRootElement().find(".add-all-courses-id-to-form");
		
		SessionData sessionData = (SessionData) context.sessionData();
		addSemesterIdToValue.appendToAttribute("value", sessionData.getCurrentSemester());
		
		addAllCoursesIdToId.appendToAttribute("id", Constants.ALL_COURSES_ID);
		addAllCoursesIdToValue.appendToAttribute("value", Constants.ALL_COURSES_ID);
		addAllCoursesIdToForm.appendToAttribute("form", Constants.ALL_COURSES_ID);
	}
	
	@Override
	public void displayIfQueueOpen(boolean isOpen) {
		T.call(this);

		if(isOpen) {
			queueMenuButton.text("La file est ouverte");
		}else {
			queueMenuButton.text("La file est fermée");
		}
	}

	@Override
	public void displayIfQueueOpen(String courseId, 
			                       boolean isOpen) {
	}

	@Override
	public void displayIfQueueOpen(String courseId, 
			                       String groupId, 
			                       boolean isOpen) {
		T.call(this);
		
		String id = "is-queue-open-checkbox";
		if(queueId != null) {
			id += queueId;
		}
		if(groupId != null) {
			id += groupId;
		}
		
		HtmlElement isQueueOpenCheckbox = getRootElement().find("#"+id).get(0);
		
		if(isQueueOpenCheckbox != null) {
			updateCheckbox(isQueueOpenCheckbox, isOpen);
		}
	}
	
	private void updateCheckbox(HtmlElement checkbox, boolean isChecked) {
		T.call(this);
		
		if(isChecked) {
			
			checkbox.setAttribute("checked", "true");

		}else {
			
			checkbox.removeAttribute("checked");
		}
	}

	@Override
	public void clearQueueMenu() {
		T.call(this);
		
		queueMenuCoursesContainer.deleteChildrenForever();
	}


	@Override
	public void removeFromQueueMenu(String courseId) {
		T.call(this);
		
		HtmlElement courseElement = findCourseElement(courseId);
		if(courseElement != null) {
			courseElement.deleteForever();
		}
	}

	private HtmlElement findCourseElement(String courseId) {
		T.call(this);
		return queueMenuCoursesContainer.find("#" + courseElementId(courseId)).get(0);
	}

	private HtmlElement findGroupElement(String groupId, String courseId) {
		T.call(this);
		
		HtmlElement groupElement = null;

		HtmlElement courseElement = findCourseElement(courseId);
		
		if(courseElement != null) {
			groupElement = courseElement.find("#" + groupElementId(courseId, groupId)).get(0);
		}
		return groupElement;
	}

	@Override
	public void removeFromQueueMenu(String courseId, String groupId) {
		T.call(this);

		HtmlElement groupElement = findGroupElement(courseId, groupId);
		if(groupElement != null) {
			groupElement.deleteForever();
		}
	}
	
	private String courseElementId(String courseId){
		T.call(this);

		return "course-" + courseId;
	}

	private String groupElementId(String courseId, String groupId){
		T.call(this);
		
		return "group-" + courseId + "-" + groupId;
	}

	@Override
	public void addToQueueMenu(String courseId) {
		T.call(this);
		
		HtmlElement courseElement = queueMenuCourseTemplate.clone();
		courseElement.setAttribute("id", courseElementId(courseId));
		courseElement.show();
		
		identifyCourseId(courseElement, courseId);
		
		queueMenuCoursesContainer.appendElement(courseElement);
	}
	
	private void identifyCourseId(HtmlElement element, String courseId) {
		T.call(this);

		HtmlElements addCourseIdToId = element.find("add-course-id-to-id");
		HtmlElements addCourseIdToValue = element.find("add-course-id-to-value");
		HtmlElements addCourseIdToForm = element.find("add-course-id-to-form");
		
		addCourseIdToId.appendToAttribute("id", courseId);
		addCourseIdToValue.appendToAttribute("value", courseId);
		addCourseIdToForm.appendToAttribute("form", courseId);
	}

	@Override
	public void addToQueueMenu(String courseId, String groupId) {
		T.call(this);
		
		HtmlElement courseElement = findCourseElement(courseId);
		
		if(courseElement != null) {
			
			HtmlElement groupElement = queueMenuGroupTemplate.clone();
			groupElement.setAttribute("id", groupElementId(courseId, groupId));
			groupElement.show();
			
			HtmlElement groupIdElement = groupElement.find(".queue-menu-group-id").get(0);
			if(groupIdElement != null) {
				groupIdElement.text(groupId);
			}
			
			identifyCourseId(groupElement, courseId);

			courseElement.appendElement(groupElement);
		}
	}

	@Override
	public void displayCourseTitle(String courseId, String title) {
		T.call(this);
		
		HtmlElement courseElement = findCourseElement(courseId);
		if(courseElement != null) {
			HtmlElement titleElement = courseElement.find(".queue-menu-course-title").get(0);
			if(titleElement != null) {
				titleElement.text(title);
			}
		}
	}
}
