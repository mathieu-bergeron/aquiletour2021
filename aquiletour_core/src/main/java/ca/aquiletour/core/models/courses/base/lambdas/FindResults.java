package ca.aquiletour.core.models.courses.base.lambdas;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.models.courses.base.Task;
import ca.ntro.core.models.lambdas.Break;
import ca.ntro.core.system.trace.T;

public class FindResults extends ArrayList<FindResult> {
	private static final long serialVersionUID = -5465197643346101186L;

	public void addOrUpdateFindResult(int distance, Task task) {
		T.call(this);
		
		FindResult result = resultByTaskId(task);

		if(result == null) {
			
			result = new FindResult(task, distance, distance);
			asList().add(result);

		}else {
			
			result.updateDistances(distance);
		}
	}

	public void addOrUpdateFindResult(FindResult resultToAdd) {
		T.call(this);
		
		FindResult result = resultByTaskId(resultToAdd.getTask());
		
		if(result == null) {
			
			asList().add(resultToAdd);
			
		}else {
			
			result.updateDistances(resultToAdd);
		}
	}
	
	private FindResult resultByTaskId(Task task) {
		T.call(this);
		
		FindResult result = null;
		
		if(task != null && task.id() != null) {

			for(FindResult candidate : asList()) {
				
				if(candidate.getTask() != null && 
						task.id().equals(candidate.getTask().id())) {

					result = candidate;
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
	public List<FindResult> asList(){
		return (List<FindResult>) this;
	}

	public void forEachTask(TaskForEach lambda) {
		T.call(this);
		
		for(FindResult result : asList()) {
			try {

				lambda.execute(result.getTask());

			}catch(Break b) {
				break;
			}
		}
	}

}
