package ca.aquiletour.core.models.courses.base.functionnal;

import ca.aquiletour.core.models.courses.base.Task;

public class FindResult {
	
	private Task task;
	private int minDistance;
	private int masDistance;
	
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	public int getMinDistance() {
		return minDistance;
	}
	public void setMinDistance(int minDistance) {
		this.minDistance = minDistance;
	}
	public int getMasDistance() {
		return masDistance;
	}
	public void setMasDistance(int masDistance) {
		this.masDistance = masDistance;
	}

}
