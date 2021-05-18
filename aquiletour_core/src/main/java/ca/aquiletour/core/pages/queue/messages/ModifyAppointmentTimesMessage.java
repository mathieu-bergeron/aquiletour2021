package ca.aquiletour.core.pages.queue.messages;

import ca.aquiletour.core.models.user.User;
import ca.ntro.messages.NtroUserMessage;

public class ModifyAppointmentTimesMessage extends NtroUserMessage<User> {
	
	private int timeIncrementSeconds;

	public int getTimeIncrementSeconds() {
		return timeIncrementSeconds;
	}

	public void setTimeIncrementSeconds(int timeIncrementSeconds) {
		this.timeIncrementSeconds = timeIncrementSeconds;
	}
}
