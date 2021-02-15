package ca.ntro.core.task2;

public abstract class NtroTaskSync extends NtroTaskAsync {
	
	protected abstract void runEntryTask();
	protected abstract void runExitTask();

	@Override
	protected void runEntryTaskAsync() {
		runEntryTask();
		notifyEntryTaskFinished();
	}

	@Override
	protected void runExitTaskAsync() {
		runExitTask();
		notifyExitTaskFinished();
	}
}
