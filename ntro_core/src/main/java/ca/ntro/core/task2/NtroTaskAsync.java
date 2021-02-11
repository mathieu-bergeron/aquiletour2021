package ca.ntro.core.task2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ca.ntro.core.services.NtroCollections;

import static ca.ntro.core.task2.State.INACTIVE;
import static ca.ntro.core.task2.State.WAITING_FOR_PREVIOUS_TASKS;
import static ca.ntro.core.task2.State.RUNNING_ENTRY_TASK;
import static ca.ntro.core.task2.State.WAITING_FOR_PREVIOUS_TASKS;
import static ca.ntro.core.task2.State.RUNNING_EXIT_TASK;
import static ca.ntro.core.task2.State.DONE;

public abstract class NtroTaskAsync implements NtroTask {

	private static Map<String, Integer> classIds = new HashMap<>();
	
	private String taskId;
	private NtroTask parentTask;
	
	private State state = INACTIVE;
	
	private Map<String, NtroTask> previousTasks = NtroCollections.concurrentMap(new HashMap<>());
	private Map<String, NtroTask> subTasks = NtroCollections.concurrentMap(new HashMap<>());
	private Map<String, NtroTask> nextTasks = NtroCollections.concurrentMap(new HashMap<>());

	private Set<String> finishedPreviousTasks = NtroCollections.concurrentSet(new HashSet<>());
	private Set<String> finishedSubTasks = NtroCollections.concurrentSet(new HashSet<>());
	
	protected abstract void runEntryTaskAsync();
	protected abstract void runExitTaskAsync();
	protected abstract void onSomePreviousTaskFinished(String taskId, NtroTask previousTask);
	protected abstract void onSomSubTaskFinished(String taskId, NtroTask subTask);
	protected abstract void onFailure(Exception e);

	public NtroTaskAsync() {
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

	public NtroTaskAsync(String taskId) {
		this.taskId = taskId;
	}
	
	@Override
	public String getId() {
		return taskId;
	}

	@Override
	public String getLabel() {
		return taskId + "\n" + state;
	}

	@Override
	public void setId(String taskId) {
		this.taskId = taskId;
	}

	@Override
	public void setParentTask(NtroTask parentTask) {
		this.parentTask = parentTask;
	}

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

		Set<NtroTask> visitedTasks = forEachTaskInGraph(t -> t.writeNode(writer));

		visitedTasks.forEach(t -> writeEdges(writer));
	}
	
	public void writeNode(GraphWriter writer) {
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

	private void writeEdges(GraphWriter writer) {
		forEachPreviousTask(pt -> writer.addEdge(pt, this));
	}

	private boolean isSubCluster() {
		return !isRoot() && isCluster();
	}

	private boolean isCluster() {
		return hasSubTasks();
	}
	private boolean isSubNode() {
		return !isRoot() && isNode();
	}

	private boolean isNode() {
		return !hasSubTasks();
	}

	private boolean isRootCluster() {
		return isRoot() && isCluster();
	}

	private boolean isRootNode() {
		return isRoot() && isNode();
	}
	
	private boolean isRoot() {
		return !hasParent();
	}

	private boolean hasParent() {
		return parentTask != null;
	}
	
	private boolean hasPreviousTasks() {
		return previousTasks.size() > 0;
	}
	
	private boolean isStartNode() {
		return isRoot() && !hasPreviousTasks();
	}
	

	private synchronized Set<NtroTask> forEachTaskInGraph(TaskLambda lambda) {
		return searchForStartNodesAndIterateForward(lambda, new HashSet<>(), new HashSet<>());
	}

	private synchronized void forEachStartTaskInGraph(TaskLambda lambda) {
		// TODO
	}
	

	private synchronized Set<NtroTask> iterateGraphForward(TaskLambda lambda, 
										          Set<NtroTask> visitedNodes) {

		if(visitedNodes.contains(this)) return visitedNodes;
		visitedNodes.add(this);
		
		lambda.execute(this);

		forEachSubTask(st -> ((NtroTaskAsync) st).iterateGraphForward(lambda, visitedNodes));
		forEachNextTask(nt -> ((NtroTaskAsync) nt).iterateGraphForward(lambda, visitedNodes));
		
		return visitedNodes;
	}

	private synchronized Set<NtroTask> searchForStartNodesAndIterateForward(TaskLambda lambda, 
			                                                       Set<NtroTask> visitedSearchNodes, 
			                                                       Set<NtroTask> visitedIterationNodes) {
		
		if(visitedSearchNodes.contains(this)) return visitedIterationNodes;
		visitedSearchNodes.add(this);
		
		if(isStartNode()) {
			
			iterateGraphForward(lambda, visitedIterationNodes);

		}else {
			if(hasParent()) {
				((NtroTaskAsync) this).searchForStartNodesAndIterateForward(lambda, visitedSearchNodes, visitedIterationNodes);
			}

			forEachPreviousTask(pt -> ((NtroTaskAsync)pt).searchForStartNodesAndIterateForward(lambda, visitedSearchNodes, visitedIterationNodes));
		}
		
		return visitedIterationNodes;
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
		forEachStartTaskInGraph(t -> ((NtroTaskAsync)t).resumeExecution(writer));
	}
	
	private void resumeExecution(GraphTraceWriter writer) {
		
		
	}
	
	@Override
	public void notifyEntryTaskFinished() {
		
	}

	@Override
	public void notifyExitTaskFinished() {
		
	}

	@Override
	public void notifySomePreviousTaskFinished(NtroTask finishedTask) {
		
	}

	@Override
	public void notifySomeSubTaskFinished(NtroTask finishedTask) {
		
	}
	
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
}
