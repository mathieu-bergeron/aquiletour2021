package ca.aquiletour.core.models.logs;


import ca.aquiletour.core.models.user.User;
import ca.ntro.core.models.DoNotCacheModel;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;
import ca.ntro.services.Ntro;

public abstract class LogModel<LI extends LogItem, LIS extends LogItems<LI>> implements NtroModel, DoNotCacheModel {
	
	public abstract LIS getLogItems();
	public abstract void setLogItems(LIS logItems);
	
	private UserById userById = new UserById();
	
	public UserById getUserById() {
		return userById;
	}

	public void setUserById(UserById userById) {
		this.userById = userById;
	}

	public void memorizeUser(User user) {
		T.call(this);
		
		getUserById().putEntry(user.getId(), user.toPublicUser());
	}

	public User userByUd(String userId) {
		T.call(this);
		
		return getUserById().valueOf(userId);
	}

	public void writeCsvFileContent(String separator, StringBuilder builder) {
		T.call(this);
		
		writeCsvHeader(separator, builder);

		getLogItems().forEachItem((index, logItem) -> {
			writeCsvLine(logItem, separator, builder);
			builder.append(Ntro.lineSeparator());
		});
	}
	
	protected abstract void writeCsvHeader(String separator, StringBuilder builder);
	protected abstract void writeCsvLine(LI logItem, String separator, StringBuilder builder);

	public LI createLogItem(Class<LI> logItemClass, NtroDate timestamp, User user) {
		T.call(this);
		
		LI logItem = Ntro.factory().newInstance(logItemClass);
		
		logItem.setLogModel(this);

		logItem.setTimestamp(timestamp);

		memorizeUser(user);
		logItem.setUserId(user.getId());
		
		return logItem;
	}

	public static LogModel<?, ?> empty() {
		T.call(LogModel.class);
		return new LogModelEmpty();
	}

}
