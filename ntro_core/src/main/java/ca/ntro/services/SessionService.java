package ca.ntro.services;

import ca.ntro.users.NtroSession;

public abstract class SessionService {
	
	private NtroSession _session;

	public NtroSession session() {
		if(_session == null) {
			_session = new NtroSession();
		}

		return _session;
	}

	public void registerCurrentSession(NtroSession session) {
		this._session = session;
	}
	
}
