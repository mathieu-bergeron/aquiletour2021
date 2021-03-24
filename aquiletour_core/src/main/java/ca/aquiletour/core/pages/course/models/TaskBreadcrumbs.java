package ca.aquiletour.core.pages.course.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ntro.core.Path;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;

public class TaskBreadcrumbs implements NtroModel {
	
	private Path trunk;
	private Map<String, List<Task>> branches = new HashMap<>();
	
	public Path getTrunk() {
		return trunk;
	}

	public void setTrunk(Path trunk) {
		this.trunk = trunk;
	}

	public Map<String, List<Task>> getBranches() {
		return branches;
	}

	public void setBranches(Map<String, List<Task>> branches) {
		this.branches = branches;
	}

	public void addBranches(TaskGraph graph) {
		T.call(this);
		
		Task cursor = graph.findTaskByPath(trunk);

		while(cursor != null) {

			cursor.forEachSubTask(st -> {

				if(!st.getPath().isPrefixOf(trunk)) {

					addBranch(st);
				}
			});

			cursor = cursor.parent();
		}
	}
	
	private void addBranch(Task sibling) {
		T.call(this);
		
		String parentId = sibling.parent().getPath().toString();
		
		List<Task> siblings = branches.get(parentId);

		if(siblings == null) {
			siblings = new ArrayList<>();
			branches.put(parentId, siblings);
		}

		siblings.add(sibling);
	}
}
