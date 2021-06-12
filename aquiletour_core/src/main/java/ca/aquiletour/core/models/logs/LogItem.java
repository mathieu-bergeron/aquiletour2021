package ca.aquiletour.core.models.logs;

import ca.aquiletour.core.models.paths.CoursePath;
import ca.aquiletour.core.models.paths.TaskPath;
import ca.aquiletour.core.models.user.User;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;

public abstract class LogItem implements NtroModelValue {
	
	private LogModel<?,?> logModel = LogModel.empty();
	private String timestamp = "";
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

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public void updateTimestamp(NtroDate timestamp) {
		setTimestamp(formatTimestamp(timestamp));
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

		builder.append(getTimestamp());

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

	protected void writeCsvLineTaskPath(String separator, StringBuilder builder, int longuestTaskPath) {
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
		
		CoursePath coursePath = getCoursePath();
		
		if(coursePath != null 
				&& coursePath.nameCount() > 0) {

			String teacherId = coursePath.teacherId();
			String semesterId = coursePath.semesterId();
			String courseId = coursePath.courseId();
			
			if(teacherId != null) {
				builder.append(separator);
				builder.append(teacherId);
			}

			if(semesterId != null) {
				builder.append(separator);
				builder.append(semesterId);
			}

			if(courseId != null) {
				builder.append(separator);
				builder.append(courseId);
			}
		}
	}

	protected void writeCsvLineGroupId(String separator, StringBuilder builder) {
		T.call(this);

		builder.append(separator);
		builder.append(groupId);
	}

	public abstract void writeCsvLine(String separator, StringBuilder builder, int longuestTaskPath);

}
