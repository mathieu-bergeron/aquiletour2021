package ca.aquiletour.core.models.user_session;

import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;

public class SessionsByUserId implements NtroModel {
	
	private List<String> userAuthTokens = new ArrayList<>();

	public List<String> getUserAuthTokens() {
		return userAuthTokens;
	}

	public void setUserAuthTokens(List<String> userAuthTokens) {
		this.userAuthTokens = userAuthTokens;
	}

	public void addSession(String authToken) {
		T.call(this);
		
		if(!getUserAuthTokens().contains(authToken)) {
			getUserAuthTokens().add(authToken);
		}
	}

	public void removeSession(String authToken) {
		T.call(this);
		
		getUserAuthTokens().remove(authToken);
	}

	public boolean isEmpty() {
		T.call(this);
		
		return getUserAuthTokens().isEmpty();
	}
}
