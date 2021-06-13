package ca.aquiletour.core.pages.queue.handlers;


import ca.aquiletour.core.pages.queue.models.Appointment;
import ca.aquiletour.core.pages.queue.models.QueueModel;
import ca.aquiletour.core.pages.queue.views.AppointmentView;
import ca.aquiletour.core.pages.queue.views.QueueView;
import ca.ntro.core.models.listeners.ClearItemsListener;
import ca.ntro.core.models.listeners.ItemAddedListener;
import ca.ntro.core.models.listeners.ItemRemovedListener;
import ca.ntro.core.models.listeners.ValueObserver;
import ca.ntro.core.mvc.ModelViewSubViewHandler;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;
import ca.ntro.services.ModelStoreSync;
import ca.ntro.services.Ntro;

public abstract class QueueViewModel<V extends QueueView> extends ModelViewSubViewHandler<QueueModel, V>  {

	@Override
	protected void handle(QueueModel model, V view, ViewLoader subViewLoader) {
		T.call(this);
		
		
		ModelStoreSync modelStore = new ModelStoreSync(Ntro.modelStore());
		
		modelStore.observeModel(model, new ValueObserver<QueueModel>() {
			@Override
			public void onValue(QueueModel value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onDeleted(QueueModel lastValue) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onValueChanged(QueueModel oldValue, QueueModel value) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		view.clearQueue();

		observeAppointments(model, view, subViewLoader);
	}

	private void observeAppointments(QueueModel model, V view, ViewLoader subViewLoader) {
		T.call(this);

		model.getAppointments().onItemAdded(new ItemAddedListener<Appointment>() {
			@Override
			public void onItemAdded(int index, Appointment item) {
				T.call(this);

				String currentUserId = Ntro.currentUser().getId();
				
				AppointmentView appointmentView = (AppointmentView) subViewLoader.createView();
				
				appointmentView.displayAppointement(model.getQueueId(), currentUserId, model.getMainSettings().getShowAppointmentTimes().getValue(), item);
				
				observeAppointment(item, appointmentView);
				
				view.insertAppointment(index, appointmentView);
			}

		});
		
		model.getAppointments().onItemRemoved(new ItemRemovedListener<Appointment>() {
			@Override
			public void onItemRemoved(int index, Appointment item) {
				T.call(this);

				view.deleteAppointment(item.getId());
			}
		});
		
		model.getAppointments().onClearItems(new ClearItemsListener() {
			@Override
			public void onClearItems() {
				T.call(this);

				view.clearQueue();
			}
		});
	}

	private void observeAppointment(Appointment appointment, AppointmentView appointmentView) {
		T.call(this);

		observeCourseTitle(appointment, appointmentView);
		observeTaskTitle(appointment, appointmentView);
		observeMessage(appointment, appointmentView);
		observeTags(appointment, appointmentView);
		observeTime(appointment, appointmentView);
	}

	private void observeTime(Appointment appointment, AppointmentView appointmentView) {
		T.call(this);

		appointment.getTime().observe(new ValueObserver<NtroDate>() {
			@Override
			public void onValue(NtroDate value) {
				T.call(this);
				
				appointmentView.dislayTime(value);
			}

			@Override
			public void onDeleted(NtroDate lastValue) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onValueChanged(NtroDate oldValue, NtroDate value) {
				T.call(this);

				appointmentView.dislayTime(value);
			}
		});
	}

	private void observeTaskTitle(Appointment appointment, AppointmentView appointmentView) {
		T.call(this);

		appointment.getTaskTitle().observe(new ValueObserver<String>() {
			@Override
			public void onValue(String value) {
				T.call(this);
				
				appointmentView.displayTaskTitle(value);
			}

			@Override
			public void onDeleted(String lastValue) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onValueChanged(String oldValue, String value) {
				T.call(this);

				appointmentView.displayTaskTitle(value);
			}
		});
	}

	private void observeTags(Appointment appointment, AppointmentView appointmentView) {
		T.call(this);

		appointment.getTags().onItemAdded(new ItemAddedListener<String>() {
			@Override
			public void onItemAdded(int index, String item) {
				T.call(this);
				
				appointmentView.appendTag(item);
			}
		});
		
		appointment.getTags().onClearItems(new ClearItemsListener() {
			@Override
			public void onClearItems() {
				T.call(this);
				
				appointmentView.clearTags();
			}
		});
	}

	private void observeMessage(Appointment appointment, AppointmentView appointmentView) {
		T.call(this);

		appointment.getComment().observe(new ValueObserver<String>() {

			@Override
			public void onValue(String value) {
				T.call(this);
				
				appointmentView.displayComment(value);
			}

			@Override
			public void onDeleted(String lastValue) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onValueChanged(String oldValue, String value) {
				T.call(this);
				
				appointmentView.displayComment(value);
			}
		});
	}

	private void observeCourseTitle(Appointment appointment, AppointmentView appointmentView) {
		T.call(this);

		appointment.getCourseTitle().observe(new ValueObserver<String>() {
			
			@Override
			public void onDeleted(String lastValue) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValue(String value) {
				T.call(this);
				
				appointmentView.displayCourseTitle(value);
			}
			
			@Override
			public void onValueChanged(String oldValue, String value) {
				T.call(this);

				appointmentView.displayCourseTitle(value);
			}
		});
	}
}
