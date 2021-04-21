package ca.aquiletour.server.backend.course_list;

import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.course_list.models.CourseItem;
import ca.aquiletour.core.pages.course_list.models.CourseListModel;
import ca.aquiletour.core.pages.course_list.models.TaskDescription;
import ca.ntro.backend.BackendMessageHandlerError;
import ca.ntro.core.models.ModelInitializer;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.trace.T;

public class CourseListUpdater {

	public static void validateCourseDescription(CourseItem courseDescription) throws BackendMessageHandlerError {
		T.call(CourseListUpdater.class);

		if(courseDescription.getCourseId().contains(" ")) {
			throw new BackendMessageHandlerError("Le code de cours ne doit pas contenir d'espace");
		}
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

	public static String getCourseTitle(ModelStoreSync modelStore, 
			                            String semesterId, 
			                            String courseId, 
			                            String userId) {

		T.call(CourseListUpdater.class);
		
		return getCourseItem(modelStore, semesterId, courseId, userId).getCourseTitle();
	}

	public static CourseItem getCourseItem(ModelStoreSync modelStore, 
			                           String semesterId, 
			                           String courseId, 
			                           String userId) {

		T.call(CourseListUpdater.class);
		
		CourseListModel model = modelStore.getModel(CourseListModel.class, "admin", userId);
		
		return model.courseById(semesterId, courseId);
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
