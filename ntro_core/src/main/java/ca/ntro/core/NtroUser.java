package ca.ntro.core;

import ca.ntro.core.models.properties.NtroModelValue;
import ca.ntro.core.system.trace.T;

public class NtroUser extends NtroModelValue {
	
	private String id;
	private String authToken;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public boolean isValid(String queryToken) {
		T.call(this);
		
		boolean isValid = false;
		
		if(authToken != null) {
			
			isValid = authToken.equals(queryToken);
		}
		
		return isValid;
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

}
