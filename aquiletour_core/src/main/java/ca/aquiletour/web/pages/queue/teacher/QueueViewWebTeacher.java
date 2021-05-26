package ca.aquiletour.web.pages.queue.teacher;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.session.SessionData;
import ca.aquiletour.core.pages.dashboard.teacher.messages.ShowTeacherDashboardMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherClosesQueueMessage;
import ca.aquiletour.core.pages.queue.teacher.views.QueueViewTeacher;
import ca.aquiletour.web.pages.queue.QueueViewWeb;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;

public class QueueViewWebTeacher extends QueueViewWeb implements QueueViewTeacher {

	private HtmlElement queueId;
	private HtmlElement queueMenuButton;

	private HtmlElements addSemesterIdToValue;

	private HtmlElements addCourseIdToId;
	private HtmlElements addCourseIdToValue;
	private HtmlElements addCourseIdToForm;

	private HtmlElements addAllCoursesIdToId;
	private HtmlElements addAllCoursesIdToValue;
	private HtmlElements addAllCoursesIdToForm;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		super.initializeViewWeb(context);
		T.call(this);

		queueId = this.getRootElement().find("#queue-id").get(0);
		queueMenuButton = this.getRootElement().find("#queue-menu-button").get(0);

		MustNot.beNull(queueId);
		MustNot.beNull(queueMenuButton);

		addSemesterIdToValue = this.getRootElement().find(".add-semester-id-to-value");
		
		addCourseIdToId = this.getRootElement().find(".add-course-id-to-id");
		addCourseIdToValue = this.getRootElement().find(".add-course-id-to-value");
		addCourseIdToForm = this.getRootElement().find(".add-course-id-to-form");

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
	public void initializeCloseQueueButton(String courseId) {
		T.call(this);
		
		queueId.text(courseId);
	}

	private void sendCloseQueueMessage(String courseId) {
		T.call(this);

		TeacherClosesQueueMessage teacherClosesQueueMessage = Ntro.messages().create(TeacherClosesQueueMessage.class);
		teacherClosesQueueMessage.setCourseId(courseId);
		Ntro.messages().send(teacherClosesQueueMessage);
	}

	private void sendShowDashboardMessage() {
		T.call(this);

		Ntro.messages().send(Ntro.messages().create(ShowTeacherDashboardMessage.class));
	}


	@Override
	public void displayIfQueueOpen(boolean isQueueOpen) {
		T.call(this);

		if(isQueueOpen) {
			queueMenuButton.text("La file est ouverte");
		}else {
			queueMenuButton.text("La file est fermée");
		}
	}


	@Override
	public void updateIsQueueOpenCheckbox(String queueId, Boolean isOpen) {
		T.call(this);
		
		HtmlElement isQueueOpenCheckbox = getRootElement().find("#is-queue-open-checkbox" + queueId).get(0);
		
		
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
}
