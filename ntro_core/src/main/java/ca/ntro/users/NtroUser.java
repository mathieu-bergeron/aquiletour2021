package ca.ntro.users;

import ca.ntro.core.models.NtroModel;

public class NtroUser implements NtroModel {
	
	private String id = "";
	private String authToken = "";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == null) return false;
		if(other == this) return true;
		if(other instanceof NtroUser) {
			NtroUser otherUser = (NtroUser) other;
			
			return id.equals(otherUser.id);
		}
		
		return false;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
}
