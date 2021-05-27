package ca.aquiletour.core.pages.queue.models;

import ca.ntro.core.models.StoredString;
import ca.ntro.core.models.lambdas.Break;
import ca.ntro.core.system.trace.T;

public class QueueSettingsCourse extends QueueSettings {

	private StoredString courseTitle = new StoredString(); 
	private SettingsByGroupId settingsByGroupId = new SettingsByGroupId();

	public SettingsByGroupId getSettingsByGroupId() {
		return settingsByGroupId;
	}

	public void setSettingsByGroupId(SettingsByGroupId settingsByGroupId) {
		this.settingsByGroupId = settingsByGroupId;
	}
	
	@Override
	public boolean isQueueOpen() {
		T.call(this);
		
		boolean isOpen = super.isQueueOpen();
		
		if(!isOpen) {
			
			isOpen = isQueueOpenForSomeGroup();
		}
		
		return isOpen;
	}

	private boolean isQueueOpenForSomeGroup() {
		T.call(this);
		
		return getSettingsByGroupId().reduceTo(Boolean.class, false, (groupId, groupSettings, currentIsOpen) ->{
			if(currentIsOpen) {
				throw new Break();
			}

			return groupSettings.isQueueOpen();
		});
	}

	public StoredString getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(StoredString courseTitle) {
		this.courseTitle = courseTitle;
	}

	public void updateTitle(String courseTitle) {
		T.call(this);
		
		getCourseTitle().set(courseTitle);
	}

	public void addGroupSettings(String groupId) {
		T.call(this);

		if(!getSettingsByGroupId().containsKey(groupId)) {
			getSettingsByGroupId().putEntry(groupId, new QueueSettings());
		}
	}
}
