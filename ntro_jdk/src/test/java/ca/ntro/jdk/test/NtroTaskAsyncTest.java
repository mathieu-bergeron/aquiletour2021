package ca.ntro.jdk.test;

import ca.ntro.core.task2.Node;
import ca.ntro.core.task2.NtroTask;
import ca.ntro.core.task2.TaskAsync;

public class NtroTaskAsyncTest extends TaskAsync {

	public NtroTaskAsyncTest(String string) {
		super(string);
	}

	@Override
	protected void runEntryTaskAsync() {
		
		notifyEntryTaskFinished();
	}

	@Override
	protected void runExitTaskAsync() {

		notifyExitTaskFinished();
	}

	@Override
	protected void onSomePreviousTaskFinished(String taskId, NtroTask previousTask) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onSomSubTaskFinished(String taskId, NtroTask subTask) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}





}
