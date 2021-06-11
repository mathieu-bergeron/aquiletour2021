package ca.aquiletour.server.backend.semester_list;

import java.util.List;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.dates.CalendarWeek;
import ca.aquiletour.core.models.schedule.ScheduleItem;
import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.aquiletour.core.models.session.SessionData;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.semester_list.admin.models.SemesterListModelAdmin;
import ca.aquiletour.core.pages.semester_list.admin.models.SemesterModelAdmin;
import ca.aquiletour.core.pages.semester_list.models.SemesterListModel;
import ca.aquiletour.core.pages.semester_list.models.SemesterModel;
import ca.aquiletour.core.pages.semester_list.teacher.models.SemesterListModelTeacher;
import ca.aquiletour.core.pages.semester_list.teacher.models.SemesterModelTeacher;
import ca.ntro.backend.BackendError;
import ca.ntro.core.models.ModelInitializer;
import ca.ntro.core.models.ModelReader;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.models.lambdas.Break;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.wrappers.options.EmptyOptionException;
import ca.ntro.core.wrappers.options.Optionnal;
import ca.ntro.services.ModelStoreSync;
import ca.ntro.services.Ntro;

public class SemesterListManager {

	public static void addScheduleItemForUser(ModelStoreSync modelStore, 
			                                  String semesterId, 
			                                  ScheduleItem scheduleItem, 
			                                  User user) throws BackendError {

		T.call(SemesterListManager.class);
		
		addScheduleItemForUserId(modelStore, semesterId, scheduleItem, user.getId());
	}

	public static void addScheduleItemForUserId(ModelStoreSync modelStore, 
			                                    String semesterId, 
			                                    ScheduleItem scheduleItem,
			                                    String userId) throws BackendError {

		T.call(SemesterListManager.class);

		modelStore.updateModel(SemesterListModelTeacher.class, 
							   "admin",
							   userId,
							   new ModelUpdater<SemesterListModelTeacher>() {

			@Override
			public void update(SemesterListModelTeacher semesterList) {
				T.call(this);
				
				semesterList.addScheduleItem(semesterId, scheduleItem);
			}
		});
	}
	
	public static <SL extends SemesterListModel<?>>  void addSemesterWeekForUser(ModelStoreSync modelStore, 
																		      Class<SL> modelClass, 
																		      String semesterId, 
																		      CalendarWeek semesterWeek, 
																		      User user) throws BackendError {

		T.call(SemesterListManager.class);
		
		addSemesterWeekToModel(modelStore, modelClass, semesterId, semesterWeek, user.getId());
	}

	public static <SL extends SemesterListModel<?>>  void addSemesterWeekToModel(ModelStoreSync modelStore, 
			                                                                  Class<SL> modelClass, 
			                                                                  String semesterId, 
			                                                                  CalendarWeek semesterWeek, 
			                                                                  String modelId) throws BackendError {
		T.call(SemesterListManager.class);

		modelStore.updateModel(modelClass, 
							   "admin",
							   modelId,
							   semesterList -> {

			semesterList.addSemesterWeek(semesterId, semesterWeek);
								   
	   });
	}

	public static <SL extends SemesterListModel<?>>  void deleteSemesterFromModel(ModelStoreSync modelStore, 
																			   Class<SL> modelClass, 
																			   String semesterId, 
																			   String modelId) throws BackendError {

		T.call(SemesterListManager.class);
		
		modelStore.updateModel(modelClass, 
							   "admin",
							   modelId,
							   new ModelUpdater<SL>() {

			@Override
			public void update(SL semesterList) {
				T.call(this);

				semesterList.removeSemester(semesterId);
			}
		});
	}

	public static 
	       <SM extends SemesterModel, SL extends SemesterListModel<SM>> 
	       void 
	       addSemesterToModel(ModelStoreSync modelStore, 
							  Class<SL> modelClass, 
							  Class<SM> semesterModelClass,
							  String semesterId, 
							  boolean adminControlled,
							  String userId) throws BackendError {

		T.call(SemesterListManager.class);
		
		modelStore.updateModel(modelClass, 
							   "admin",
							   userId,
							   new ModelUpdater<SL>() {

			@Override
			public void update(SL semesterList) {
				T.call(this);
				
				SM semester = Ntro.factory().newInstance(semesterModelClass);
				semester.setSemesterId(semesterId);
				if(semester instanceof SemesterModelTeacher) {
					((SemesterModelTeacher) semester).setAdminControlled(adminControlled);
				}
				semesterList.addSemester(semester);
			}
		});
	}

	public static 
	       <SM extends SemesterModel, SL extends SemesterListModel<SM>> 
	       void 
	       addSemesterToModel(ModelStoreSync modelStore, 
							  Class<SL> modelClass, 
							  Class<SM> semesterModelClass,
							  SemesterModel semesterToAdd,
							  boolean adminControlled,
							  String userId) throws BackendError {

		T.call(SemesterListManager.class);
		
		modelStore.updateModel(modelClass, 
							   "admin",
							   userId,
							   new ModelUpdater<SL>() {

			@Override
			public void update(SL semesterList) {
				T.call(this);
				
				SM semester = Ntro.factory().newInstance(semesterModelClass);
				semester.setSemesterId(semesterToAdd.getSemesterId());
				semester.setSemesterSchedule(semesterToAdd.getSemesterSchedule());
				if(semester instanceof SemesterModelTeacher) {
					((SemesterModelTeacher) semester).setAdminControlled(adminControlled);
				}
				semesterList.addSemester(semester);
			}
		});
	}

	public static <SL extends SemesterListModel<?>> void createSemesterListForUser(ModelStoreSync modelStore, 
			                                                                  Class<SL> modelClass, 
			                                                                  User user) throws BackendError {

		T.call(SemesterListManager.class);
		
		createSemesterListForModelId(modelStore, modelClass, user.getId());
	}

	public static void addManagedSemestersForTeacher(ModelStoreSync modelStore, 
			                                         User user) throws BackendError {

		T.call(SemesterListManager.class);
		
		addManagedSemestersToModel(modelStore, user.getId());
	}

	public static void addManagedSemestersToModel(ModelStoreSync modelStore, 
			                                      String modelId) throws BackendError {

		T.call(SemesterListManager.class);

		Optionnal<BackendError> backendError = new Optionnal<>();
		
		modelStore.readModel(SemesterListModelAdmin.class, "admin", Constants.ADMIN_CONTROLLED_SEMESTER_LIST_ID, semesterList -> {

			semesterList.getSemesters().forEachItem((index, semester) -> {
				try {

					addSemesterToModel(modelStore, SemesterListModelTeacher.class, SemesterModelTeacher.class, semester, true, modelId);

				}catch(BackendError e) {
					backendError.set(e);
					throw new Break();
				}
			});
		});

		if(!backendError.isEmpty()) {
			try {
				throw backendError.get();
			} catch (EmptyOptionException e) {}
		}
	}

	public static <SL extends SemesterListModel<?>> void createSemesterListForModelId(ModelStoreSync modelStore, 
			                                                                     Class<SL> modelClass, 
			                                                                     String modelId) throws BackendError {

		T.call(SemesterListManager.class);

		modelStore.createModel(modelClass, "admin", modelId, new ModelInitializer<SL>() {
			@Override
			public void initialize(SL newModel) {
				T.call(this);
			}
		});
	}

	public static <SL extends SemesterListModel<?>>  void deleteSemesterForUser(ModelStoreSync modelStore, 
																		        Class<SL> modelClass,
			 														            String semesterId, 
			 														            User user) throws BackendError {

		T.call(SemesterListManager.class);
		
		deleteSemesterFromModel(modelStore, modelClass, semesterId, user.getId());
	}

	public static 
	       <SL extends SemesterListModel<SM>, SM extends SemesterModel>  
	       void addSemesterForUser(ModelStoreSync modelStore, 
					               Class<SL> modelClass, 
					               Class<SM> semesterModelClass,
					               String semesterId, 
					               boolean adminControlled,
					               User user) throws BackendError {

		T.call(SemesterListManager.class);
		
		addSemesterToModel(modelStore, modelClass, semesterModelClass, semesterId, adminControlled, user.getId());
	}

	public static <SL extends SemesterListModel<?>>  void setActiveSemestersForModelId(ModelStoreSync modelStore, 
																				   Class<SL> modelClass,
																				   String semesterId, 
																				   boolean isActive, 
																				   String userId) throws BackendError {
		T.call(SemesterListManager.class);

		if(!modelStore.ifModelExists(modelClass, "admin", userId)) {
			modelStore.createModel(modelClass, "admin", userId, new ModelInitializer<SL>() {
				@Override
				public void initialize(SL newModel) {
					T.call(this);
				}
			});
		}

		modelStore.updateModel(modelClass, 
							   "admin",
							   userId,
							   new ModelUpdater<SL>() {

			@Override
			public void update(SL semesterList) {
				T.call(this);
				
				semesterList.updateActiveSemesterId(semesterId, isActive);
			}
		});
	}


	public static <SL extends SemesterListModel<?>> void setActiveSemesterForUser(ModelStoreSync modelStore, 
																					Class<SL> modelClass, 
																					String semesterId, 
																					boolean currentSemester, 
																					User user) throws BackendError {
		T.call(SemesterListManager.class);
		
		setActiveSemestersForModelId(modelStore, modelClass, semesterId, currentSemester, user.getId());
	}
	
	
	public static void addCourseGroupForUserId(ModelStoreSync modelStore, 
			                                   String semesterId, 
			                                   String courseId, 
			                                   String groupId, 
			                                   String userId) throws BackendError {

		T.call(SemesterListManager.class);

		modelStore.updateModel(SemesterListModelTeacher.class, 
							   "admin",
							   userId,
							   new ModelUpdater<SemesterListModelTeacher>() {

			@Override
			public void update(SemesterListModelTeacher semesterList) {
				T.call(this);

				semesterList.addCourseGroup(semesterId, courseId, groupId);
			}
		});
	}
	

	public static void addCourseGroupForUser(ModelStoreSync modelStore, 
			                                 String semesterId, 
			                                 String courseId, 
			                                 String groupId, 
			                                 User user) throws BackendError {

		T.call(SemesterListManager.class);
		
		addCourseGroupForUserId(modelStore, semesterId, courseId, groupId, user.getId());
	}

	public static <SL extends SemesterListModel<?>> SemesterSchedule getSemesterSchedule(ModelStoreSync modelStore, 
																					  Class<SL> modelClass, 
																					  String semesterId, 
																					  String userId) throws BackendError {
		T.call(SemesterListManager.class);
		
		return modelStore.extractFromModel(modelClass,"admin" , userId, SemesterSchedule.class, model -> {

			return model.semesterSchedule(semesterId);

		});
	}

	public static <SL extends SemesterListModel<?>> SemesterSchedule getSemesterSchedule(ModelStoreSync modelStore, 
																					  Class<SL> modelClass, 
																					  String semesterId, 
																					  User user) throws BackendError {
		T.call(SemesterListManager.class);
		
		return getSemesterSchedule(modelStore, modelClass, semesterId, user.getId());
	}

	public static TeacherSchedule getTeacherSchedule(ModelStoreSync modelStore, 
			                                         String semesterId, 
			                                         String userId) throws BackendError {
		T.call(SemesterListManager.class);
		
		return modelStore.extractFromModel(SemesterListModelTeacher.class, "admin", userId, TeacherSchedule.class, model -> {
			return model.teacherSchedule(semesterId);
		});
	}

	public static TeacherSchedule getTeacherSchedule(ModelStoreSync modelStore, 
			                                         String semesterId, 
			                                         User user) throws BackendError {
		T.call(SemesterListManager.class);
		
		return getTeacherSchedule(modelStore, semesterId, user.getId());
	}

	public static void initialize(ModelStoreSync modelStore) throws BackendError {
		T.call(SemesterListManager.class);
		
		createSemesterListForModelId(modelStore, SemesterListModelAdmin.class, Constants.ADMIN_CONTROLLED_SEMESTER_LIST_ID);
	}

	public static void addActiveSemesterIds(ModelStoreSync modelStore, List<String> activeSemesterIds) throws BackendError {
		T.call(SemesterListManager.class);
		
		modelStore.readModel(SemesterListModel.class, "admin", Constants.ADMIN_CONTROLLED_SEMESTER_LIST_ID, new ModelReader<SemesterListModel>() {
			@Override
			public void read(@SuppressWarnings("rawtypes") SemesterListModel existingModel) {
				T.call(this);

				existingModel.getActiveSemesterIds().forEachItem((index, semesterId) -> {
					activeSemesterIds.add(semesterId);
				});
			}
		});
	}
}
