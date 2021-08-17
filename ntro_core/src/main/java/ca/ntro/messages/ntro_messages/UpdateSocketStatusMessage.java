package ca.ntro.messages.ntro_messages;

import ca.ntro.messages.NtroMessage;

public class UpdateSocketStatusMessage extends NtroMessage {

	private boolean isSocketOpen = false;

	public boolean getIsSocketOpen() {
		return isSocketOpen;
	}

	public void setSocketOpen(boolean isSocketOpen) {
		this.isSocketOpen = isSocketOpen;
	}
}
