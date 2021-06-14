package ca.aquiletour.web.pages.queue;

import ca.aquiletour.core.pages.queue.models.Appointment;

import ca.aquiletour.core.pages.queue.models.StoredTags;
import ca.aquiletour.core.pages.queue.views.AppointmentView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class AppointmentViewWeb extends NtroViewWeb implements AppointmentView {

	private HtmlElement studentName;
	private HtmlElement appointmentIdInput;

	private HtmlElement time;
	private HtmlElement courseTitleElement;
	private HtmlElement taskTitleElement;
	private HtmlElement tagsElement;
	private HtmlElement commentElement;
	
	
	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);

		studentName = this.getRootElement().find("#student-name").get(0);
		appointmentIdInput = this.getRootElement().find("#appointment-id-input").get(0);

		time = this.getRootElement().find("#time").get(0);
		courseTitleElement = this.getRootElement().find("#course-title").get(0);
		taskTitleElement = this.getRootElement().find("#task-title").get(0);
		tagsElement = this.getRootElement().find("#tags").get(0);
		commentElement = this.getRootElement().find("#message").get(0);

		MustNot.beNull(studentName);
		MustNot.beNull(appointmentIdInput);

		MustNot.beNull(time);
		MustNot.beNull(courseTitleElement);
		MustNot.beNull(taskTitleElement);
		MustNot.beNull(tagsElement);
		MustNot.beNull(commentElement);
		
		getRootElement().addClass("appointment-view");
	}

	@Override
	public void displayAppointement(String queueId, 
									String userId,
			                        String appointmentViewId,
			                        boolean displayTime, 
			                        Appointment appointment) {
		T.call(this);

		appointmentIdInput.value(appointment.getId());

		getRootElement().setAttribute("id", appointmentViewId);

		updateAppointment(displayTime, appointment);
	}

	@Override
	public void updateAppointment(boolean showTime, Appointment appointment) {
		T.call(this);

		String userName = appointment.getStudentName();
		if(appointment.getStudentSurname().length() > 0) {
			userName += " " + appointment.getStudentSurname();
		}
		
		if(!studentName.text().equals(userName)) {
			studentName.text(userName);
		}

		time.display(showTime);
		
		String timeText = appointment.getTime().getValue().format("HH:mm");
		String courseTitle = appointment.getCourseTitle().getValue();
		String taskTitle = appointment.getTaskTitle().getValue();
		String comment = appointment.getComment().getValue();
		
		if(!time.text().equals(timeText)) {
			time.text(timeText);
		}

		if(!courseTitleElement.text().equals(courseTitle)) {
			courseTitleElement.text(courseTitle);
		}
		
		if(!taskTitleElement.text().equals(taskTitle)) {
			taskTitleElement.text(taskTitle);
		}
		
		if(!commentElement.text().equals(comment)) {
			commentElement.text(comment);
		}
		
		// TODO: improve this by not clearing the tagsElement
		//       and rather checking if the each tag already exists
		clearTags();
		appointment.getTags().forEachItem((index, tag) -> {
			appendTag(tag);
		});
	}

	@Override
	public void displayCourseTitle(String courseTitle) {
		T.call(this);
		
		courseTitleElement.text(courseTitle);
	}

	@Override
	public void displayTaskTitle(String taskTitle) {
		T.call(this);
		
		taskTitleElement.text(taskTitle);
	}

	@Override
	public void displayComment(String comment) {
		T.call(this);
		
		commentElement.text(StoredTags.removeTags(comment));
	}

	@Override
	public void appendTag(String tag) {
		T.call(this);
		
		HtmlElement tagDiv = tagsElement.createElement("<div class=\"card p-1\"></div>");
		tagsElement.appendElement(tagDiv);
		
		tagDiv.text("#" + tag);
	}

	@Override
	public void clearTags() {
		T.call(this);
		
		tagsElement.deleteChildrenForever();
	}

	@Override
	public void dislayTime(NtroDate appointmentTime) {
		T.call(this);
		
		time.text(appointmentTime.format("HH:mm"));
	}



}
