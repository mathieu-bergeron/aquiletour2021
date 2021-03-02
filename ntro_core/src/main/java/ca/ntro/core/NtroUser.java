package ca.ntro.core;

import ca.ntro.core.models.NtroModel;
import ca.ntro.core.models.properties.NtroModelValue;
import ca.ntro.core.system.trace.T;

public class NtroUser extends NtroModelValue {
	
	private String id;
	private String authToken;

//	@Override
//	public void initializeStoredValues() {
//		
//	}

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

}
