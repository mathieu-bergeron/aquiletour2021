package ca.ntro.core.task2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import ca.ntro.core.services.NtroCollections;
import ca.ntro.core.system.log.Log;

import static ca.ntro.core.task2.TaskState.INIT;
import static ca.ntro.core.task2.TaskState.DELEGATING_TO_PARENT;
import static ca.ntro.core.task2.TaskState.WAITING_FOR_PREVIOUS_TASKS;
import static ca.ntro.core.task2.TaskState.RUNNING_ENTRY_TASK;
import static ca.ntro.core.task2.TaskState.WAITING_FOR_SUB_TASKS;
import static ca.ntro.core.task2.TaskState.RUNNING_EXIT_TASK;
import static ca.ntro.core.task2.TaskState.DONE;
import static ca.ntro.core.task2.TaskState.DELETED;

public abstract class TaskAsync implements NtroTask, TaskGraph, Node {

	private static Map<String, Integer> classIds = new HashMap<>();
	
	private String taskId;
	private TaskAsync parentTask;
	
	private TaskState state = INIT;
	private GraphTraceImpl trace;
	
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

	public TaskAsync() {
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

	public TaskAsync(String taskId) {
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
		this.parentTask = (TaskAsync) parentTask;
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
	public synchronized void forEachStartNode(NodeLambda lambda) {
		visitStartNodes(new HashSet<>(), lambda);
	}

	private synchronized void visitStartNodes(Set<NtroTask> visitedNodes, NodeLambda lambda) {
		if(visitedNodes.contains(this)) return;
		visitedNodes.add(this);
		
		if(isStartNode()) {
			
			lambda.execute(this);

		}else {

			if(parentTask != null) {
				((TaskAsync) parentTask).visitStartNodes(visitedNodes, lambda);
			}

			forEachPreviousTask(pt -> ((TaskAsync) pt).visitStartNodes(visitedNodes, lambda));
		}
	}

	@Override
	public synchronized void forEachNode(NodeLambda lambda) {
		
		Set<NtroTask> visitedNodes = new HashSet<>();

		forEachStartNode(sn -> ((TaskAsync)sn).visitAllNodes(visitedNodes, lambda));
	}

	@Override
	public synchronized void forEachSubNode(NodeLambda lambda) {
		forEachSubTask(st -> lambda.execute(st.asNode()));
	}

	@Override
	public synchronized void forEachSubNodeTransitive(NodeLambda lambda) {
		Set<Node> visitedNodes = new HashSet<>();

		forEachSubTask(st -> ((TaskAsync) st).visitSubNodes(visitedNodes, lambda));
	}
	
	private synchronized void visitSubNodes(Set<Node> visitedNodes, NodeLambda lambda) {
		if(visitedNodes.contains(this)) return;
		visitedNodes.add(this);
		
		lambda.execute(this);

		forEachSubTask(st -> ((TaskAsync) st).visitSubNodes(visitedNodes, lambda));
	}

	@Override
	public synchronized void forEachNextNode(NodeLambda lambda) {
	}

	@Override
	public synchronized void forEachNextNodeTransitive(NodeLambda lambda) {
	}

	@Override
	public synchronized void forEachReachableNode(NodeLambda lambda) {
	}

	@Override
	public synchronized void forEachReachableNodeTransitive(NodeLambda lambda) {
	}
	
	@Override 
	public Node getParentNode() {
		if(parentTask != null) return parentTask.asNode();
		return null;
	}

	private synchronized void visitAllNodes(Set<NtroTask> visitedNodes, NodeLambda lambda) {
		if(visitedNodes.contains(this)) return;
		visitedNodes.add(this);
		
		lambda.execute(this);

		forEachSubTask(st -> ((TaskAsync)st).visitAllNodes(visitedNodes, lambda));
		forEachNextTask(nt -> ((TaskAsync)nt).visitAllNodes(visitedNodes, lambda));
	}
	
	@Override
	public synchronized void forEachEdge(EdgeLambda lambda) {

		Set<NtroTask> visitedNodes = new HashSet<>();

		forEachStartNode(sn -> ((TaskAsync)sn).visitAllEdges(visitedNodes, lambda));
	}

	private synchronized void visitAllEdges(Set<NtroTask> visitedNodes, EdgeLambda lambda) {
		if(visitedNodes.contains(this)) return;
		visitedNodes.add(this);

		forEachSubTask(st -> ((TaskAsync)st).visitAllEdges(visitedNodes, lambda));

		forEachNextTask(nt -> {
			lambda.execute(this.asNode(), nt.asNode());
			((TaskAsync)nt).visitAllEdges(visitedNodes, lambda);
		});
	}

	public void writeNode(GraphWriter writer) {
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
	public TaskGraph asGraph() {
		return this;
		
	}

	@Override
	public Node asNode() {
		return this;
	}

	@Override
	public NtroTask asTask() {
		return this;
	}
	
	
	@Override
	public int hashCode() {
		Set<NtroTask> subTasks = new HashSet<>();
		forEachSubTask(st -> subTasks.add(st));

		return getId().hashCode() + Objects.hash(subTasks.toArray());
	}

	@Override
	public boolean equals(Object otherObject) {
		if(this == otherObject) return true;
		if(otherObject == null) return false;

		if(otherObject instanceof TaskAsync) {
			TaskAsync otherTask = (TaskAsync) otherObject;
			
			if(!getId().equals(otherTask.getId())) return false;
			
			Set<NtroTask> mySubTasks = new HashSet<>();
			forEachSubTask(st -> mySubTasks.add(st));
			
			Set<NtroTask> otherSubTasks = new HashSet<>();
			otherTask.forEachSubTask(st -> otherSubTasks.add(st));
			
			return mySubTasks.equals(otherSubTasks);
		}
		
		return false;
	}
	
	private class Edge{
		public Node from;
		public Node to;
		public Edge(Node from, Node to) {
			this.from = from;
			this.to = to;
		}
		@Override
		public int hashCode() {
			return Objects.hash(from, to);
		}
		@Override
		public boolean equals(Object otherObject) {
			if(otherObject == null) return false;
			if(this == otherObject) return true;
			if(otherObject instanceof Edge) {
				Edge otherEdge = (Edge) otherObject;
				return from.equals(otherEdge.from) && to.equals(otherEdge.to);
			}
			return false;
		}
	}

	@Override
	public boolean isSameGraphAs(TaskGraph otherGraph) {
		
		Set<Node> myNodes = new HashSet<>();
		asGraph().forEachNode(n -> myNodes.add(n));

		Set<Node> otherNodes = new HashSet<>();
		otherGraph.forEachNode(n -> otherNodes.add(n));
		
		if(!myNodes.equals(otherNodes)) return false;
		
		Set<Edge> myEdges = new HashSet<>();
		asGraph().forEachEdge((from, to) -> myEdges.add(new Edge(from, to)));

		Set<Edge> otherEdges = new HashSet<>();
		otherGraph.forEachEdge((from, to) -> otherEdges.add(new Edge(from, to)));

		return myEdges.equals(otherEdges);
	}

	@Override
	public synchronized NodeDescription getNodeDescription() {
		return new NodeDescriptionImpl(getId(), 
				                       getLabel(),
				                       isRoot(),
				                       isCluster(),
				                       isStartNode(),
				                       parentTask != null ? parentTask.asNode() : null);
	}

	@Override
	public synchronized GraphDescription getGraphDescription() {
		GraphDescriptionImpl description = new GraphDescriptionImpl();
		
		asGraph().forEachNode(n -> description.addNode(n.getNodeDescription()));

		asGraph().forEachEdge((from, to) -> description.addEdge(from.getNodeDescription(), to.getNodeDescription()));
		
		return description;
	}
	
	private TaskStateDescription getTaskStateDescription() {
		return new TaskStateDescriptionImpl(getId(), state);
	}
	
	private void appendCurrentStateToTrace(GraphTrace trace) {
		trace.append(getGraphDescription(), getTaskStateDescription());
	}

	@Override
	public GraphTraceConnector execute() {

		GraphTraceImpl trace = new GraphTraceImpl();
		
		appendCurrentStateToTrace(trace);

		execute(trace);
		
		return trace;
	}
	
	private void execute(GraphTraceImpl trace) {
		
		this.trace = trace;
		
		resumeExecution();
	}

	
	private void resumeExecution() {
		
		switch(state) {

			case INIT:
				launchExecution();
			break;

			case DELEGATING_TO_PARENT:
				launchPreviousTasks();
			break;

			case WAITING_FOR_PREVIOUS_TASKS:
				if(haveFinishedPreviousTasks()) {
					changeState(RUNNING_ENTRY_TASK);
					resumeExecution();
				}
			break;

			case RUNNING_ENTRY_TASK:
				runEntryTaskAsync();
			break;

			case WAITING_FOR_SUB_TASKS:
				if(haveFinishedSubTasks()) {
					changeState(RUNNING_EXIT_TASK);
					resumeExecution();
				}
			break;

			case RUNNING_EXIT_TASK:
				runExitTaskAsync();
			break;

			case DONE:
			break;

			case DELETED:
			break;
			
			default:
				Log.fatalError("NtroTaskAsync.resumeExecution should not get here");
			break;
		}
	}
	
	private void changeState(TaskState newState) {
		if(state != newState) {
			state = newState;
			appendCurrentStateToTrace(trace);
		}
	}

	private void launchExecution() {
		if(parentTask != null && parentTask.state == INIT) {

			changeState(DELEGATING_TO_PARENT);
			parentTask.execute(trace);

		}else {

			launchPreviousTasks();

		}
	}
	
	private void launchPreviousTasks() {
		if(!hasPreviousTasks() || haveFinishedPreviousTasks()) {

			changeState(RUNNING_ENTRY_TASK);
			resumeExecution();
			
		}else {

			changeState(WAITING_FOR_PREVIOUS_TASKS);
			forEachPreviousTask(pt -> ((TaskAsync)pt).execute(trace));

		}
	}
	
	private boolean haveFinishedPreviousTasks() {
		return finishedPreviousTasks.size() == previousTasks.size();
	}

	private boolean haveFinishedSubTasks() {
		return finishedSubTasks.size() == subTasks.size();
	}

	private void launchSubTasks() {
		if(!hasSubTasks() || haveFinishedSubTasks()) {

			changeState(RUNNING_EXIT_TASK);
			resumeExecution();
			
		}else {

			changeState(WAITING_FOR_SUB_TASKS);
			forEachSubTask(pt -> ((TaskAsync)pt).execute(trace));

		}
	}
	
	@Override
	public void notifyEntryTaskFinished() {
		if(state == RUNNING_ENTRY_TASK) {
			launchSubTasks();
		}
	}

	@Override
	public void notifyExitTaskFinished() {
		if(state == RUNNING_EXIT_TASK) {
			changeState(TaskState.DONE);
			notifyTaskFinished();
			launchNextTasks();
		}
	}

	private void notifyTaskFinished() {
		if(parentTask != null) {
			parentTask.notifySomeSubTaskFinished(this);
			parentTask.execute(trace);
		}

		forEachNextTask(nt -> ((TaskAsync)nt).notifySomePreviousTaskFinished(this));
	}

	private void launchNextTasks() {
		forEachNextTask(nt -> ((TaskAsync)nt).execute(trace));
	}

	void notifySomePreviousTaskFinished(NtroTask finishedTask) {
		finishedPreviousTasks.add(finishedTask.getId());
		onSomePreviousTaskFinished(finishedTask.getId(), finishedTask);
	}

	void notifySomeSubTaskFinished(NtroTask finishedTask) {
		finishedSubTasks.add(finishedTask.getId());
		onSomSubTaskFinished(finishedTask.getId(), finishedTask);
	}
}
