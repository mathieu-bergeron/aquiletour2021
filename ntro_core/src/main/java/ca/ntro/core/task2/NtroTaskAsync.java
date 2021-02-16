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

public abstract class NtroTaskAsync implements NtroTask, TaskGraph, TaskGraphNode {

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
		
		asGraph().forEachNode(t -> t.asNode().writeNode(writer));

		asGraph().forEachEdge((from, to) -> writer.addEdge(from.asNode(), to.asNode()));
	}
	
	@Override
	public synchronized void forEachStartNode(TaskLambda lambda) {
		visitStartNodes(new HashSet<>(), lambda);
	}

	private synchronized void visitStartNodes(Set<NtroTask> visitedNodes, TaskLambda lambda) {
		if(visitedNodes.contains(this)) return;
		visitedNodes.add(this);
		
		if(isStartNode()) {
			
			lambda.execute(this);

		}else {

			if(parentTask != null) {
				((NtroTaskAsync) parentTask).visitStartNodes(visitedNodes, lambda);
			}

			forEachPreviousTask(pt -> ((NtroTaskAsync) pt).visitStartNodes(visitedNodes, lambda));
		}
	}

	@Override
	public synchronized void forEachNode(TaskLambda lambda) {
		
		Set<NtroTask> visitedNodes = new HashSet<>();

		forEachStartNode(sn -> ((NtroTaskAsync)sn).visitAllNodes(visitedNodes, lambda));
	}

	private synchronized void visitAllNodes(Set<NtroTask> visitedNodes, TaskLambda lambda) {
		if(visitedNodes.contains(this)) return;
		visitedNodes.add(this);
		
		lambda.execute(this);

		forEachSubTask(st -> ((NtroTaskAsync)st).visitAllNodes(visitedNodes, lambda));
		forEachNextTask(nt -> ((NtroTaskAsync)nt).visitAllNodes(visitedNodes, lambda));
	}
	
	@Override
	public synchronized void forEachEdge(EdgeLambda lambda) {

		Set<NtroTask> visitedNodes = new HashSet<>();

		forEachStartNode(sn -> ((NtroTaskAsync)sn).visitAllEdges(visitedNodes, lambda));
	}

	private synchronized void visitAllEdges(Set<NtroTask> visitedNodes, EdgeLambda lambda) {
		if(visitedNodes.contains(this)) return;
		visitedNodes.add(this);

		forEachSubTask(st -> ((NtroTaskAsync)st).visitAllEdges(visitedNodes, lambda));

		forEachNextTask(nt -> {
			lambda.execute(this, nt);
			((NtroTaskAsync)nt).visitAllEdges(visitedNodes, lambda);
		});
	}

	public void writeNode(GraphWriter writer) {
		if(isRootNode()) {

			writer.addRootNode(this.asNode());

		}else if(isRootCluster()) {

			writer.addRootCluster(this.asNode());

		}else if(isSubNode()) {

			writer.addSubNode(getParentTask().asNode(), this.asNode());

		}else if(isSubCluster()){

			writer.addSubCluster(getParentTask().asNode(), this.asNode());
		}
	}

	@Override
	public boolean isSubCluster() {
		return !isRoot() && isCluster();
	}

	@Override
	public boolean isCluster() {
		return hasSubTasks();
	}
	
	@Override
	public boolean isSubNode() {
		return !isRoot() && isNode();
	}

	@Override
	public boolean isNode() {
		return !hasSubTasks();
	}

	@Override
	public boolean isRootCluster() {
		return isRoot() && isCluster();
	}

	@Override
	public boolean isRootNode() {
		return isRoot() && isNode();
	}
	
	@Override
	public boolean isRoot() {
		return !hasParent();
	}

	private boolean hasParent() {
		return parentTask != null;
	}
	
	private boolean hasPreviousTasks() {
		return previousTasks.size() > 0;
	}

	@Override
	public boolean isStartNode() {
		return isRoot() && !hasPreviousTasks();
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
		// TODO
	}
	
	private void resumeExecution(GraphTraceWriter writer) {
		// TODO
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


	@Override
	public TaskGraph asGraph() {
		return this;
		
	}

	@Override
	public TaskGraphNode asNode() {
		return this;
	}

	@Override
	public NtroTask asTask() {
		return this;
	}
}
