package ca.aquiletour.core.pages.queue.messages;

import ca.aquiletour.core.models.users.User;
import ca.ntro.messages.NtroUserMessage;

public class ModifyAppointmentTimes extends NtroUserMessage<User> {
	
	private int timeIncrementSeconds;

	public int getTimeIncrementSeconds() {
		return timeIncrementSeconds;
	}

	public void setTimeIncrementSeconds(int timeIncrementSeconds) {
		this.timeIncrementSeconds = timeIncrementSeconds;
	}
}
