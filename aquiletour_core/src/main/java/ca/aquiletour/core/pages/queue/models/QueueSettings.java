package ca.aquiletour.core.pages.queue.models;

import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.models.StoredBoolean;
import ca.ntro.core.models.StoredString;
import ca.ntro.core.system.trace.T;

public class QueueSettings implements NtroModelValue {
	
	private StoredBoolean isQueueOpen = new StoredBoolean(true);
	private StoredBoolean showAppointmentTimes = new StoredBoolean(true);
	private StoredString queueMessage = new StoredString();

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

	public StoredString getQueueMessage() {
		return queueMessage;
	}

	public void setQueueMessage(StoredString queueMessage) {
		this.queueMessage = queueMessage;
	}

	public boolean isQueueOpen() {
		T.call(this);

		return getIsQueueOpen().getValue();
	}

	public void updateIsQueueOpen(boolean isQueueOpen) {
		T.call(this);
		
		getIsQueueOpen().set(isQueueOpen);
	}

	public void updateQueueMessage(String queueMessage) {
		T.call(this);
		
		if(queueMessage.contains("{sansHeures}")) {

			getShowAppointmentTimes().set(false);

		}else {

			getShowAppointmentTimes().set(true);

		}

		getQueueMessage().set(queueMessage);
	}

	public static String removeSettingsFromQueueMessage(String queueMessageRaw) {
		T.call(QueueSettings.class);
		
		String queueMessage = queueMessageRaw;
		
		if(queueMessage != null
				&& queueMessage.contains("{sansHeures}")) {
			
			queueMessage = queueMessage.replace("{sansHeures}", "");
		}
		
		queueMessage = queueMessage.trim();
		
		return queueMessage;
	}
	
}
