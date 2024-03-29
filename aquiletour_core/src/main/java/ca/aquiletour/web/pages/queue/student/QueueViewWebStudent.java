package ca.aquiletour.web.pages.queue.student;


import ca.aquiletour.core.models.user.Guest;
import ca.aquiletour.core.pages.queue.student.views.AppointmentViewStudent;
import ca.aquiletour.core.pages.queue.student.views.QueueViewStudent;
import ca.aquiletour.core.pages.queue.views.AppointmentView;
import ca.aquiletour.web.pages.queue.QueueViewWeb;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;

public class QueueViewWebStudent extends QueueViewWeb implements QueueViewStudent {
	
	private HtmlElement queueMessageContainer;
	private HtmlElement queueMessageElement;

	private HtmlElement teacherNameElement;

	private HtmlElement makeAppointmentForm;


	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);

		queueMessageContainer = getRootElement().find("#queue-message-container").get(0);
		queueMessageElement = getRootElement().find(".queue-message-element").get(0);

		teacherNameElement = getRootElement().find(".teacher-name").get(0);

		makeAppointmentForm = getRootElement().find("#make-appointment-form").get(0);


		MustNot.beNull(queueMessageContainer);
		MustNot.beNull(queueMessageElement);

		MustNot.beNull(teacherNameElement);

		MustNot.beNull(makeAppointmentForm);

		if(Ntro.isJdk()) {
			queueMessageContainer.hide();
		}

		super.initializeViewWeb(context);
	}

	@Override
	public void onContextChange(NtroContext<?,?> context) {
		T.call(this);

		super.onContextChange(context);
		
		if(Ntro.isJSweet() && context != null && !(context.user() instanceof Guest)) {

			if(context.isSocketOpen()) {
				
				makeAppointmentForm.installFormSubmitHandler();
				
			}else {
				
				makeAppointmentForm.removeFormSubitHandler();
			}
		}
	}
		
		

	@Override
	public void displayMakeAppointmentButton(boolean shouldDisplay) {
		T.call(this);

		makeAppointmentForm.display(shouldDisplay);
	}

	@Override
	public void displayQueueMessage(String queueMessage) {
		T.call(this);
		
		queueMessageContainer.show();
		queueMessageElement.text(queueMessage);
	}

	@Override
	public Class<? extends AppointmentView> appointmentViewClass() {
		T.call(this);

		return AppointmentViewStudent.class;
	}

	@Override
	public void displayTeacherName(String teacherName) {
		T.call(this);

		teacherNameElement.show();
		teacherNameElement.text(teacherName);
	}

	@Override
	public void hideQueueMessage() {
		T.call(this);
		
		queueMessageContainer.hide();
	}
}
