package ca.ntro.core.task2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import ca.ntro.core.services.NtroCollections;
import ca.ntro.core.system.log.Log;

import static ca.ntro.core.task2.State.BEFORE_EXECUTION;
import static ca.ntro.core.task2.State.WAITING_FOR_PREVIOUS_TASKS;
import static ca.ntro.core.task2.State.RUNNING_ENTRY_TASK;
import static ca.ntro.core.task2.State.WAITING_FOR_PREVIOUS_TASKS;
import static ca.ntro.core.task2.State.RUNNING_EXIT_TASK;
import static ca.ntro.core.task2.State.AFTER_EXECUTION;
import static ca.ntro.core.task2.State.REMOVED_FROM_GRAPH;

public abstract class NtroTaskAsync implements NtroTask, TaskGraph, Node {

	private static Map<String, Integer> classIds = new HashMap<>();
	
	private String taskId;
	private NtroTask parentTask;
	
	private State state = BEFORE_EXECUTION;
	
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
				((NtroTaskAsync) parentTask).visitStartNodes(visitedNodes, lambda);
			}

			forEachPreviousTask(pt -> ((NtroTaskAsync) pt).visitStartNodes(visitedNodes, lambda));
		}
	}

	@Override
	public synchronized void forEachNode(NodeLambda lambda) {
		
		Set<NtroTask> visitedNodes = new HashSet<>();

		forEachStartNode(sn -> ((NtroTaskAsync)sn).visitAllNodes(visitedNodes, lambda));
	}

	@Override
	public synchronized void forEachSubNode(NodeLambda lambda) {
		forEachSubTask(st -> lambda.execute(st.asNode()));
	}

	@Override
	public synchronized void forEachSubNodeTransitive(NodeLambda lambda) {
		Set<Node> visitedNodes = new HashSet<>();

		forEachSubTask(st -> ((NtroTaskAsync) st).visitSubNodes(visitedNodes, lambda));
	}
	
	private synchronized void visitSubNodes(Set<Node> visitedNodes, NodeLambda lambda) {
		if(visitedNodes.contains(this)) return;
		visitedNodes.add(this);
		
		lambda.execute(this);

		forEachSubTask(st -> ((NtroTaskAsync) st).visitSubNodes(visitedNodes, lambda));
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
			lambda.execute(this.asNode(), nt.asNode());
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

		if(otherObject instanceof NtroTaskAsync) {
			NtroTaskAsync otherTask = (NtroTaskAsync) otherObject;
			
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
	public synchronized GraphDescription getGraphDescription() {
		GraphDescriptionImpl description = new GraphDescriptionImpl();
		
		asGraph().forEachNode(n -> description.addNode(n));

		asGraph().forEachEdge((from, to) -> description.addEdge(from, to));
		
		return description;
	}

	@Override
	public GraphTraceConnector execute() {
		GraphTraceImpl trace = new GraphTraceImpl();
		
		asGraph().forEachStartNode(n -> {
			((NtroTaskAsync) n).executeForward(trace);
		});
		
		return trace;
	}

	
	private void executeForward(GraphTrace trace) {
		switch(state) {

			case BEFORE_EXECUTION:
				launchPreviousTasks(trace);
			break;

			case WAITING_FOR_PREVIOUS_TASKS:
			break;

			case RUNNING_ENTRY_TASK:
			break;

			case WAITING_FOR_SUB_TASKS:
			break;

			case RUNNING_EXIT_TASK:
			break;

			case AFTER_EXECUTION:
			break;

			case REMOVED_FROM_GRAPH:
			break;
			
			default:
				Log.fatalError("resumeExecution should not get here");
			break;
		}
	}
	
	private void changeState(State newState, GraphTrace trace) {
		if(state != newState) {
			state = newState;
			trace.appendGraph(getGraphDescription());
		}
	}
	
	private void launchPreviousTasks(GraphTrace trace) {
		if(!hasPreviousTasks() || haveFinishedPreviousTasks()) {

			changeState(RUNNING_ENTRY_TASK, trace);
			
		}else {

			forEachPreviousTask(pt -> ((NtroTaskAsync)pt).executeForward(trace));

		}
	}
	
	private boolean haveFinishedPreviousTasks() {
		return finishedPreviousTasks.size() == previousTasks.size();
	}
	
	@Override
	public void notifyEntryTaskFinished() {
		
	}

	@Override
	public void notifyExitTaskFinished() {
		
	}

	void notifySomePreviousTaskFinished(NtroTask finishedTask) {
		
	}

	void notifySomeSubTaskFinished(NtroTask finishedTask) {
		
	}
}
