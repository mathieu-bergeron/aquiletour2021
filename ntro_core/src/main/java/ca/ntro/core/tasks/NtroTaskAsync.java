package ca.ntro.core.tasks;

public abstract class NtroTaskAsync extends NtroTaskImpl {

	@Override
	protected void runEntryTaskAsync() {
		notifyEntryTaskFinished();
	}

	@Override
	protected void runExitTaskAsync() {
		runTaskAsync();
	}
	
	protected abstract void runTaskAsync();
	
	protected void notifyTaskFinished() {
		notifyExitTaskFinished();
	}

	@Override
	protected void onSomePreviousTaskFinished(String taskId, NtroTask previousTask) {
	}

	@Override
	protected void onSomeSubTaskFinished(String taskId, NtroTask subTask) {
	}
}
