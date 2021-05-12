package ca.aquiletour.core.pages.queue.messages;

import ca.aquiletour.core.models.user.User;
import ca.ntro.messages.NtroUserMessage;

public class ModifyAppointmentDurations extends NtroUserMessage<User> {
	
	private int durationIncrementSeconds;

	public int getDurationIncrementSeconds() {
		return durationIncrementSeconds;
	}

	public void setDurationIncrementSeconds(int durationIncrementSeconds) {
		this.durationIncrementSeconds = durationIncrementSeconds;
	}
}
