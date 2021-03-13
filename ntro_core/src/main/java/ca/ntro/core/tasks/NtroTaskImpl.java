package ca.ntro.core.tasks;

import static ca.ntro.core.tasks.TaskState.INIT;
import static ca.ntro.core.tasks.TaskState.WAITING_FOR_PREVIOUS_TASKS;
import static ca.ntro.core.tasks.TaskState.LAUNCHING_ENTRY_TASK;
import static ca.ntro.core.tasks.TaskState.WAITING_FOR_ENTRY_TASK;
import static ca.ntro.core.tasks.TaskState.WAITING_FOR_SUB_TASKS;
import static ca.ntro.core.tasks.TaskState.LAUNCHING_EXIT_TASK;
import static ca.ntro.core.tasks.TaskState.WAITING_FOR_EXIT_TASK;
import static ca.ntro.core.tasks.TaskState.DONE;
import static ca.ntro.core.tasks.TaskState.DELETED;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import ca.ntro.core.system.log.Log;
import ca.ntro.services.NtroCollections;

public abstract class NtroTaskImpl implements NtroTask, TaskGraph, Node {

	private String taskId;
	private NtroTaskImpl parentTask;
	
	private TaskState state = INIT;
	private GraphTraceImpl trace;
	
	private Map<String, NtroTask> previousTasks = NtroCollections.concurrentMap(new HashMap<>());
	private Map<String, NtroTask> subTasks = NtroCollections.concurrentMap(new HashMap<>());
	private Map<String, NtroTask> nextTasks = NtroCollections.concurrentMap(new HashMap<>());

	protected abstract void runEntryTaskAsync();
	protected abstract void runExitTaskAsync();
	protected abstract void onSomePreviousTaskFinished(String taskId, NtroTask previousTask);
	protected abstract void onSomeSubTaskFinished(String taskId, NtroTask subTask);
	protected abstract void onFailure(Exception e);

	public NtroTaskImpl() {
		taskId = this.getClass().getSimpleName();
	}

	public NtroTaskImpl(String taskId) {
		this.taskId = taskId;
	}
	
	@Override
	public String getTaskId() {
		return taskId;
	}

	@Override
	public String getLabel() {
		// TMP
		/*
		String state = "WAIT";
		if(this.state == DONE) {
			state = this.state.name();
		}*/

		return taskId + "\n" + state;
	}

	@Override
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	@Override
	public void setParentTask(NtroTask parentTask) {
		this.parentTask = (NtroTaskImpl) parentTask;
	}

	public NtroTask getParentTask() {
		return parentTask;
	}

	@Override
	public NtroTask addSubTask(NtroTask task) {

		task.setParentTask(this);

		forEachNode(n -> {
			if(n.getNodeId().equals(task.asNode().getNodeId())){
				throw new IllegalArgumentException("Adding task " + task.getTaskId() + " would result in non-unique nodeId " + n.getNodeId());
			}
		});
		
		if(subTasks.containsKey(task.getTaskId())) {
			throw new IllegalArgumentException("Adding task " + task.getTaskId() + " would result in non-unique subTask");
		}
		
		subTasks.put(task.getTaskId(), task);
		
		return this;
	}

	@Override
	public NtroTask addSubTask(NtroTask task, String taskId) {
		task.setTaskId(taskId);
		addSubTask(task);
		
		return this;
	}

	@SuppressWarnings("unchecked")
	public <N extends NtroTask> N getPreviousTask(Class<N> taskClass, String taskId) {
		N previousTask = (N) previousTasks.get(taskId);

		return (N) previousTask;
	}

	@SuppressWarnings("unchecked")
	public <N extends NtroTask> N getSubTask(Class<N> taskClass, String taskId) {
		N subTask = (N) subTasks.get(taskId);

		return (N) subTask;
	}

	
	@Override
	public synchronized void forEachStartNode(NodeLambda lambda) {
		forEachNode(n -> {
			if(n.isStartNode()) {
				lambda.execute(n);
			}
		});
	}

	private synchronized void visitAllNodes(Set<String> visitedNodes, NodeLambda lambda) {
		if(visitedNodes.contains(getNodeId())) return;
		visitedNodes.add(getNodeId());
		
		lambda.execute(this);

		if(parentTask != null) {
			((NtroTaskImpl) parentTask).visitAllNodes(visitedNodes, lambda);
		}

		forEachPreviousTask(pt -> ((NtroTaskImpl) pt).visitAllNodes(visitedNodes, lambda));
		forEachSubTask(st -> ((NtroTaskImpl) st).visitAllNodes(visitedNodes, lambda));
		forEachNextTask(nt -> ((NtroTaskImpl) nt).visitAllNodes(visitedNodes, lambda));
	}

	@Override
	public synchronized void forEachNode(NodeLambda lambda) {
		Set<String> visitedNodes = new HashSet<>();

		visitAllNodes(visitedNodes, lambda);
	}

	@Override
	public synchronized void forEachSubNode(NodeLambda lambda) {
		forEachSubTask(st -> lambda.execute(st.asNode()));
	}

	@Override
	public synchronized void forEachSubNodeTransitive(NodeLambda lambda) {
		Set<String> visitedNodes = new HashSet<>();

		forEachSubTask(st -> ((NtroTaskImpl) st).visitSubNodes(visitedNodes, lambda));
	}
	
	private synchronized void visitSubNodes(Set<String> visitedNodes, NodeLambda lambda) {
		if(visitedNodes.contains(getNodeId())) return;
		visitedNodes.add(getNodeId());
		
		lambda.execute(this);

		forEachSubTask(st -> ((NtroTaskImpl) st).visitSubNodes(visitedNodes, lambda));
	}

	@Override
	public synchronized void forEachNextNode(NodeLambda lambda) {
		forEachNextTask(nt -> lambda.execute(nt.asNode()));
	}

	@Override
	public synchronized void forEachNextNodeTransitive(NodeLambda lambda) {
		Set<String> visitedNodes = new HashSet<>();

		forEachNextTask(st -> ((NtroTaskImpl) st).visitNextNodes(visitedNodes, lambda));
	}

	private synchronized void visitNextNodes(Set<String> visitedNodes, NodeLambda lambda) {
		if(visitedNodes.contains(getNodeId())) return;
		visitedNodes.add(getNodeId());
		
		lambda.execute(this);

		forEachNextTask(st -> ((NtroTaskImpl) st).visitNextNodes(visitedNodes, lambda));
	}

	@Override
	public synchronized void forEachReachableNode(NodeLambda lambda) {
		forEachNextNode(lambda);
		forEachSubNode(lambda);
	}

	@Override
	public synchronized void forEachReachableNodeTransitive(NodeLambda lambda) {
		Set<String> visitedNodes = new HashSet<>();

		forEachSubTask(st -> ((NtroTaskImpl) st).visitReachableNodes(visitedNodes, lambda));
		forEachNextTask(nt -> ((NtroTaskImpl) nt).visitReachableNodes(visitedNodes, lambda));
	}
	
	@Override 
	public Node getParentNode() {
		if(parentTask != null) return parentTask.asNode();
		return null;
	}

	private synchronized void visitReachableNodes(Set<String> visitedNodes, NodeLambda lambda) {
		if(visitedNodes.contains(getNodeId())) return;
		visitedNodes.add(getNodeId());
		
		lambda.execute(this);

		forEachSubTask(st -> ((NtroTaskImpl)st).visitReachableNodes(visitedNodes, lambda));
		forEachNextTask(nt -> ((NtroTaskImpl)nt).visitReachableNodes(visitedNodes, lambda));
	}
	
	@Override
	public synchronized void forEachEdge(EdgeLambda lambda) {
		forEachNode(n -> {
			n.forEachNextNode(nn -> {
				lambda.execute(n, nn);
			});
		});
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
	public NtroTask addNextTask(NtroTask task) {
		if(!haveNextTask(task)) {
			nextTasks.put(task.getTaskId(), task);
			task.addPreviousTask(this);
		}
		
		return this;
	}

	private boolean haveNextTask(NtroTask task) {
		return nextTasks.containsKey(task.getTaskId());
	}

	@Override
	public NtroTask addNextTask(NtroTask task, String taskId) {
		task.setTaskId(taskId);
		addNextTask(task);
		
		return this;
	}

	@Override
	public NtroTask addPreviousTask(NtroTask task) {
		if(!havePreviousTask(task)) {
			previousTasks.put(task.getTaskId(), task);
			task.addNextTask(this);
		}
		
		return this;
	}

	private boolean havePreviousTask(NtroTask task) {
		return previousTasks.containsKey(task.getTaskId());
	}

	@Override
	public NtroTask addPreviousTask(NtroTask task, String taskId) {
		task.setTaskId(taskId);
		addPreviousTask(task);
		
		return this;
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

		return getTaskId().hashCode() + Objects.hash(subTasks.toArray());
	}

	@Override
	public boolean equals(Object otherObject) {
		if(this == otherObject) return true;
		if(otherObject == null) return false;

		if(otherObject instanceof NtroTaskImpl) {
			NtroTaskImpl otherTask = (NtroTaskImpl) otherObject;
			
			if(!getTaskId().equals(otherTask.getTaskId())) return false;
			
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
	public String getNodeId() {
		
		String nodeId;
		
		if(parentTask != null) {

			nodeId = parentTask.getNodeId() + "_" + getTaskId();

		}else {
			
			nodeId = getTaskId();
			
		}
		
		return nodeId;
	}

	@Override
	public synchronized NodeDescription getNodeDescription() {

		return new NodeDescriptionImpl(getNodeId(), 
				                       getLabel(),
				                       isRoot(),
				                       isCluster(),
				                       isStartNode(),
				                       parentTask != null ? parentTask.asNode().getNodeDescription() : null);
	}

	@Override
	public synchronized GraphDescription getGraphDescription() {
		GraphDescriptionImpl description = new GraphDescriptionImpl();
		
		asGraph().forEachNode(n -> description.addNode(n.getNodeDescription()));

		asGraph().forEachEdge((from, to) -> description.addEdge(from.getNodeDescription(), to.getNodeDescription()));
		
		return description;
	}
	
	private TaskStateDescription getTaskStateDescription() {
		return new TaskStateDescriptionImpl(getNodeId(), state);
	}
	
	private boolean appendCurrentStateToTrace(GraphTrace trace, NtroTaskImpl task) {
		return trace.append(getGraphDescription(), task.getTaskStateDescription());
	}

	@Override
	public GraphTraceConnector execute() {

		if(trace == null) {
			trace = new GraphTraceImpl();
		}
		
		execute(trace);
		
		return trace;
	}
	
	private void execute(GraphTraceImpl trace) {

		appendCurrentStateToTrace(trace, this);
		
		Map<String, NtroTask> allTasks = new HashMap<>();
		forEachNode(n -> allTasks.put(n.getNodeId(),n.asTask()));

		boolean ifShouldContinueExecution;
		
		do {

			ifShouldContinueExecution = false;

			for(NtroTask task : allTasks.values()) {
				
				TaskState oldState = ((NtroTaskImpl)task).state;
				
				((NtroTaskImpl)task).resumeExecutionIfPossible(trace);

				TaskState newState = ((NtroTaskImpl)task).state;
				
				boolean ifTaskChangedState = (oldState != newState);

				if(ifTaskChangedState) {
					
					boolean cycleDetected = appendCurrentStateToTrace(trace, (NtroTaskImpl)task);
					if(cycleDetected) {
						Log.warning("Cycle detected in task graph at node " + getNodeId());
					}
					
					ifShouldContinueExecution = true;
				}
			}
			
		} while(ifShouldContinueExecution);
	}

	
	private void resumeExecutionIfPossible(GraphTraceImpl trace) {
		this.trace = trace;
		
		switch(state) {

			case INIT:
				if(isStartNode()) {
					
					state = LAUNCHING_ENTRY_TASK;

				}else if(allNextTasksAreWaitingForPreviousTasks()
						&& isRoot()) {
					
						state = WAITING_FOR_PREVIOUS_TASKS;

				}else if(allNextTasksAreWaitingForPreviousTasks()
						&& parentTask.state == WAITING_FOR_SUB_TASKS) {

						state = WAITING_FOR_PREVIOUS_TASKS;
				}

			break;

			case WAITING_FOR_PREVIOUS_TASKS:
				if(ifAllPreviousTasksAreDone()) {
					state = LAUNCHING_ENTRY_TASK;
				}
			break;

			case LAUNCHING_ENTRY_TASK:
				state = WAITING_FOR_ENTRY_TASK;
				runEntryTaskAsync();
			break;

			case WAITING_FOR_ENTRY_TASK:
			break;

			case WAITING_FOR_SUB_TASKS:
				if(ifAllSubTasksAreDone()) {
					state = LAUNCHING_EXIT_TASK;
				}
			break;

			case LAUNCHING_EXIT_TASK:
				state = WAITING_FOR_EXIT_TASK;
				runExitTaskAsync();
			break;

			case WAITING_FOR_EXIT_TASK:
			break;

			case DONE:
			break;

			case DELETED:
			break;
		}
	}
	
	private boolean allNextTasksAreWaitingForPreviousTasks() {
		boolean allWaiting = true;

		synchronized(previousTasks) {
			for(NtroTask nextTask : nextTasks.values()) {
				if(((NtroTaskImpl)nextTask).state != WAITING_FOR_PREVIOUS_TASKS) {
					allWaiting = false;
					break;
				}
			}
		}
		
		return allWaiting;
	}
	
	private boolean ifAllPreviousTasksAreDone() {
		boolean allDone = true;

		synchronized(previousTasks) {
			for(NtroTask previousTask : previousTasks.values()) {
				if(((NtroTaskImpl)previousTask).state != DONE) {
					allDone = false;
					break;
				}
			}
		}

		return allDone;
	}

	private boolean ifAllSubTasksAreDone() {
		boolean allDone = true;

		synchronized(subTasks) {
			for(NtroTask subTask : subTasks.values()) {
				if(((NtroTaskImpl)subTask).state != DONE) {
					allDone = false;
					break;
				}
			}
		}

		return allDone;
	}

	@Override
	public void notifyEntryTaskFinished() {
		if(state == WAITING_FOR_ENTRY_TASK) {
			state = WAITING_FOR_SUB_TASKS;
		}
	}

	@Override
	public void notifyExitTaskFinished() {
		if(state == WAITING_FOR_EXIT_TASK) {
			state = DONE;
			notifyTaskFinished();
			//if(asGraph().isExecuting){}
			execute();                // FIXME: needed to be async 
		}
	}

	private void notifyTaskFinished() {
		if(parentTask != null) {
			parentTask.onSomeSubTaskFinished(getTaskId(), this);
		}

		forEachNextTask(nt -> ((NtroTaskImpl)nt).onSomePreviousTaskFinished(getTaskId(), this));
	}

	@Override
	public void resetGraph() {
		forEachStartNode(sn -> sn.resetNodeTransitive(INIT));
	}

	@Override
	public void resetNodeTransitive(TaskState state) {
		asTask().resetTask(state);
		forEachReachableNodeTransitive(n -> n.asTask().resetTask(state));
	}

	@Override
	public void resetTask(TaskState state) {
		this.state = state;
	}

	@Override
	public void replaceWith(NtroTask replacementTask) {
		if(!taskId.equals(((NtroTaskImpl)replacementTask).taskId)) {
			throw new IllegalArgumentException("replacementTask must have same taskId as replaced task");
		}

		boolean shouldExecuteReplacement = (state != INIT);
		GraphTraceImpl trace = this.trace;

		replacementTask.resetTask(INIT);

		deleteSubTasksTransitive();

		if(parentTask != null) {
			parentTask.replaceSubTaskWith(getTaskId(), replacementTask);
			replacementTask.setParentTask(parentTask);
		}

		Set<NtroTask> finishedPreviousTasks = new HashSet<>();
		forEachPreviousTask(pt -> {
			((NtroTaskImpl)pt).replaceNextTaskWith(getTaskId(), replacementTask);
			replacementTask.addPreviousTask(pt, pt.getTaskId());
			if(((NtroTaskImpl)pt).state == DONE) {
				finishedPreviousTasks.add(pt);
			}
		});

		forEachNextTask(nt -> {
			((NtroTaskImpl)nt).replacePreviousTaskWith(this.getTaskId(), replacementTask);
			((NtroTaskImpl)nt).state = INIT;
			replacementTask.addNextTask(nt, nt.getTaskId());
		});

		this.deleteTask();
		
		((NtroTaskImpl)replacementTask).forEachNodeUpAndForward(n -> ((NtroTaskImpl) n).updateState());

		if(shouldExecuteReplacement) {
			finishedPreviousTasks.forEach(ft -> ((NtroTaskImpl)replacementTask).onSomePreviousTaskFinished(ft.getTaskId(), ft));
			((NtroTaskImpl)replacementTask).execute(trace);
		}
	}
	
	private void forEachNodeUpAndForward(NodeLambda lambda) {
		Set<String> visitedNodes = new HashSet<>();
		
		visitNodesUpAndForward(visitedNodes, lambda);
	}
	
	private void visitNodesUpAndForward(Set<String> visitedNodes, NodeLambda lambda) {
		if(visitedNodes.contains(getNodeId())) return;
		visitedNodes.add(getNodeId());
		
		lambda.execute(this);
		
		if(parentTask != null) {
			parentTask.visitNodesUpAndForward(visitedNodes, lambda);
		}
		
		forEachNextTask(nt -> ((NtroTaskImpl) nt).visitNodesUpAndForward(visitedNodes, lambda));
	}

	private void deleteSubTasksTransitive() {
		forEachSubTask(st -> {
			((NtroTaskImpl)st).forEachPreviousTask(pt -> ((NtroTaskImpl)pt).deleteNextTask(st.getTaskId()));
			((NtroTaskImpl)st).forEachNextTask(nt -> ((NtroTaskImpl)nt).deletePreviousTask(st.getTaskId()));
			((NtroTaskImpl)st).deleteSubTasksTransitive();
		});
	}
	
	private void deleteTask() {
		parentTask = null;
		subTasks = null;
		nextTasks = null;
		previousTasks = null;
		trace = null;
		state = DELETED;
	}
	
	private void updateState() {

		if(hasPreviousTasks() && !ifAllPreviousTasksAreDone()) {

			state = INIT;

		} else if(hasSubTasks() && !ifAllSubTasksAreDone()) {

			state = INIT;
		}
	}

	private void replaceSubTaskWith(String id, NtroTask replacementTask) {
		subTasks.put(id, replacementTask);
	}
	
	private void replaceNextTaskWith(String id, NtroTask replacementTask) {
		nextTasks.put(id, replacementTask);
	}

	private void replacePreviousTaskWith(String id, NtroTask replacementTask) {
		previousTasks.put(id, replacementTask);
	}
	
	private void deletePreviousTask(String id) {
		previousTasks.remove(id);
	}

	private void deleteNextTask(String id) {
		nextTasks.remove(id);
	}
	

	@Override
	public Node findNodeById(String id) {
		Set<Node> visitedNodes = new HashSet<>();
		
		return findNodeById(id, visitedNodes);
	}
	
	private Node findNodeById(String id, Set<Node> visitedNodes) {
		if(visitedNodes.contains(this)) return null;
		visitedNodes.add(this);

		Node foundNode = null;

		if(getTaskId().equals(id)) {
			foundNode = this;
		}
		
		if(foundNode == null && parentTask != null) {
			foundNode = parentTask.findNodeById(id, visitedNodes);
		}
		
		if(foundNode == null) {
			synchronized(previousTasks) {
				for(NtroTask previousTask : previousTasks.values()) {
					foundNode = ((NtroTaskImpl) previousTask).findNodeById(id, visitedNodes);
					if(foundNode != null) break;
				}
			}
		}

		if(foundNode == null) {
			synchronized(subTasks) {
				for(NtroTask subTask : subTasks.values()) {
					foundNode = ((NtroTaskImpl) subTask).findNodeById(id, visitedNodes);
					if(foundNode != null) break;
				}
			}
		}

		if(foundNode == null) {
			synchronized(nextTasks) {
				for(NtroTask nextTask : nextTasks.values()) {
					foundNode = ((NtroTaskImpl) nextTask).findNodeById(id, visitedNodes);
					if(foundNode != null) break;
				}
			}
		}
		
		return foundNode;
	}
	
	
	@Override
	public void writeNode(GraphWriter writer) {
		getNodeDescription().writeNode(writer);
	}

}
