package ca.aquiletour.core.pages.course.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ntro.core.Path;
import ca.ntro.core.models.NtroModel;

public class TaskBreadcrumbs implements NtroModel {
	
	/* The task breadcrumb is a tree where
	 * 
	 * - the trunk is the taskPath
	 * - branches are lists of sibling tasks
	 */
	
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

}
