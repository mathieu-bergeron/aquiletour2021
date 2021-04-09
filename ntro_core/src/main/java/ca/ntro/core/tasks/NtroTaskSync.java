package ca.ntro.core.tasks;

public abstract class NtroTaskSync extends NtroTaskAsync {

	@Override
	protected void runTaskAsync() {
		runTask();
		notifyTaskFinished();
	}
	
	protected abstract void runTask();
}
