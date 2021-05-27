package ca.aquiletour.core.models.courses.base.lambdas;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import ca.aquiletour.core.models.courses.base.Task;
import ca.ntro.core.models.lambdas.Break;
import ca.ntro.core.system.trace.T;

public class FindResults  {
	
	private List<FindResult> results = new ArrayList<>();

	public List<FindResult> getResults() {
		return results;
	}

	public void setResults(List<FindResult> results) {
		this.results = results;
	}

	public void addOrUpdateFindResult(int distance, Task task) {
		T.call(this);
		
		FindResult result = resultByTaskId(task);

		if(result == null) {
			
			result = new FindResult(task, distance, distance);
			getResults().add(result);

		}else {
			
			result.updateDistances(distance);
		}
	}

	public void addOrUpdateFindResult(FindResult resultToAdd) {
		T.call(this);
		
		FindResult result = resultByTaskId(resultToAdd.getTask());
		
		if(result == null) {
			
			getResults().add(resultToAdd);
			
		}else {
			
			result.updateDistances(resultToAdd);
		}
	}
	
	private FindResult resultByTaskId(Task task) {
		T.call(this);
		
		FindResult result = null;
		
		if(task != null && task.id() != null) {

			for(FindResult candidate : getResults()) {
				
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
		
		for(FindResult candidate : getResults()) {
			if(result == null) {
				result = candidate;
			}else if(candidate.getMinDistance() < result.getMinDistance()){
				result = candidate;
			}
		}
		
		return result;
	}

	public void forEachTask(TaskForEach lambda) {
		T.call(this);
		
		for(FindResult result : getResults()) {
			try {

				lambda.execute(result.getTask());

			}catch(Break b) {
				break;
			}
		}
	}

	public int size() {
		T.call(this);
		
		return getResults().size();
	}

	public FindResult get(int i) {
		T.call(this);
		
		return getResults().get(i);
		
	}

	public void sort(Comparator<? super FindResult> comparator) {
		T.call(this);

		getResults().sort(comparator);
	}

}
