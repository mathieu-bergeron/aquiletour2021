package ca.aquiletour.core.models.logs;


import ca.aquiletour.core.models.user.User;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;
import ca.ntro.services.Ntro;

public abstract class LogModel<LI extends LogItem, LIS extends LogItems<LI>> implements NtroModel	{
	
	public abstract LIS getLogItems();

	public void setLogItems(LIS logItems) {
		T.call(this);
		
		logItems.registerLogModel(this);

		registerLogItems(logItems);
	}

	public abstract void registerLogItems(LIS logItems);
	
	private UserById userById = new UserById();
	
	public UserById getUserById() {
		return userById;
	}

	public void setUserById(UserById userById) {
		this.userById = userById;
	}

	public void memorizeUser(User user) {
		T.call(this);
		
		getUserById().putEntry(user.getId(), user);
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
			builder.append(System.lineSeparator());
		});
	}
	
	protected abstract void writeCsvHeader(String separator, StringBuilder builder);
	protected abstract void writeCsvLine(LI logItem, String separator, StringBuilder builder);

	public static <LI extends LogItem> LI createLogItem(Class<LI> logItemClass, LogModel<?,?> logModel, NtroDate timestamp, User user) {
		T.call(LogModel.class);
		
		LI logItem = Ntro.factory().newInstance(logItemClass);
		
		logItem.registerLogModel(logModel);

		logItem.setTimestamp(timestamp);

		logModel.memorizeUser(user);
		logItem.setUserId(user.getId());
		
		return logItem;
	}



}
