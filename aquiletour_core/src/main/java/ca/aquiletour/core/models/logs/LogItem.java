package ca.aquiletour.core.models.logs;

import ca.aquiletour.core.models.paths.CoursePath;
import ca.aquiletour.core.models.paths.TaskPath;
import ca.aquiletour.core.models.user.User;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;

public abstract class LogItem implements NtroModelValue {
	
	private int longuestTaskPath = 0;
	void registerLonguestTaskPath(int longuestTaskPath) {
		this.longuestTaskPath = longuestTaskPath;
	}
	
	private LogModel<?,?> logModel = LogModel.empty();
	private NtroDate timestamp = new NtroDate();
	private String userId = "";

	private TaskPath taskPath = new TaskPath();
	private CoursePath coursePath = new CoursePath();
	private String groupId = "";

	public CoursePath getCoursePath() {
		return coursePath;
	}

	public void setCoursePath(CoursePath coursePath) {
		this.coursePath = coursePath;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public TaskPath getTaskPath() {
		return taskPath;
	}

	public void setTaskPath(TaskPath taskPath) {
		this.taskPath = taskPath;
	}

	public LogModel<?, ?> getLogModel() {
		return logModel;
	}

	public void setLogModel(LogModel<?, ?> logModel) {
		this.logModel = logModel;
	}

	public NtroDate getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(NtroDate timestamp) {
		this.timestamp = timestamp;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	protected static String formatTimestamp(NtroDate timestamp) {
		T.call(LogItem.class);
		
		String timestampString = "";
		
		if(timestamp != null) {
			
			timestampString = timestamp.format("yyyy-MM-dd HH:mm:ss");
		}

		return timestampString;
	}

	protected void writeCsvLineBasicInfo(String separator, 
			                             StringBuilder builder) {
		T.call(this);

		builder.append(formatTimestamp(getTimestamp()));

		User user = logModel.userByUd(getUserId());

		builder.append(separator);
		builder.append(user.getId());

		builder.append(separator);
		builder.append(user.getUuid());

		builder.append(separator);
		builder.append(user.getFirstname());

		builder.append(separator);
		builder.append(user.getLastname());

	}

	protected void writeCsvLineTaskPath(String separator, StringBuilder builder) {
		T.call(this);

		for(int i = 0; i < longuestTaskPath; i++) {

			if(i < getTaskPath().nameCount()) {

				builder.append(separator);
				builder.append(getTaskPath().name(i));
			}
		}
	}

	protected void writeCsvLineCoursePath(String separator, StringBuilder builder) {
		T.call(this);

		builder.append(separator);
		builder.append(getCoursePath().teacherId());

		builder.append(separator);
		builder.append(getCoursePath().semesterId());

		builder.append(separator);
		builder.append(getCoursePath().courseId());
	}

	protected void writeCsvLineGroupId(String separator, StringBuilder builder) {
		T.call(this);

		builder.append(separator);
		builder.append(groupId);
	}

	public abstract void writeCsvLine(String separator, StringBuilder builder);

}
