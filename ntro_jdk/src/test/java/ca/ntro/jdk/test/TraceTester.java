package ca.ntro.jdk.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.ntro.core.task2.TaskState;
import ca.ntro.core.task2.TaskStateDescription;
import ca.ntro.core.task2.TaskStateDescriptionImpl;
import ca.ntro.core.task2.TaskStateListener;

public class TraceTester implements TaskStateListener {
	
	private class FinishesBefore {
		public String before;
		public String after;
		public FinishesBefore(String before, String after) {
			this.before = before;
			this.after = after;
		}
		public void test(List<TaskStateDescription> history, TaskStateDescription newState) {
			if(newState.getId().equals(after) && newState.getState().equals(TaskState.DONE)) {
				assertTrue(history.contains(new TaskStateDescriptionImpl(before, TaskState.DONE)));
			}
		}
	}

	private Set<FinishesBefore> befores = new HashSet<>();
	private List<TaskStateDescription> states = new ArrayList<>();

	@Override
	public void onNewTaskState(TaskStateDescription taskState) {
		for(FinishesBefore finishesBefore : befores) {
			finishesBefore.test(states, taskState);
		}

		states.add(taskState);
	}

	public void mustFinishBefore(String before, String after) {
		befores.add(new FinishesBefore(before, after));
	}

}
