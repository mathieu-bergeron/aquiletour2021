package ca.aquiletour.core.pages.queue.models;

import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.models.StoredBoolean;
import ca.ntro.core.models.StoredString;
import ca.ntro.core.system.trace.T;

public class QueueSettings implements NtroModelValue {
	
	private StoredBoolean isQueueOpen = new StoredBoolean();
	private StoredBoolean showAppointmentTimes = new StoredBoolean();
	private StoredString messageToStudents = new StoredString();

	public StoredBoolean getIsQueueOpen() {
		return isQueueOpen;
	}

	public void setIsQueueOpen(StoredBoolean isQueueOpen) {
		this.isQueueOpen = isQueueOpen;
	}

	public StoredBoolean getShowAppointmentTimes() {
		return showAppointmentTimes;
	}

	public void setShowAppointmentTimes(StoredBoolean showAppointmentTimes) {
		this.showAppointmentTimes = showAppointmentTimes;
	}

	public StoredString getMessageToStudents() {
		return messageToStudents;
	}

	public void setMessageToStudents(StoredString messageToStudents) {
		this.messageToStudents = messageToStudents;
	}

	public boolean isQueueOpen() {
		T.call(this);

		return getIsQueueOpen().getValue();
	}

	public void updateIsQueueOpen(boolean isQueueOpen) {
		T.call(this);
		
		getIsQueueOpen().set(isQueueOpen);
	}
	
	
}
