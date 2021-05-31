package ca.aquiletour.server.backend.course_list;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.models.session.SessionData;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.course_list.models.CourseListItem;
import ca.aquiletour.core.pages.course_list.models.CourseListModel;
import ca.aquiletour.core.pages.course_list.models.TaskDescription;
import ca.aquiletour.core.pages.course_list.student.CourseListModelStudent;
import ca.aquiletour.server.backend.course.CourseManager;
import ca.ntro.backend.BackendError;
import ca.ntro.core.models.ModelInitializer;
import ca.ntro.core.models.ModelReader;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.trace.T;

public class CourseListManager {

	public static void validateCourseListItem(CourseListItem courseDescription) throws BackendError {
		T.call(CourseListManager.class);
		
		// forbidden chars: ' " ¤ /

		if(courseDescription.getCourseId().contains(" ")) {
			throw new BackendError("Le code de cours ne doit pas contenir d'espace");
		}
	}

	public static <CLM extends CourseListModel> void addSemesterForUser(ModelStoreSync modelStore, 
																	    Class<CLM> courseListModelClass,
			                                                            String semesterId, 
			                                                            User user) throws BackendError {

		T.call(CourseListManager.class);
		
		addSemesterForUserId(modelStore,courseListModelClass, semesterId, user.getId());
	}
	
	public static <CLM extends CourseListModel> void addSemesterForUserId(ModelStoreSync modelStore, 
			                                                              Class<CLM> courseListModelClass,
			                                                              String semesterId, 
			                                                              String userId) throws BackendError {

		T.call(CourseListManager.class);

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
			                                                          CourseListItem courseDescription, 
			                                                          User teacher) throws BackendError {
		T.call(CourseListManager.class);
		
		modelStore.updateModel(courseListModelClass, "admin", teacher.getId(), new ModelUpdater<CLM>() {
			@Override
			public void update(CLM existingModel) {
				T.call(this);
				
				existingModel.addCourse(courseDescription);
			}
		});
	}

	public static <CLM extends CourseListModel> void addGroupForUser(ModelStoreSync modelStore, 
																     Class<CLM> courseListModelClass, 
																     String semesterId, 
																     String courseId, 
																     String groupId, 
																     User user) throws BackendError {

		T.call(CourseListManager.class);
		
		addGroupForUserId(modelStore, courseListModelClass, semesterId, courseId, groupId, user.getId());
		
	}

	public static <CLM extends CourseListModel> void addGroupForUserId(ModelStoreSync modelStore, 
																	   Class<CLM> courseListModelClass, 
																	   String semesterId, 
																	   String courseId, 
																	   String groupId, 
																	   String userId) throws BackendError {

		T.call(CourseListManager.class);

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
																		 String userId) throws BackendError {

		T.call(CourseListManager.class);

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
			 														   User user) throws BackendError { 
		T.call(CourseListManager.class);
		
		closeQueueForUserId(modelStore, courseListModelClass, semesterId, courseId, user.getId());
	}
	

	public static <CLM extends CourseListModel> void openQueueForUserId(ModelStoreSync modelStore, 
			 															Class<CLM> courseListModelClass, 
			 															String semesterId, 
			 															String courseId, 
			 															String userId) throws BackendError {

		T.call(CourseListManager.class);

		modelStore.updateModel(courseListModelClass, "admin", userId, new ModelUpdater<CLM>() {
			@Override
			public void update(CLM existingModel) {
				T.call(this);
				
				existingModel.openQueue(semesterId, courseId);

			}
		});
	}
	
	public static <CLM extends CourseListModel> void openQueueForUser(ModelStoreSync modelStore, 
																      Class<CLM> courseListModelClass, 
																      String semesterId, 
																      String courseId, 
																      User user) throws BackendError {

		T.call(CourseListManager.class);
		
		openQueueForUserId(modelStore, courseListModelClass, semesterId, courseId, user.getId());
	}
	

	public static <CLM extends CourseListModel> String getCourseTitle(ModelStoreSync modelStore, 
																      Class<CLM> courseListModelClass, 
																      String semesterId, 
																      String courseId, 
																      String userId) { 
		T.call(CourseListManager.class);
		
		return getCourseItem(modelStore, courseListModelClass, semesterId, courseId, userId).getCourseTitle();
	}

	public static <CLM extends CourseListModel> CourseListItem getCourseItem(ModelStoreSync modelStore, 
																         Class<CLM> courseListModelClass, 
																         String semesterId, 
																         String courseId, 
																         String userId) {

		T.call(CourseListManager.class);
		
		CourseListModel model = modelStore.getModel(courseListModelClass, "admin", userId);
		
		return model.courseById(semesterId, courseId);
	}
	
	public static <CLM extends CourseListModel> void addTask(ModelStoreSync modelStore, 
			 												 Class<CLM> courseListModelClass,
			                                                 CoursePath coursePath, 
			                                                 TaskDescription task) throws BackendError {
		T.call(CourseListManager.class);
		
		modelStore.updateModel(courseListModelClass, "admin", coursePath.teacherId(), new ModelUpdater<CLM>() {
			@Override
			public void update(CLM courseList) {
				T.call(this);
				
				courseList.addTask(coursePath, task);
			}
		});
	}

	public static <CLM extends CourseListModel> List<CoursePath> getCourseList(ModelStoreSync modelStore, 
																			   Class<CLM> courseListModelClass, 
																			   String semesterId, 
																			   String userId) {
		T.call(CourseListManager.class);

		List<CoursePath> courses = new ArrayList<>();
		
		if(modelStore.ifModelExists(courseListModelClass, "admin", userId)) {
			
			CLM model = modelStore.getModel(courseListModelClass, "admin", userId);
			
			for(CourseListItem item :  model.getCourses().getValue()) {

				courses.add(item.coursePath());
			}
		}

		return courses;

	}

	public static <CLM extends CourseListModel> List<CoursePath> getCourseList(ModelStoreSync modelStore, 
																			   Class<CLM> courseListModelClass, 
																			   String semesterId, 
																			   User user) {
		T.call(CourseListManager.class);
		
		return getCourseList(modelStore, courseListModelClass, semesterId, user.getId());
	}

	public static <CLM extends CourseListModel> void createCourseListForModelId(ModelStoreSync modelStore, 
			                                                               Class<CLM> modelClass, 
			                                                               String modelId) {

		T.call(CourseListManager.class);

		modelStore.createModel(modelClass, "admin", modelId, new ModelInitializer<CLM>() {
			@Override
			public void initialize(CLM newModel) {
				T.call(this);
			}
		});
	}

	public static <CLM extends CourseListModel> void createCourseListForUser(ModelStoreSync modelStore, 
			                                                            Class<CLM> modelClass, 
			                                                            User user) {

		T.call(CourseListManager.class);
		
		createCourseListForModelId(modelStore, modelClass, user.getId());
	}

	public static void updateSessionData(ModelStoreSync modelStore, SessionData sessionData, User user) {
		T.call(CourseListManager.class);
		
		modelStore.readModel(CourseListModelStudent.class, "admin", user.getId(), new ModelReader<CourseListModelStudent>() {
			@Override
			public void read(CourseListModelStudent courseListModel) {
				T.call(this);
				
				courseListModel.getCourses().forEachItem((index, courseListItem) -> {
					T.call(this);
					
					CourseManager.updateSessionData(modelStore, sessionData, courseListItem.coursePath(), user);
				});
			}
		});
		
	}

}
