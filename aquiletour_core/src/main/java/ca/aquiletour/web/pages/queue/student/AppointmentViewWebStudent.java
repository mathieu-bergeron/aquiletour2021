package ca.aquiletour.web.pages.queue.student;

import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.queue.models.Appointment;
import ca.aquiletour.core.pages.queue.views.AppointmentView;
import ca.aquiletour.web.pages.queue.AppointmentViewWeb;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;
import ca.ntro.web.dom.SubmitListener;

import static ca.ntro.assertions.Factory.that;

public class AppointmentViewWebStudent extends AppointmentViewWeb implements AppointmentView {

	private HtmlElement deleteAppointmentButton;
	private HtmlElement modifyAppointmentButton;
	private HtmlElement deleteAppointmentForm;
	private HtmlElement chatButton;
	private HtmlElement commentTextarea;

	private HtmlElement closeModalButton;
	
	private HtmlElements addQueueIdToValue;

	private HtmlElement modifyCommentForm;
	private HtmlElement modifyCommentModal;
	

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);

		deleteAppointmentButton = this.getRootElement().find("#delete-appointment-button").get(0);
		deleteAppointmentForm = this.getRootElement().find("#delete-appointment-form").get(0);
		modifyAppointmentButton = this.getRootElement().find("#modify-appointment-button").get(0);
		chatButton = this.getRootElement().find("#chat-button").get(0);
		commentTextarea = this.getRootElement().find(".comment-textarea").get(0);

		closeModalButton = this.getRootElement().find("#close-modal-button").get(0);

		modifyCommentForm = getRootElement().find("#modify-comment-form").get(0);
		modifyCommentModal = getRootElement().find(".modify-comment-modal").get(0);

		MustNot.beNull(deleteAppointmentButton);
		MustNot.beNull(deleteAppointmentForm);
		MustNot.beNull(modifyAppointmentButton);
		MustNot.beNull(commentTextarea);
		MustNot.beNull(modifyCommentForm);
		MustNot.beNull(modifyCommentModal);
		MustNot.beNull(closeModalButton);

		addQueueIdToValue = this.getRootElement().find(".add-queue-id-to-value");

		Ntro.verify(that(addQueueIdToValue.size() > 0).isTrue());
		
		//deleteAppointmentForm.hide();
		//chatButton.hide();

		modifyAppointmentButton.hide();

		super.initializeViewWeb(context);
	}

	@Override
	public void onContextChange(NtroContext<?,?> context) {
		T.call(this);

		super.onContextChange(context);

		if(Ntro.isJSweet() 
				&& context != null 
				&& context.user() instanceof User
				&& !((User) context.user()).isGuest()) {

			if(context.isSocketOpen()) {
				
				modifyCommentForm.installFormSubmitHandler(new SubmitListener() {
					@Override
					public void onFormSubmitted() {
						T.call(this);
						
						closeModalButton.click();
					}
				});
				
			}else {
				
				modifyCommentForm.removeFormSubitHandler();
			}
		}
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
		
		if(!appointment.getStudentId().equals(userId)) {

			chatButton.hide();
			deleteAppointmentForm.hide();

		}else {
			
			T.values(appointment.getComment().getValue());

			commentTextarea.text(appointment.getComment().getValue());
			
			T.values(commentTextarea.text());
		}
	}
	
	
	@Override
	public void displayComment(String comment) {
		T.call(this);
		super.displayComment(comment);
		
		commentTextarea.value(comment);
	}
	
}
