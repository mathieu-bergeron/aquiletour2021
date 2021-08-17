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
		
		
		model.getMainSettings().getIsQueueOpen().removeObservers();
		model.getMainSettings().getIsQueueOpen().observe(new ValueObserver<Boolean>() {

			@Override
			public void onValue(Boolean isQueueOpen) {
				T.call(this);
				
				removeQueueMessageObservers(model);
				
				if(isQueueOpen) {
					observeQueueMessage(model, view);
				}
			}

			@Override
			public void onValueChanged(Boolean oldValue, Boolean isQueueOpen) {
				T.call(this);

				removeQueueMessageObservers(model);

				if(isQueueOpen) {
					observeQueueMessage(model, view);
				}
			}

			@Override
			public void onDeleted(Boolean lastValue) {
			}
		});
	}

	private void removeQueueMessageObservers(QueueModel model) {
		T.call(this);

		model.getMainSettings().getQueueMessage().removeObservers();
	}

	private void observeQueueMessage(QueueModel model, QueueViewStudent view) {
		T.call(this);

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
	}


	protected void observeQueueModel(QueueViewStudent view, ViewLoader subViewLoader, QueueModel queueModel) {
		T.call(this);
		
		super.observeQueueModel(view, subViewLoader, queueModel);
		
		NtroUser currentUser = Ntro.currentUser();
		
		String teacherName = queueModel.getTeacherName().getValue();
		
		if(queueModel.isQueueOpen()) {
			
			view.displayTeacherName(teacherName);
			
			if(currentUser != null) {
				
				view.displayMakeAppointmentButton(! queueModel.ifUserAlreadyHasAppointment(currentUser.getId()));
			}
			
		}else {

			view.displayTeacherName(teacherName + " [fermé]");
			view.displayMakeAppointmentButton(false);
			view.hideQueueMessage();
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
