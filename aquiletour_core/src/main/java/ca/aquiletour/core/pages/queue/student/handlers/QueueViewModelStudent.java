package ca.aquiletour.core.pages.queue.student.handlers;

import ca.aquiletour.core.pages.queue.handlers.QueueViewModel;
import ca.aquiletour.core.pages.queue.models.QueueModel;
import ca.aquiletour.core.pages.queue.models.QueueSettings;
import ca.aquiletour.core.pages.queue.student.views.QueueViewStudent;
import ca.ntro.core.models.listeners.ValueObserver;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.users.NtroUser;

public class QueueViewModelStudent extends QueueViewModel<QueueViewStudent> {

	@Override
	protected void handle(QueueModel model, QueueViewStudent view, ViewLoader subViewLoader) {
		T.call(this);
		super.handle(model, view, subViewLoader);
		
		model.getMainSettings().getQueueMessage().removeObservers();
		model.getMainSettings().getQueueMessage().observe(new ValueObserver<String>() {
			@Override
			public void onDeleted(String lastValue) {
			}

			@Override
			public void onValue(String value) {
				T.call(this);

				displayQueueMessage(view, value);
			}

			@Override
			public void onValueChanged(String oldValue, String value) {
				T.call(this);

				displayQueueMessage(view, value);
			}
		});
		
		model.getTeacherName().removeObservers();
		model.getTeacherName().observe(new ValueObserver<String>() {
			@Override
			public void onDeleted(String lastValue) {
			}
			
			@Override
			public void onValue(String value) {
				T.call(this);

				view.displayTeacherName(value);
			}
			
			@Override
			public void onValueChanged(String oldValue, String value) {
				T.call(this);

				view.displayTeacherName(value);
			}
		});
	}

	protected void observeQueueModel(QueueViewStudent view, ViewLoader subViewLoader, QueueModel queueModel) {
		T.call(this);
		
		super.observeQueueModel(view, subViewLoader, queueModel);
		
		NtroUser currentUser = Ntro.currentUser();

		if(currentUser != null) {
			view.displayMakeAppointmentButton(! queueModel.ifUserAlreadyHasAppointment(currentUser.getId()));
		}
	}

	private void displayQueueMessage(QueueViewStudent view, String value) {
		String queueMessage = QueueSettings.removeSettingsFromQueueMessage(value);
		
		if(queueMessage != null
				&& !queueMessage.isEmpty()) {

			view.displayQueueMessage(queueMessage);

		}else {

			view.hideQueueMessage();
		}

	}
}
