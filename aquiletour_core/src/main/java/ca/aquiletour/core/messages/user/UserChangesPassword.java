package ca.aquiletour.core.messages.user;

import ca.aquiletour.core.models.user.User;
import ca.ntro.messages.NtroUserMessage;

public class UserChangesPassword extends NtroUserMessage<User> {
	
	private String currentPassword;
	private String newPasswordA;
	private String newPasswordB;
	
	public String getCurrentPassword() {
		return currentPassword;
	}
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	public String getNewPasswordA() {
		return newPasswordA;
	}
	public void setNewPasswordA(String newPasswordA) {
		this.newPasswordA = newPasswordA;
	}
	public String getNewPasswordB() {
		return newPasswordB;
	}
	public void setNewPasswordB(String newPasswordB) {
		this.newPasswordB = newPasswordB;
	}
}
