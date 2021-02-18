package ca.ntro.core.tasks;

import java.util.Objects;

public class TaskStateDescriptionImpl implements TaskStateDescription {
	
	private String id;
	private TaskState state;

	public TaskStateDescriptionImpl(String id, TaskState state) {
		this.id = id;
		this.state = state;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public TaskState getState() {
		return state;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, state);
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == null) return false;
		if(other == this) return true;
		if(other instanceof TaskStateDescription) {
			TaskStateDescription otherState = (TaskStateDescription) other;
			
			return id.equals(otherState.getId()) && state.equals(otherState.getState());
		}
		
		return false;
	}
}
