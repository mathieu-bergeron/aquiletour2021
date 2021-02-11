package ca.ntro.core.task2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ca.ntro.core.services.NtroCollections;

public abstract class NtroTaskImpl implements NtroTask {

	private static Map<String, Integer> classIds = new HashMap<>();
	
	private String taskId;
	private NtroTask parentTask;
	
	private Map<String, NtroTask> previousTasks = NtroCollections.concurrentMap(new HashMap<>());
	private Map<String, NtroTask> subTasks = NtroCollections.concurrentMap(new HashMap<>());
	private Map<String, NtroTask> nextTasks = NtroCollections.concurrentMap(new HashMap<>());

	private Set<String> finishedPreviousTasks = NtroCollections.concurrentSet(new HashSet<>());
	private Set<String> finishedSubTasks = NtroCollections.concurrentSet(new HashSet<>());

	@Override 
	public int hashCode() {
		return taskId.hashCode();
	}

	@Override 
	public boolean equals(Object other) {
		if(this == other) return true;
		if(!(other instanceof NtroTask)) return false;
		return ((NtroTask)other).getId().equals(this.getId());
	}

	public NtroTaskImpl() {
		this.taskId = generateId();
	}
	
	private String generateId(){
		String myClassName = this.getClass().getSimpleName();

		int suffix = 1;

		if(classIds.containsKey(myClassName)) {
			suffix = classIds.get(myClassName) + 1;
		}
		
		classIds.put(myClassName, suffix);
		
		return myClassName + "$" + suffix;
	}

	public NtroTaskImpl(String taskId) {
		this.taskId = taskId;
	}
	
	@Override
	public String getId() {
		return taskId;
	}

	@Override
	public String getLabel() {
		return taskId;
	}

	@Override
	public void setId(String taskId) {
		this.taskId = taskId;
	}

	@Override
	public void setParentTask(NtroTask parentTask) {
		this.parentTask = parentTask;
	}

	@Override
	public NtroTask getParentTask() {
		return parentTask;
	}

	@Override
	public void addSubTask(NtroTask task) {
		task.setParentTask(this);
		subTasks.put(task.getId(), task);
	}

	@Override
	public void addSubTask(NtroTask task, String taskId) {
		task.setId(taskId);
		addSubTask(task);
	}

	@Override
	public synchronized void writeGraph(GraphWriter writer) {
		forEachTaskInGraph(t -> t.write(writer));
	}
	
	public void write(GraphWriter writer) {
		if(isRootNode()) {

			writer.addRootNode(this);

		}else if(isRootCluster()) {

			writer.addRootCluster(this);

		}else if(isSubNode()) {

			writer.addSubNode(getParentTask(), this);

		}else if(isSubCluster()){

			writer.addSubCluster(getParentTask(), this);
		}
	}

	@Override
	public boolean isSubCluster() {
		return hasParent() && hasSubTasks();
	}

	@Override
	public boolean isSubNode() {
		return hasParent() && !hasSubTasks();
	}

	@Override
	public boolean isRootCluster() {
		return isRoot() && hasSubTasks();
	}

	@Override
	public boolean isRootNode() {
		return isRoot() && !hasSubTasks();
	}
	
	public boolean isRoot() {
		return !hasParent();
	}

	@Override
	public boolean hasParent() {
		return parentTask != null;
	}
	
	private boolean hasPreviousTasks() {
		return previousTasks.size() > 0;
	}
	
	private boolean isStartNode() {
		return !hasPreviousTasks() && !hasParent();
	}
	

	private synchronized void forEachTaskInGraph(TaskLambda lambda) {
		searchForStartNodesAndIterateForward(lambda, new HashSet<>(), new HashSet<>());
	}

	private synchronized void forEachStartTaskInGraph(TaskLambda lambda) {
		// TODO
	}
	

	private synchronized void iterateGraphForward(TaskLambda lambda, 
										          Set<NtroTask> visitedNodes) {

		if(visitedNodes.contains(this)) return;
		visitedNodes.add(this);
		
		forEachSubTask(lambda);
		forEachNextTask(lambda);
	}

	private synchronized void searchForStartNodesAndIterateForward(TaskLambda lambda, 
			                                                       Set<NtroTask> visitedSearchNodes, 
			                                                       Set<NtroTask> visitedIterationNodes) {
		
		if(visitedSearchNodes.contains(this)) return;
		visitedSearchNodes.add(this);
		
		if(isStartNode()) {
			
			iterateGraphForward(lambda, visitedIterationNodes);

		}else {
			if(hasParent()) {
				((NtroTaskImpl) this).searchForStartNodesAndIterateForward(lambda, visitedSearchNodes, visitedIterationNodes);
			}

			forEachPreviousTask(pt -> ((NtroTaskImpl)pt).searchForStartNodesAndIterateForward(lambda, visitedSearchNodes, visitedIterationNodes));
		}
	}



	private void forEachSubTask(TaskLambda lambda) {
		synchronized (subTasks) {
			for(NtroTask subTask : subTasks.values()) {
				lambda.execute(subTask);
			}
		}
	}

	private void forEachPreviousTask(TaskLambda lambda) {
		synchronized(previousTasks) {
			for(NtroTask previousTask : previousTasks.values()) {
				lambda.execute(previousTask);
			}
		}
	}

	private void forEachNextTask(TaskLambda lambda) {
		synchronized(nextTasks) {
			for(NtroTask nextTask : nextTasks.values()) {
				lambda.execute(nextTask);
			}
		}
	}

	@Override
	public boolean hasSubTasks() {
		return subTasks.size() > 0;
	}

	@Override
	public void addNextTask(NtroTask task) {
		if(!haveNextTask(task)) {
			nextTasks.put(task.getId(), task);
			task.addPreviousTask(this);
		}
	}

	private boolean haveNextTask(NtroTask task) {
		return nextTasks.containsKey(task.getId());
	}

	@Override
	public void addNextTask(NtroTask task, String taskId) {
		task.setId(taskId);
		addNextTask(task);
	}

	@Override
	public void addPreviousTask(NtroTask task) {
		if(!havePreviousTask(task)) {
			previousTasks.put(task.getId(), task);
			task.addNextTask(this);
		}
	}

	private boolean havePreviousTask(NtroTask task) {
		return previousTasks.containsKey(task.getId());
	}

	@Override
	public void addPreviousTask(NtroTask task, String taskId) {
		task.setId(taskId);
		addPreviousTask(task);
	}

	@Override
	public void execute() {
		execute(new GraphTraceWriterNull());
	}


	@Override
	public void execute(GraphTraceWriter writer) {
		
	}
}
