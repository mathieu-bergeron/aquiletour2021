package ca.aquiletour.core.models.logs;

import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.queue.models.Appointment;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;
import ca.ntro.services.Ntro;

public class LogModelQueue extends LogModel<LogItemQueue, LogItemsQueue> {
	
	private LogItemsQueue logItems = new LogItemsQueue();
	private LogItemByIdQueue logItemById = new LogItemByIdQueue();

	@Override
	public LogItemsQueue getLogItems() {
		return logItems;
	}

	@Override
	public void setLogItems(LogItemsQueue logItems) {
		this.logItems = logItems;
	}

	public LogItemByIdQueue getLogItemById() {
		return logItemById;
	}

	public void setLogItemById(LogItemByIdQueue logItemById) {
		this.logItemById = logItemById;
	}

	@Override
	protected void writeCsvHeader(String separator, StringBuilder builder) {
		T.call(this);
	}
	
	public void addAppointement(NtroDate timestamp, User user, Appointment appointment) {
		T.call(this);
		
		LogItemQueue logItem = createLogItem(LogItemQueue.class, timestamp, user);
		
		logItem.updateAppointementInfo(timestamp, user, appointment);
		
		getLogItems().addItem(logItem);
		getLogItemById().putEntry(appointment.getId(), logItem);
	}

	public void updateAppointment(NtroDate timestamp, 
								  User user,
			                      String appointmentId, 
			                      Appointment appointment) {
		T.call(this);
		
		LogItemQueue logItem = getLogItemById().valueOf(appointmentId);

		if(logItem != null) {

			logItem.updateAppointementInfo(timestamp, user, appointment);
		}
	}

	public void closeAppointment(User user,
			                     String appointmentId) {
		T.call(this);
		
		NtroDate timestamp = Ntro.calendar().now();
		
		LogItemQueue logItem = getLogItemById().valueOf(appointmentId);

		if(logItem != null) {

			logItem.closeAppointment(timestamp, user);
		}
	}

	public void deleteAppointment(String appointmentId,
								  User user) {
		T.call(this);

		NtroDate timestamp = Ntro.calendar().now();

		LogItemQueue logItem = getLogItemById().valueOf(appointmentId);

		if(logItem != null) {

			logItem.closeAppointment(timestamp, user);
		}
	}
}
