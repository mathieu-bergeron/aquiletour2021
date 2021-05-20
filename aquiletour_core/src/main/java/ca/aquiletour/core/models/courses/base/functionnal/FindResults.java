package ca.aquiletour.core.models.courses.base.functionnal;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.models.courses.base.Task;
import ca.ntro.core.system.trace.T;

public class FindResults extends ArrayList<FindResult> {
	private static final long serialVersionUID = -5465197643346101186L;

	public void addOrUpdateFindResult(int distance, Task task) {
		T.call(this);
		
		FindResult result = resultByTaskId(task);
		
		if(result == null) {
			
			result = new FindResult(task, distance, distance);
			add(result);

		}else {
			
			result.updateDistances(distance);
		}
	}
	
	private FindResult resultByTaskId(Task task) {
		T.call(this);
		
		FindResult result = null;
		
		if(task != null) {
			for(FindResult candidate : asList()) {
				if(candidate.getTask().id().equals(task.id())) {
					candidate = result;
					break;
				}
			}
		}

		return result;
	}

	public FindResult closest() {
		T.call(this);
		
		FindResult result = null;
		
		for(FindResult candidate : asList()) {
			if(result == null) {
				result = candidate;
			}else if(candidate.getMinDistance() < result.getMinDistance()){
				result = candidate;
			}
		}
		
		return result;
	}

	// JSWEET: (FindResult candidate: this) does not compile
	private List<FindResult> asList(){
		return (List<FindResult>) this;
	}
}
