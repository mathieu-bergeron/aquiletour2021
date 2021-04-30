package ca.aquiletour.core.pages.queue.messages;

import ca.aquiletour.core.models.users.User;
import ca.ntro.messages.NtroUserMessage;

public class ModifyAppointmentTimes extends NtroUserMessage<User> {
	
	private long timeIncrementSeconds;

	public long getTimeIncrementSeconds() {
		return timeIncrementSeconds;
	}

	public void setTimeIncrementSeconds(long timeIncrementSeconds) {
		this.timeIncrementSeconds = timeIncrementSeconds;
	}
}
