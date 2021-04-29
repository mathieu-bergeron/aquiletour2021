package ca.aquiletour.server.backend.course_list;

import java.util.ArrayList;
import java.util.List;

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
		
		// forbidden chars: ' " ¤ /

		if(courseDescription.getCourseId().contains(" ")) {
			throw new BackendMessageHandlerError("Le code de cours ne doit pas contenir d'espace");
		}
	}

	public static <CLM extends CourseListModel> void addSemesterForUser(ModelStoreSync modelStore, 
																	    Class<CLM> courseListModelClass,
			                                                            String semesterId, 
			                                                            User user) {

		T.call(CourseListUpdater.class);
		
		addSemesterForUserId(modelStore,courseListModelClass, semesterId, user.getId());
	}
	
	public static <CLM extends CourseListModel> void addSemesterForUserId(ModelStoreSync modelStore, 
			                                                              Class<CLM> courseListModelClass,
			                                                              String semesterId, 
			                                                              String userId) {

		T.call(CourseListUpdater.class);
		
		if(!modelStore.ifModelExists(courseListModelClass, "admin", userId)) {
			modelStore.createModel(courseListModelClass, "admin", userId, new ModelInitializer<CLM>() {
				@Override
				public void initialize(CLM newModel) {
					T.call(this);
				}
			});
		}

		modelStore.updateModel(courseListModelClass, 
							   "admin",
							   userId,
							   new ModelUpdater<CLM>() {

			@Override
			public void update(CLM courseListModel) {
				T.call(this);
				
				courseListModel.addSemesterId(semesterId);
			}
		});
	}
	

	public static <CLM extends CourseListModel> void addCourseForUser(ModelStoreSync modelStore, 
			 														  Class<CLM> courseListModelClass,
			                                                          CourseItem courseDescription, 
			                                                          User teacher) {
		T.call(CourseListUpdater.class);
		
		if(modelStore.ifModelExists(courseListModelClass, "admin", teacher.getId())) {
			
			modelStore.updateModel(courseListModelClass, "admin", teacher.getId(), new ModelUpdater<CLM>() {
				@Override
				public void update(CLM existingModel) {
					T.call(this);
					
					existingModel.addCourse(courseDescription);
				}
			});

		}else {
			
			modelStore.createModel(courseListModelClass, "admin", teacher.getId(), new ModelInitializer<CLM>() {
				@Override
				public void initialize(CLM newModel) {
					T.call(this);
				}
			});
			
			addCourseForUser(modelStore, courseListModelClass, courseDescription, teacher);
		}
	}

	public static <CLM extends CourseListModel> void addGroupForUser(ModelStoreSync modelStore, 
																     Class<CLM> courseListModelClass, 
																     String semesterId, 
																     String courseId, 
																     String groupId, 
																     User user) {

		T.call(CourseListUpdater.class);
		
		addGroupForUserId(modelStore, courseListModelClass, semesterId, courseId, groupId, user.getId());
		
	}

	public static <CLM extends CourseListModel> void addGroupForUserId(ModelStoreSync modelStore, 
																	   Class<CLM> courseListModelClass, 
																	   String semesterId, 
																	   String courseId, 
																	   String groupId, 
																	   String userId) {

		T.call(CourseListUpdater.class);

		modelStore.updateModel(courseListModelClass, "admin", userId, new ModelUpdater<CLM>() {
			@Override
			public void update(CLM existingModel) {
				T.call(this);

				existingModel.addGroup(semesterId, courseId, groupId);
			}
		});
	}

	public static <CLM extends CourseListModel> void closeQueueForUserId(ModelStoreSync modelStore, 
																		 Class<CLM> courseListModelClass, 
																		 String semesterId, 
																		 String courseId, 
																		 String userId) {

		T.call(CourseListUpdater.class);

		modelStore.updateModel(courseListModelClass, "admin", userId, new ModelUpdater<CLM>() {
			@Override
			public void update(CLM existingModel) {
				T.call(this);
				
				existingModel.closeQueue(semesterId, courseId);
			}
		});
	}
	
	public static <CLM extends CourseListModel> void closeQueueForUser(ModelStoreSync modelStore, 
			 														   Class<CLM> courseListModelClass, 
			 														   String semesterId, 
			 														   String courseId, 
			 														   User user) { 
		T.call(CourseListUpdater.class);
		
		closeQueueForUserId(modelStore, courseListModelClass, semesterId, courseId, user.getId());
	}
	

	public static <CLM extends CourseListModel> void openQueueForUserId(ModelStoreSync modelStore, 
			 															Class<CLM> courseListModelClass, 
			 															String semesterId, 
			 															String courseId, 
			 															String userId) {

		T.call(CourseListUpdater.class);

		modelStore.updateModel(courseListModelClass, "admin", userId, new ModelUpdater<CLM>() {
			@Override
			public void update(CLM existingModel) {
				T.call(this);
				
				existingModel.openQueue(semesterId, courseId);

			}
		});
	}
	
	public static void openQueueForUser(ModelStoreSync modelStore, 
										String semesterId,
			                            String courseId, 
			                            User user) {

		T.call(CourseListUpdater.class);
		
		openQueueForUserId(modelStore, semesterId, courseId, user.getId());
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

	public static List<CoursePath> getCourseList(ModelStoreSync modelStore, String semesterId, User user) {
		T.call(CourseListUpdater.class);

		List<CoursePath> courses = new ArrayList<>();
		
		if(modelStore.ifModelExists(CourseListModel.class, "admin", user.getId())) {
			
			CourseListModel model = modelStore.getModel(CourseListModel.class, "admin", user.getId());
			
			for(CourseItem item :  model.getCourses().getValue()) {

				courses.add(item.coursePath());
			}
		}

		return courses;
	}

}
