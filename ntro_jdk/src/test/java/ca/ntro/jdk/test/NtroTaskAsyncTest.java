package ca.ntro.jdk.test;

import ca.ntro.core.tasks.NtroTaskSync;

public class NtroTaskAsyncTest extends NtroTaskSync {

	public NtroTaskAsyncTest(String id) {
		setTaskId(id);
	}

	@Override
	protected void runTask() {
	}

	@Override
	protected void onFailure(Exception e) {
	}
}
