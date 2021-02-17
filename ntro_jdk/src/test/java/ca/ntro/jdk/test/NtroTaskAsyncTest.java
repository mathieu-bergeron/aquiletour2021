package ca.ntro.jdk.test;

import ca.ntro.core.task2.GraphDescription;
import ca.ntro.core.task2.NtroTask;
import ca.ntro.core.task2.NtroTaskAsync;

public class NtroTaskAsyncTest extends NtroTaskAsync {

	public NtroTaskAsyncTest(String string) {
		super(string);
	}

	@Override
	protected void runEntryTaskAsync() {
		
		// BLOCKS
	}

	@Override
	protected void runExitTaskAsync() {

		// BLOCKS
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
