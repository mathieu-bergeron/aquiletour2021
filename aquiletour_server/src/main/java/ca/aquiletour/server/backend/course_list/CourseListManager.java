package ca.aquiletour.server.backend.course_list;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.aquiletour.core.models.paths.CoursePath;
import ca.aquiletour.core.models.session.SessionData;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.course_list.models.CourseListItem;
import ca.aquiletour.core.pages.course_list.models.CourseListModel;
import ca.aquiletour.core.pages.course_list.models.TaskDescription;
import ca.aquiletour.core.pages.course_list.student.CourseListModelStudent;
import ca.aquiletour.core.pages.course_list.teacher.CourseListModelTeacher;
import ca.aquiletour.server.backend.course.CourseManager;
import ca.ntro.backend.BackendError;
import ca.ntro.core.models.ModelInitializer;
import ca.ntro.core.models.ModelReader;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.models.lambdas.Break;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.wrappers.options.EmptyOptionException;
import ca.ntro.core.wrappers.options.Optionnal;
import ca.ntro.services.ModelStoreSync;

public class CourseListManager {

	public static void validateCourseListItem(CourseListItem courseDescription) throws BackendError {
		T.call(CourseListManager.class);
		
		// forbidden chars: ' " ¤ /

		if(courseDescription.getCourseId().contains(" ")) {
			throw new BackendError("Le code de cours ne doit pas contenir d'espace");
		}
	}

	/*
	public static <CLM extends CourseListModel> void addSemesterForUser(ModelStoreSync modelStore, 
																	    Class<CLM> courseListModelClass,
			                                                            String semesterId, 
			                                                            User user) throws BackendError {

		T.call(CourseListManager.class);
		
		addSemesterForUserId(modelStore,courseListModelClass, semesterId, user.getId());
	}*/
	
	/*
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
	}*/

	public static <CLM extends CourseListModel> void addCourseToCategory(ModelStoreSync modelStore, 
			 														     Class<CLM> courseListModelClass,
			 														     String categoryId,
			                                                             CourseListItem courseDescription, 
			                                                             User teacher) throws BackendError {
		T.call(CourseListManager.class);
		
		modelStore.updateModel(courseListModelClass, "admin", teacher.getId(), new ModelUpdater<CLM>() {
			@Override
			public void update(CLM existingModel) {
				T.call(this);
				
				existingModel.addCourseToCategory(categoryId, courseDescription);
			}
		});
	}

	public static <CLM extends CourseListModel> void addGroupForUser(ModelStoreSync modelStore, 
																     Class<CLM> courseListModelClass, 
																     CoursePath coursePath,
																     String groupId, 
																     User user) throws BackendError {

		T.call(CourseListManager.class);
		
		addGroupForUserId(modelStore, courseListModelClass, coursePath, groupId, user.getId());
		
	}

	public static <CLM extends CourseListModel> void addGroupForUserId(ModelStoreSync modelStore, 
																	   Class<CLM> courseListModelClass, 
																	   CoursePath coursePath,
																	   String groupId, 
																	   String userId) throws BackendError {

		T.call(CourseListManager.class);

		modelStore.updateModel(courseListModelClass, "admin", userId, new ModelUpdater<CLM>() {
			@Override
			public void update(CLM existingModel) {
				T.call(this);

				existingModel.addGroup(coursePath, groupId);
			}
		});
	}

	public static <CLM extends CourseListModel> void updateQueueOpenForUserId(ModelStoreSync modelStore, 
			 															      Class<CLM> courseListModelClass, 
			 															      CoursePath coursePath,
			 															      boolean queueOpen,
			 															      String userId) throws BackendError {

		T.call(CourseListManager.class);

		modelStore.updateModel(courseListModelClass, "admin", userId, new ModelUpdater<CLM>() {
			@Override
			public void update(CLM existingModel) {
				T.call(this);
				
				existingModel.updateQueueOpen(coursePath, queueOpen);
			}
		});
	}
	
	public static <CLM extends CourseListModel> void updateQueueOpenForUser(ModelStoreSync modelStore, 
																            Class<CLM> courseListModelClass, 
																            CoursePath coursePath,
																            boolean queueOpen,
																            User user) throws BackendError {

		T.call(CourseListManager.class);
		
		updateQueueOpenForUserId(modelStore, courseListModelClass, coursePath, queueOpen, user.getId());
	}
	

	public static <CLM extends CourseListModel> String getCourseTitle(ModelStoreSync modelStore, 
																      Class<CLM> courseListModelClass, 
																      CoursePath coursePath,
																      String userId) { 
		T.call(CourseListManager.class);
		
		return getCourseItem(modelStore, courseListModelClass, coursePath, userId).getCourseTitle();
	}

	public static <CLM extends CourseListModel> CourseListItem getCourseItem(ModelStoreSync modelStore, 
																             Class<CLM> courseListModelClass, 
																             CoursePath coursePath,
																             String userId) {

		T.call(CourseListManager.class);

		return modelStore.reduceModel(courseListModelClass, 
				                      "admin", 
				                      userId, 
				                      CourseListItem.class, 
				                      null, 
				                      (model, accumulator) -> {
			return model.courseByPath(coursePath);
		});
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
																			   String userId) throws BackendError {
		T.call(CourseListManager.class);

		List<CoursePath> courses = new ArrayList<>();
		
		modelStore.readModel(courseListModelClass, "admin", userId, new ModelReader<CLM>() {
			@Override
			public void read(CLM model) {
				T.call(this);

				model.getCourseListItemsByCategoryId().forEachEntry((categoryId, courseItems) -> {
					courseItems.forEachItem((index, courseItem) -> {

						courses.add(courseItem.coursePath());
					});
				});
			}
		});

		return courses;
	}

	public static <CLM extends CourseListModel> List<CoursePath> getCourseList(ModelStoreSync modelStore, 
																			   Class<CLM> courseListModelClass, 
																			   String semesterId, 
																			   User user) throws BackendError {
		T.call(CourseListManager.class);
		
		return getCourseList(modelStore, courseListModelClass, semesterId, user.getId());
	}

	public static <CLM extends CourseListModel> void createCourseListForModelId(ModelStoreSync modelStore, 
			                                                               Class<CLM> modelClass, 
			                                                               String modelId) throws BackendError {

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
			                                                            User user) throws BackendError {

		T.call(CourseListManager.class);
		
		createCourseListForModelId(modelStore, modelClass, user.getId());
	}

	public static void updateSessionData(ModelStoreSync modelStore, SessionData sessionData, User user) throws BackendError {
		T.call(CourseListManager.class);
		
		modelStore.readModel(CourseListModelStudent.class, "admin", user.getId(), new ModelReader<CourseListModelStudent>() {
			@Override
			public void read(CourseListModelStudent courseListModel) throws BackendError {
				T.call(this);
				
				Optionnal<BackendError> backendError = new Optionnal<>();

				courseListModel.getCourseListItemsByCategoryId().forEachEntry((categoryId, courseItems) -> {
					courseItems.forEachItem((index, courseItem) -> {

						try {

							CourseManager.updateSessionData(modelStore, sessionData, courseItem.coursePath(), user);

						} catch (BackendError e) {
							backendError.set(e);
						}
					});
				});
				
				try {

					throw backendError.get();

				} catch (EmptyOptionException e) {}
			}
		});
	}

	public static <CLM extends CourseListModel>  void setActiveSemestersForModelId(ModelStoreSync modelStore, 
																				   Class<CLM> modelClass,
																				   String semesterId, 
																				   boolean isActive, 
																				   String userId) throws BackendError {
		T.call(CourseListModel.class);
		
		modelStore.updateModel(modelClass, "admin", userId, new ModelUpdater<CLM>(){
			@Override
			public void update(CLM courseListModel) throws BackendError {
				T.call(this);
				
				if(isActive) {
					
					courseListModel.addActiveSemester(semesterId);
					
				}else {

					courseListModel.removeActiveSemester(semesterId);
				}
			}
		});
	}

	public static Set<String> getStudentIds(ModelStoreSync modelStore, 
			 								String teacherId) throws BackendError {
		
		Set<String> studentIds = new HashSet<>();

		modelStore.readModel(CourseListModelTeacher.class, "admin", teacherId, new ModelReader<CourseListModelTeacher>() {
			@Override
			public void read(CourseListModelTeacher courseListModel) throws BackendError {
				T.call(this);
				
				Optionnal<BackendError> backendError = new Optionnal<>();
				
				courseListModel.getCourseListItemsByCategoryId().forEachEntry((categoryId, courseListItems) -> {

					courseListItems.forEachItem((index, courseListItem) -> {

						CoursePath coursePath = courseListItem.coursePath();

						try {

							studentIds.addAll(CourseManager.getStudentIds(modelStore, coursePath));

						} catch (BackendError e) {
							backendError.set(e);
							throw new Break();
						}
					});
				});
				
				try {
					throw backendError.get();
				} catch (EmptyOptionException e) {}
			}
		});
		
		return studentIds;
	}

	public static <CLM extends CourseListModel>  void setActiveSemestersForTeacherId(ModelStoreSync modelStore, 
																				     Class<CLM> modelClass,
																				     String semesterId, 
																				     boolean isActive, 
																				     String teacherId) throws BackendError {
		T.call(CourseListModel.class);

		setActiveSemestersForModelId(modelStore, modelClass, semesterId, isActive, teacherId);

		Set<String> studentIds = getStudentIds(modelStore, teacherId);

		for(String studentId : studentIds) {
			setActiveSemestersForModelId(modelStore, modelClass, semesterId, isActive, studentId);
		}
	}

}
