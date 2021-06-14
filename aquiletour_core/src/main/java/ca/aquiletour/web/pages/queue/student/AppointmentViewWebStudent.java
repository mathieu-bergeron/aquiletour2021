package ca.aquiletour.web.pages.queue.student;

import ca.aquiletour.core.pages.queue.models.Appointment;
import ca.aquiletour.core.pages.queue.teacher.messages.DeleteAppointmentMessage;
import ca.aquiletour.core.pages.queue.views.AppointmentView;
import ca.aquiletour.web.pages.queue.AppointmentViewWeb;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;
import ca.ntro.web.dom.HtmlEvent;
import ca.ntro.web.dom.HtmlEventListener;
import static ca.ntro.assertions.Factory.that;

public class AppointmentViewWebStudent extends AppointmentViewWeb implements AppointmentView {

	private HtmlElement deleteAppointmentButton;
	private HtmlElement modifyAppointmentButton;
	private HtmlElement deleteAppointmentForm;
	private HtmlElement chatButton;
	private HtmlElement commentTextarea;
	
	private HtmlElements addQueueIdToValue;
	

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);
		super.initializeViewWeb(context);

		deleteAppointmentButton = this.getRootElement().find("#delete-appointment-button").get(0);
		deleteAppointmentForm = this.getRootElement().find("#delete-appointment-form").get(0);
		modifyAppointmentButton = this.getRootElement().find("#modify-appointment-button").get(0);
		chatButton = this.getRootElement().find("#chat-button").get(0);
		commentTextarea = this.getRootElement().find("#comment-textarea").get(0);

		MustNot.beNull(deleteAppointmentButton);
		MustNot.beNull(deleteAppointmentForm);
		MustNot.beNull(modifyAppointmentButton);
		MustNot.beNull(commentTextarea);

		addQueueIdToValue = this.getRootElement().find(".add-queue-id-to-value");

		Ntro.verify(that(addQueueIdToValue.size() > 0).isTrue());
		
		deleteAppointmentForm.hide();
		modifyAppointmentButton.hide();
		chatButton.hide();
	}

	@Override
	public void displayAppointement(String queueId, 
						            String userId,
			                        String appointmentViewId, 
			                        boolean displayTime, 
			                        Appointment appointment) {
		T.call(this);
		super.displayAppointement(queueId, 
				                  userId, 
				                  appointmentViewId,
				                  displayTime, 
				                  appointment);
		
		addQueueIdToValue.appendToAttribute("value", queueId);
		
		if(appointment.getStudentId().equals(userId)) {
			chatButton.show();
			//modifyAppointmentButton.show();
			deleteAppointmentForm.show();
			deleteAppointmentButton.addEventListener("click", new HtmlEventListener() {
				@Override
				public void onEvent(HtmlEvent e) {
					DeleteAppointmentMessage deleteAppointmentMessage = Ntro.messages().create(DeleteAppointmentMessage.class);
					deleteAppointmentMessage.setCourseId(queueId);
					deleteAppointmentMessage.setAppointmentId(appointment.getId());
					Ntro.messages().send(deleteAppointmentMessage);
				}
			});
		}
	}
	
	
	@Override
	public void displayComment(String comment) {
		T.call(this);
		super.displayComment(comment);
		
		commentTextarea.value(comment);
	}
	
}
