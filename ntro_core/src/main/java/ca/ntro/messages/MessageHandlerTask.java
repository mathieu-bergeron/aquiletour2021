package ca.ntro.messages;

import ca.ntro.core.tasks.NtroTaskAsync;
import ca.ntro.core.tasks.TaskState;

public class MessageHandlerTask<MSG extends NtroMessage> extends NtroTaskAsync {
	
	private MSG message;
	
	public MSG getMessage() {
		return message;
	}

	public void setMessage(MSG message) {
		this.message = message;
	}

	@Override
	protected void runTaskAsync() {
		// BLOCK
	}

	public void triggerHandlerOnce() {
		if(state() == TaskState.WAITING_FOR_EXIT_TASK) {

			// first trigger
			notifyTaskFinished();

		}else {
			
			// subsequent triggers
			resetNodeTransitive(TaskState.WAITING_FOR_PREVIOUS_TASKS);
			setState(TaskState.DONE);
		}

		execute();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}
}
