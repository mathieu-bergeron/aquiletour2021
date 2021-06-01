package ca.aquiletour.core.pages.queue.teacher.handlers;


import java.util.Map;

import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.pages.queue.handlers.QueueViewModel;
import ca.aquiletour.core.pages.queue.models.QueueModel;
import ca.aquiletour.core.pages.queue.models.QueueSettings;
import ca.aquiletour.core.pages.queue.models.QueueSettingsCourse;
import ca.aquiletour.core.pages.queue.models.SettingsByCourseKey;
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
		observeSettingsByCourseId(model, model.getSettingsByCourseKey(), view);
	}

	private void observeSettingsByCourseId(QueueModel model, SettingsByCourseKey settingsByCourseKey, QueueViewTeacher view) {
		T.call(this);
		
		view.clearQueueMenu();
		settingsByCourseKey.removeObservers();
		settingsByCourseKey.observe(new MapObserver<QueueSettingsCourse>() {
			@Override
			public void onEntryAdded(String courseKey, QueueSettingsCourse value) {
				T.call(this);
				updateIsQueueOpen(model, view);
				view.addToQueueMenu(CoursePath.fromKey(courseKey));
				observeCourseSettings(model, CoursePath.fromKey(courseKey), value, view);
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
			public void onEntryRemoved(String courseKey, QueueSettingsCourse value) {
				T.call(this);
				updateIsQueueOpen(model, view);
				view.removeFromQueueMenu(CoursePath.fromKey(courseKey));
			}
		});
	}
	
	private void observeSettingsByGroupId(QueueModel model, 
			 							  CoursePath coursePath, 
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
				view.addToQueueMenu(coursePath, groupId);
				updateIsQueueOpen(model, view);
			}

			@Override
			public void onEntryRemoved(String groupId, QueueSettings value) {
				T.call(this);
				updateIsQueueOpen(model, view);
				view.removeFromQueueMenu(coursePath, groupId);
			}
		});
		
		
		
	}

	protected void observeIsQueueOpen(QueueModel model, 
			                          CoursePath coursePath,
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
				view.displayIfQueueOpen(coursePath, groupId, false);
			}

			@Override
			public void onDeleted(Boolean lastValue) {
				T.call(this);
				updateIsQueueOpen(model, view);
			}

			@Override
			public void onValueChanged(Boolean oldValue, Boolean value) {
				updateIsQueueOpen(model, view);
				view.displayIfQueueOpen(coursePath, groupId, value);
			}
		});
	}

	protected void observeCourseSettings(QueueModel model, 
			                             CoursePath coursePath, 
			                             QueueSettingsCourse courseSettings, 
			                             QueueViewTeacher view) {
		T.call(this);
		
		observeSettingsByGroupId(model, coursePath, courseSettings.getSettingsByGroupId(), view);
		observeIsQueueOpen(model, coursePath, null, courseSettings.getIsQueueOpen(), view);
		observeCourseTitle(coursePath, courseSettings.getCourseTitle(),view);
	}

	private void observeCourseTitle(CoursePath coursePath, StoredString courseTitle, QueueViewTeacher view) {
		T.call(this);
		
		courseTitle.removeObservers();
		courseTitle.observe(new ValueObserver<String>() {
			@Override
			public void onValue(String value) {
				T.call(this);
				view.displayCourseTitle(coursePath, value);
			}

			@Override
			public void onDeleted(String lastValue) {
			}

			@Override
			public void onValueChanged(String oldValue, String value) {
				T.call(this);
				view.displayCourseTitle(coursePath, value);
			}
		});
	}

	protected void observeGroupSettings(QueueModel model, 
										CoursePath coursePath,
			                            String groupId, 
			                            QueueSettings settings, 
			                            QueueViewTeacher view) {
		T.call(this);
		
		observeIsQueueOpen(model, coursePath, groupId, settings.getIsQueueOpen(), view);
	}

	protected void observeMainSettings(QueueModel model, 
			                           QueueSettings mainSettings, 
			                           QueueViewTeacher view) {
		T.call(this);

		observeIsQueueOpen(model, CoursePath.allCourses(), null, mainSettings.getIsQueueOpen(), view);
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
