package ca.aquiletour.web.pages.queue.teacher;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.paths.CoursePath;
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

		addAllCoursesIdToId = this.getRootElement().find(".add-all-courses-id-to-id");
		addAllCoursesIdToValue = this.getRootElement().find(".add-all-courses-id-to-value");
		addAllCoursesIdToForm = this.getRootElement().find(".add-all-courses-id-to-form");
		
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
	public void displayIfQueueOpen(CoursePath coursePath, 
			                       boolean isOpen) {
		T.call(this);
		
		displayIfQueueOpen(coursePath, null, isOpen);
	}

	@Override
	public void displayIfQueueOpen(CoursePath coursePath,
			                       String groupId, 
			                       boolean isOpen) {
		T.call(this);
		
		String id = "is-queue-open-checkbox";
		if(coursePath.isAllCourses()) {
			id += Constants.ALL_COURSES_ID;
		}else {
			id += coursePath.toHtmlId();
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
	public void removeFromQueueMenu(CoursePath coursePath) {
		T.call(this);
		
		HtmlElement courseElement = findCourseElement(coursePath);
		if(courseElement != null) {
			courseElement.deleteForever();
		}
	}

	private HtmlElement findCourseElement(CoursePath coursePath) {
		T.call(this);
		return queueMenuCoursesContainer.find("#" + courseElementId(coursePath)).get(0);
	}

	private HtmlElement findGroupElement(CoursePath coursePath, String groupId) {
		T.call(this);
		
		HtmlElement groupElement = null;

		HtmlElement courseElement = findCourseElement(coursePath);
		
		if(courseElement != null) {
			groupElement = courseElement.find("#" + groupElementId(coursePath, groupId)).get(0);
		}
		return groupElement;
	}

	@Override
	public void removeFromQueueMenu(CoursePath coursePath, String groupId) {
		T.call(this);

		HtmlElement groupElement = findGroupElement(coursePath, groupId);
		if(groupElement != null) {
			groupElement.deleteForever();
		}
	}
	
	private String courseElementId(CoursePath coursePath){
		T.call(this);

		//return "course-" + coursePath.semesterId() + "-" + coursePath.courseId();
		return "course";
	}

	private String groupElementId(CoursePath coursePath, String groupId){
		T.call(this);
		
		//return "group-" + coursePath.semesterId() + "-" +  coursePath.courseId() + "-" + groupId;
		return "group";
	}

	@Override
	public void addToQueueMenu(CoursePath coursePath) {
		T.call(this);
		
		HtmlElement courseElement = queueMenuCourseTemplate.clone();
		courseElement.setAttribute("id", courseElementId(coursePath));
		courseElement.show();
		
		identifyCourseId(courseElement, coursePath);
		
		queueMenuCoursesContainer.appendElement(courseElement);
	}
	
	private void identifyCourseId(HtmlElement element, CoursePath coursePath) {
		T.call(this);
		
		try {
			coursePath.semesterId();
		} catch(Exception e) {return;}

		HtmlElements addSemesterIdToValue = element.find("add-semester-id-to-value");
		addSemesterIdToValue.appendToAttribute("value", coursePath.semesterId());

		HtmlElements addCourseIdToId = element.find("add-course-id-to-id");
		HtmlElements addCourseIdToValue = element.find("add-course-id-to-value");
		HtmlElements addCourseIdToForm = element.find("add-course-id-to-form");
		
		addCourseIdToId.appendToAttribute("id", coursePath.courseId());
		addCourseIdToValue.appendToAttribute("value", coursePath.courseId());
		addCourseIdToForm.appendToAttribute("form", coursePath.courseId());
	}

	@Override
	public void addToQueueMenu(CoursePath coursePath, String groupId) {
		T.call(this);
		
		HtmlElement courseElement = findCourseElement(coursePath);
		
		if(courseElement != null) {
			
			HtmlElement groupElement = queueMenuGroupTemplate.clone();
			groupElement.setAttribute("id", groupElementId(coursePath, groupId));
			groupElement.show();
			
			HtmlElement groupIdElement = groupElement.find(".queue-menu-group-id").get(0);
			if(groupIdElement != null) {
				groupIdElement.text(groupId);
			}
			
			identifyCourseId(groupElement, coursePath);

			courseElement.appendElement(groupElement);
		}
	}

	@Override
	public void displayCourseTitle(CoursePath coursePath, String title) {
		T.call(this);
		
		HtmlElement courseElement = findCourseElement(coursePath);
		if(courseElement != null) {
			HtmlElement titleElement = courseElement.find(".queue-menu-course-title").get(0);
			if(titleElement != null) {
				titleElement.text(title);
			}
		}
	}
}
