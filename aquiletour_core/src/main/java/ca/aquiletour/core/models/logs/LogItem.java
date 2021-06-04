package ca.aquiletour.core.models.logs;

import ca.aquiletour.core.models.user.User;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.models.NtroDate;

public abstract class LogItem implements NtroModelValue {
	
	private LogModel<?,?> logModel;
	public void registerLogModel(LogModel<?,?> logModel) {
		this.logModel = logModel;
	}
	
	private NtroDate timestamp = new NtroDate();
	private String userId = "";

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

	public void writeCsvLine(String separator, 
			                 StringBuilder builder) {

		builder.append(getTimestamp().format("yyyy-MM-dd HH:mm:ss"));
		
		User user = logModel.userByUd(getUserId());

		builder.append(separator);
		builder.append(user.getId());

		builder.append(separator);
		builder.append(user.getUuid());

		builder.append(separator);
		builder.append(user.getFirstname());

		builder.append(separator);
		builder.append(user.getLastname());

		writeCsvLineAfterBasicInfo(separator, builder);
	}
	
	protected abstract void writeCsvLineAfterBasicInfo(String separator, StringBuilder builder);

}
