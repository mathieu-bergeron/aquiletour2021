package ca.aquiletour.core.models.logs;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTaskCompletion;
import ca.aquiletour.core.models.paths.TaskPath;
import ca.aquiletour.core.models.user.User;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;
import ca.ntro.services.Ntro;

public class LogModelCourse extends LogModel<LogItemCourse, LogItemsCourse> {
	
	private LogItemsCourse logItems = new LogItemsCourse();
	private int longuestTaskPath;

	public void addAtomicTaskCompletion(TaskPath taskPath, 
										User user,
			                            String groupId, 
			                            String atomicTaskId, 
			                            AtomicTaskCompletion completion) {
		T.call(this);
		
		List<String> eventItems = new ArrayList<>();
		
		eventItems.add(atomicTaskId);
		
		if(completion != null) {
			eventItems.addAll(completion.logItems());
			
		}
		
		NtroDate timestamp = Ntro.calendar().now();
		
		LogItemCourse item = createLogItem(LogItemCourse.class, timestamp, user);

		item.setTaskPath(taskPath);
		item.setGroupId(groupId);
		item.setEventItems(eventItems);

		getLogItems().addItem(item);
	}

	@Override
	public LogItemsCourse getLogItems() {
		return logItems;
	}

	@Override
	public void setLogItems(LogItemsCourse logItems) {
		this.logItems = logItems;
	}

	@Override
	protected void writeCsvLine(LogItemCourse logItem, String separator, StringBuilder builder) {
		T.call(this);

		logItem.registerLonguestTaskPath(longuestTaskPath);
		logItem.writeCsvLine(separator, builder);
	}

	@Override
	protected void writeCsvHeader(String separator, StringBuilder builder) {
		T.call(this);

		longuestTaskPath = logItems.longuestTaskPath();
	}

}
