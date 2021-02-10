package ca.ntro.core.task2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ca.ntro.core.services.NtroCollections;
import ca.ntro.core.system.log.Log;

public abstract class NtroTaskImpl implements NtroTask {

	private static Map<String, Integer> classIds = new HashMap<>();
	
	private String taskId;
	private NtroTask parentTask;
	
	private Map<String, NtroTask> previousTasks = NtroCollections.concurrentMap(new HashMap<>());
	private Map<String, NtroTask> subTasks = NtroCollections.concurrentMap(new HashMap<>());
	private Map<String, NtroTask> nextTasks = NtroCollections.concurrentMap(new HashMap<>());

	private Set<String> finishedPreviousTasks = NtroCollections.concurrentSet(new HashSet<>());
	private Set<String> finishedSubTasks = NtroCollections.concurrentSet(new HashSet<>());
	private Set<String> finishedNextTasks = NtroCollections.concurrentSet(new HashSet<>());

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
	public void setId(String taskId) {
		this.taskId = taskId;
	}

	@Override
	public void setParentTask(NtroTask parentTask) {
		this.parentTask = parentTask;
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
		Set<NtroTask> visitedTasks = new HashSet<>();
		writeGraph(writer, visitedTasks);
	}

	@Override
	public synchronized void writeGraph(GraphWriter writer, Set<NtroTask> visitedTasks) {
		if(visitedTasks.contains(this)) return;
		visitedTasks.add(this);
		
		if(parentTask == null && !hasSubTasks()) {
			writer.addRootNode(this);

		}else if(parentTask == null && hasSubTasks()) {
			writer.addRootCluster(this);

		}else if(parentTask != null && !hasSubTasks()) {
			parentTask.writeGraph(writer, visitedTasks);
			writer.addSubNode(parentTask, this);

		}else if(parentTask != null && hasSubTasks()){
			parentTask.writeGraph(writer, visitedTasks);
			writer.addSubCluster(parentTask, this);

		}else {
			Log.warning("Should not occur");
		}

		forEachPreviousTask(previousTask -> previousTask.writeGraph(writer, visitedTasks));
		forEachPreviousTask(previousTask -> writer.addEdge(previousTask,this));
		
		forEachSubTask(subTask -> subTask.writeGraph(writer, visitedTasks));

		forEachNextTask(nextTask -> nextTask.writeGraph(writer, visitedTasks));
		forEachNextTask(nextTask -> writer.addEdge(this, nextTask));
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

	private boolean hasSubTasks() {
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
