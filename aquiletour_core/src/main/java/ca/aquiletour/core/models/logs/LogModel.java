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

	public void renameUser(String userId, String firstname, String lastname) {
		T.call(this);
		
		User user = getUserById().valueOf(userId);

		if(user != null) {

			user.setFirstname(firstname);
			user.setLastname(lastname);
		}
	}

	public void writeCsvFileContent(String separator, StringBuilder builder) {
		T.call(this);

		int longuestTaskPath = getLogItems().longuestTaskPath();

		writeCsvHeader(separator, builder);
		
		getLogItems().forEachItem((index, logItem) -> {
			
			logItem.writeCsvLine(separator, builder, longuestTaskPath);

			builder.append(Ntro.lineSeparator());
		});
	}
	
	protected abstract void writeCsvHeader(String separator, StringBuilder builder);

	public LI createLogItem(Class<LI> logItemClass, NtroDate timestamp, User user) {
		T.call(this);
		
		LI logItem = Ntro.factory().newInstance(logItemClass);
		
		logItem.setLogModel(this);

		logItem.updateTimestamp(timestamp);

		memorizeUser(user);
		logItem.setUserId(user.getId());
		
		return logItem;
	}

	public static LogModel<?, ?> empty() {
		T.call(LogModel.class);
		return new LogModelEmpty();
	}
}
