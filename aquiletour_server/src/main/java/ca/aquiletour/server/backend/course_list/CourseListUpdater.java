package ca.aquiletour.server.backend.course_list;

import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.models.courses.base.OnTaskAdded;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.course_list.models.CourseItem;
import ca.aquiletour.core.pages.course_list.models.CourseListModel;
import ca.aquiletour.core.pages.course_list.models.TaskDescription;
import ca.aquiletour.server.backend.git.GitMessages;
import ca.ntro.core.Path;
import ca.ntro.core.models.ModelInitializer;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.trace.T;

public class CourseListUpdater {

	public static String validateCourseDescription(CourseItem courseDescription) {
		T.call(CourseListUpdater.class);
		
		String errorMessage = null;

		if(courseDescription.getCourseId().contains(" ")) {
			errorMessage = "Le code de cours ne doit pas contenir d'espace";
		}
			
		return errorMessage;
	}

	public static void addSemesterForUser(ModelStoreSync modelStore, 
			                              String semesterId, 
			                              User user) {

		T.call(CourseListUpdater.class);
		
		addSemesterForUserId(modelStore, semesterId, user.getId());
	}
	
	public static void addSemesterForUserId(ModelStoreSync modelStore, 
			                              String semesterId, 
			                              String userId) {

		T.call(CourseListUpdater.class);
		
		if(!modelStore.ifModelExists(CourseListModel.class, "admin", userId)) {
			modelStore.createModel(CourseListModel.class, "admin", userId, new ModelInitializer<CourseListModel>() {
				@Override
				public void initialize(CourseListModel newModel) {
					T.call(this);
				}
			});
		}

		modelStore.updateModel(CourseListModel.class, 
							   "admin",
							   userId,
							   new ModelUpdater<CourseListModel>() {

			@Override
			public void update(CourseListModel courseListModel) {
				T.call(this);
				
				courseListModel.addSemesterId(semesterId);
			}
		});
	}
	

	public static void addCourseForUser(ModelStoreSync modelStore, CourseItem courseDescription, User teacher) {
		T.call(CourseListUpdater.class);
		
		if(modelStore.ifModelExists(CourseListModel.class, "admin", teacher.getId())) {
			
			modelStore.updateModel(CourseListModel.class, "admin", teacher.getId(), new ModelUpdater<CourseListModel>() {
				@Override
				public void update(CourseListModel existingModel) {
					T.call(this);
					
					existingModel.addCourse(courseDescription);
				}
			});

		}else {
			
			modelStore.createModel(CourseListModel.class, "admin", teacher.getId(), new ModelInitializer<CourseListModel>() {
				@Override
				public void initialize(CourseListModel newModel) {
					T.call(this);
				}
			});
			
			addCourseForUser(modelStore, courseDescription, teacher);
		}
	}

	public static void addGroupForUser(ModelStoreSync modelStore, 
			                           String semesterId, 
			                           String courseId, 
			                           String groupId, 
			                           User user) {

		T.call(CourseListUpdater.class);
		
		addGroupForUserId(modelStore, semesterId, courseId, groupId, user.getId());
		
	}

	public static void addGroupForUserId(ModelStoreSync modelStore, 
			                             String semesterId, 
			                             String courseId, 
			                             String groupId,
			                             String userId) {

		T.call(CourseListUpdater.class);

		modelStore.updateModel(CourseListModel.class, "admin", userId, new ModelUpdater<CourseListModel>() {
			@Override
			public void update(CourseListModel existingModel) {
				T.call(this);

				existingModel.addGroup(semesterId, courseId, groupId);
			}
		});
	}
	
	
	public static void addTask(ModelStoreSync modelStore, CoursePath coursePath, TaskDescription task) {
		T.call(CourseListUpdater.class);
		
		modelStore.updateModel(CourseListModel.class, "admin", coursePath.teacherId(), new ModelUpdater<CourseListModel>() {
			@Override
			public void update(CourseListModel courseList) {
				T.call(this);
				
				courseList.addTask(coursePath, task);
			}
		});
	}

}