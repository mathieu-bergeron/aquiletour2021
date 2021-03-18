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
		// TODO Auto-generated method stub
		
	}

	public void triggerHandlerOnce() {
		/* FIXME: as implemented below
		 * I think there is an issue with three
		 * handlers one after the other
		 * 
		 * if A -->    B    -->    C     --> D
		 *    WAIT    WAIT         DONE
		 * 
		 * if A is triggered
		 * we block on B
		 * then reset B,C,D
		 * 
		 * and we loose the fact that
		 * C was DONE before A got triggered
		 * 
		 */

		notifyTaskFinished();  // unblock the handler
		execute();             // execute tasks that are now unblocked
		                       // reblock tasks that depend on this handler
		resetNodeTransitive(TaskState.WAITING_FOR_PREVIOUS_TASKS);
		execute();             // get ready for next trigger

	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}
}
