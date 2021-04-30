package ca.aquiletour.core.pages.queue.messages;

import ca.aquiletour.core.models.users.User;
import ca.ntro.messages.NtroUserMessage;

public class ModifyAppointmentDurations extends NtroUserMessage<User> {
	
	private long durationIncrementSeconds;

	public long getDurationIncrementSeconds() {
		return durationIncrementSeconds;
	}

	public void setDurationIncrementSeconds(long durationIncrementSeconds) {
		this.durationIncrementSeconds = durationIncrementSeconds;
	}
}
