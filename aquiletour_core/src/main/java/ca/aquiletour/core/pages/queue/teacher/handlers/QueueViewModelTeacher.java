package ca.aquiletour.core.pages.queue.teacher.handlers;


import java.util.Map;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.queue.handlers.QueueViewModel;
import ca.aquiletour.core.pages.queue.models.QueueModel;
import ca.aquiletour.core.pages.queue.models.QueueSettings;
import ca.aquiletour.core.pages.queue.models.QueueSettingsCourse;
import ca.aquiletour.core.pages.queue.models.SettingsByCourseId;
import ca.aquiletour.core.pages.queue.models.SettingsByGroupId;
import ca.aquiletour.core.pages.queue.teacher.views.QueueViewTeacher;
import ca.ntro.core.models.StoredBoolean;
import ca.ntro.core.models.StoredString;
import ca.ntro.core.models.listeners.MapObserver;
import ca.ntro.core.models.listeners.ValueObserver;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public class QueueViewModelTeacher extends QueueViewModel<QueueViewTeacher> {
	
	private boolean isQueueOpen = false;

	@Override
	protected void handle(QueueModel model, QueueViewTeacher view, ViewLoader subViewLoader) {
		T.call(this);
		super.handle(model, view, subViewLoader);
		
		observeMainSettings(model, model.getMainSettings(), view);
		observeSettingsByCourseId(model, model.getSettingsByCourseId(), view);
	}

	private void observeSettingsByCourseId(QueueModel model, SettingsByCourseId settingsByCourseId, QueueViewTeacher view) {
		T.call(this);
		
		view.clearQueueMenu();
		settingsByCourseId.removeObservers();
		settingsByCourseId.observe(new MapObserver<QueueSettingsCourse>() {
			@Override
			public void onEntryAdded(String courseId, QueueSettingsCourse value) {
				T.call(this);
				updateIsQueueOpen(model, view);
				view.addToQueueMenu(courseId);
				observeCourseSettings(model, courseId, value, view);
			}
			
			@Override
			public void onClearEntries() {
				T.call(this);
				updateIsQueueOpen(model, view);
			}
			
			@Override
			public void onDeleted(Map<String, QueueSettingsCourse> lastValue) {
				T.call(this);
				updateIsQueueOpen(model, view);
			}
			
			@Override
			public void onValue(Map<String, QueueSettingsCourse> value) {
				T.call(this);
				updateIsQueueOpen(model, view);
			}

			@Override
			public void onValueChanged(Map<String, QueueSettingsCourse> oldValue, Map<String, QueueSettingsCourse> value) {
				T.call(this);
				updateIsQueueOpen(model, view);
			}

			@Override
			public void onEntryRemoved(String courseId, QueueSettingsCourse value) {
				T.call(this);
				updateIsQueueOpen(model, view);
				view.removeFromQueueMenu(courseId);
			}
		});
	}
	
	private void observeSettingsByGroupId(QueueModel model, 
			 							  String courseId, 
			 							  SettingsByGroupId settingsByGroupId, 
			 							  QueueViewTeacher view) {
		T.call(this);
		
		settingsByGroupId.removeObservers();
		settingsByGroupId.observe(new MapObserver<QueueSettings>() {
			@Override
			public void onValueChanged(Map<String, QueueSettings> oldValue, Map<String, QueueSettings> value) {
				T.call(this);
				updateIsQueueOpen(model, view);
			}

			@Override
			public void onValue(Map<String, QueueSettings> value) {
				T.call(this);
				updateIsQueueOpen(model, view);
			}

			@Override
			public void onDeleted(Map<String, QueueSettings> lastValue) {
				T.call(this);
				updateIsQueueOpen(model, view);
			}

			@Override
			public void onClearEntries() {
				T.call(this);
				updateIsQueueOpen(model, view);
			}

			@Override
			public void onEntryAdded(String groupId, QueueSettings value) {
				T.call(this);
				view.addToQueueMenu(courseId, groupId);
				updateIsQueueOpen(model, view);
			}

			@Override
			public void onEntryRemoved(String groupId, QueueSettings value) {
				T.call(this);
				updateIsQueueOpen(model, view);
				view.removeFromQueueMenu(courseId, groupId);
			}
		});
		
		
		
	}

	protected void observeIsQueueOpen(QueueModel model, 
			                          String courseId, 
			                          String groupId, 
			                          StoredBoolean isQueueOpen, 
			                          QueueViewTeacher view) {
		T.call(this);

		isQueueOpen.removeObservers();
		isQueueOpen.observe(new ValueObserver<Boolean>() {
			@Override
			public void onValue(Boolean value) {
				T.call(this);
				updateIsQueueOpen(model, view);
				view.displayIfQueueOpen(courseId, groupId, false);
			}

			@Override
			public void onDeleted(Boolean lastValue) {
				T.call(this);
				updateIsQueueOpen(model, view);
			}

			@Override
			public void onValueChanged(Boolean oldValue, Boolean value) {
				updateIsQueueOpen(model, view);
				view.displayIfQueueOpen(courseId, groupId, value);
			}
		});
	}

	protected void observeCourseSettings(QueueModel model, String courseId, QueueSettingsCourse courseSettings, QueueViewTeacher view) {
		T.call(this);
		
		observeSettingsByGroupId(model, courseId, courseSettings.getSettingsByGroupId(), view);
		observeIsQueueOpen(model, courseId, null, courseSettings.getIsQueueOpen(), view);
		observeCourseTitle(courseId, courseSettings.getCourseTitle(),view);
	}

	private void observeCourseTitle(String courseId, StoredString courseTitle, QueueViewTeacher view) {
		T.call(this);
		
		courseTitle.removeObservers();
		courseTitle.observe(new ValueObserver<String>() {
			@Override
			public void onValue(String value) {
				T.call(this);
				view.displayCourseTitle(courseId, value);
			}

			@Override
			public void onDeleted(String lastValue) {
			}

			@Override
			public void onValueChanged(String oldValue, String value) {
				T.call(this);
				view.displayCourseTitle(courseId, value);
			}
		});
	}

	protected void observeGroupSettings(QueueModel model, 
			                            String courseId, 
			                            String groupId, 
			                            QueueSettings settings, 
			                            QueueViewTeacher view) {
		T.call(this);
		
		observeIsQueueOpen(model, courseId, groupId, settings.getIsQueueOpen(), view);
	}

	protected void observeMainSettings(QueueModel model, QueueSettings mainSettings, QueueViewTeacher view) {
		T.call(this);

		observeIsQueueOpen(model, Constants.ALL_COURSES_ID, null, mainSettings.getIsQueueOpen(), view);
	}

	private void updateIsQueueOpen(QueueModel model, QueueViewTeacher view) {
		T.call(this);
		
		boolean isQueueOpenNow = model.isQueueOpen();
		
		if(isQueueOpen != isQueueOpenNow) {
			isQueueOpen = isQueueOpenNow;
			view.displayIfQueueOpen(isQueueOpen);
		}
	}

}
