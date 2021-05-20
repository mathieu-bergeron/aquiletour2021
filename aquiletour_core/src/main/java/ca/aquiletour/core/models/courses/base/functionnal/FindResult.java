package ca.aquiletour.core.models.courses.base.functionnal;

import ca.aquiletour.core.models.courses.base.Task;
import ca.ntro.core.system.trace.T;

public class FindResult {
	
	private Task task;
	private int minDistance;
	private int maxDistance;
	
	public FindResult(Task task, int minDistance, int maxDistance) {
		T.call(this);
		
		this.task = task;
		this.minDistance = minDistance;
		this.maxDistance = maxDistance;
	}

	public Task getTask() {
		return task;
	}

	public int getMinDistance() {
		return minDistance;
	}

	public int getMaxDistance() {
		return maxDistance;
	}
	
	public void updateDistances(int distance) {
		T.call(this);
		
		if(distance < minDistance) {
			minDistance = distance;
		}
		
		if(distance > maxDistance) {
			maxDistance = distance;
		}
	}

}
