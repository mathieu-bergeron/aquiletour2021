package ca.aquiletour.server.backend.users;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.user.Admin;
import ca.aquiletour.core.models.user.Student;
import ca.aquiletour.core.models.user.Teacher;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.models.user_list.UserList;
import ca.aquiletour.core.models.user_uuid.UserIdByUuid;
import ca.aquiletour.core.models.user_uuid.UuidByUserId;
import ca.aquiletour.core.pages.course_list.student.CourseListModelStudent;
import ca.aquiletour.core.pages.course_list.teacher.CourseListModelTeacher;
import ca.aquiletour.core.pages.dashboard.student.models.DashboardModelStudent;
import ca.aquiletour.core.pages.dashboard.teacher.models.DashboardModelTeacher;
import ca.aquiletour.core.pages.semester_list.teacher.models.SemesterListModelTeacher;
import ca.aquiletour.server.AquiletourConfig;
import ca.aquiletour.server.backend.course_list.CourseListManager;
import ca.aquiletour.server.backend.dashboard.DashboardManager;
import ca.aquiletour.server.backend.group_list.GroupListManager;
import ca.aquiletour.server.backend.login.SessionManager;
import ca.aquiletour.server.backend.queue.QueueManager;
import ca.aquiletour.server.backend.semester_list.SemesterListManager;
import ca.ntro.backend.BackendError;
import ca.ntro.core.models.ModelInitializer;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.models.lambdas.Break;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.jdk.digest.PasswordDigest;
import ca.ntro.jdk.random.SecureRandomString;
import ca.ntro.services.ModelStoreSync;
import ca.ntro.services.Ntro;
import ca.ntro.stores.DocumentPath;

public class UserManager {
	
	private static int NUMBER_OF_COLLISIONS_BEFORE_INCREMENTING_USER_ID_LENGTH = 10;

	private synchronized static String generateUuid(ModelStoreSync modelStore) throws BackendError {
		T.call(UserManager.class);

		int userIdLength = Constants.DEFAULT_USER_ID_LENGTH;

		String uuid;
		int numberOfCollisions = -1;
		
		do {
			
			numberOfCollisions++;
			if(numberOfCollisions >= NUMBER_OF_COLLISIONS_BEFORE_INCREMENTING_USER_ID_LENGTH) {
				numberOfCollisions = -1;
				userIdLength++;
			}

			uuid = SecureRandomString.generate(userIdLength);

		} while(modelStore.ifModelExists(UserIdByUuid.class, "admin", uuid));

		return uuid;
	}

	private static String generateAndStoreUuid(ModelStoreSync modelStore, String userId) throws BackendError {
		T.call(UserManager.class);
		
		String uuid = generateUuid(modelStore);

		modelStore.createModel(UuidByUserId.class, "admin", userId, new ModelInitializer<UuidByUserId>() {
			@Override
			public void initialize(UuidByUserId newModel) {
				T.call(this);
				newModel.setUuid(uuid);
			}
		});

		modelStore.createModel(UserIdByUuid.class, "admin", uuid, new ModelInitializer<UserIdByUuid>() {
			@Override
			public void initialize(UserIdByUuid newModel) {
				T.call(this);
				newModel.setUserId(userId);
			}
		});

		return uuid;
	}

	public static void setUserPassword(ModelStoreSync modelStore, String newPassword, User user) throws BackendError {
		T.call(UserManager.class);

		if(modelStore.ifModelExists(User.class, "admin", user.getId())) {
			
			modelStore.updateModel(User.class, "admin", user.getId(), new ModelUpdater<User>() {
				@Override
				public void update(User userModel) {
					T.call(this);

					userModel.setPasswordHash(PasswordDigest.passwordDigest(newPassword));
					userModel.setHasPassword(true);
				}
			});

		}else {
			
			Log.warning("Model not found: " + user.getId());
		}
	}

	public static boolean isUserPasswordValid(ModelStoreSync modelStore, String password, User user) throws BackendError {
		T.call(UserManager.class);

		return modelStore.extractFromModel(User.class, "admin", user.getId(), Boolean.class, storedUser -> {
			return !storedUser.getHasPassword()
					|| storedUser.getPasswordHash().equals(PasswordDigest.passwordDigest(password));
		});
	}

	public static void createUsers(ModelStoreSync modelStore, List<User> usersToAdd) throws BackendError {
		T.call(UserManager.class);

		for(User user : usersToAdd) {
			createUser(modelStore, user);
		}
	}

	public static void createUser(ModelStoreSync modelStore, User user) throws BackendError {
		T.call(UserManager.class);

		if(modelStore.ifModelExists(User.class, "admin", user.getId())) {

			updateExistingUser(modelStore, user);

		}else{

            createStoredUser(modelStore, user);
			initializeUserModels(modelStore, user);
		}
	}

	private static void updateExistingUser(ModelStoreSync modelStore, User user) throws BackendError {
		T.call(UserManager.class);
		
		modelStore.updateModel(User.class, "admin", user.getId(), new ModelUpdater<User>() {
			@Override
			public void update(User existingUser) {
				existingUser.updateInfoIfEmpty(user);
			}
		});
	}

	private static void initializeUserModels(ModelStoreSync modelStore, User user) throws BackendError {
		T.call(UserManager.class);
		
		if(user instanceof Admin) {
			
			initializeAdminModels(modelStore, user);
			initializeTeacherModels(modelStore, user);
			initializeStudentModels(modelStore, user);
			
			storeTeacherId(modelStore, user);
			storeStudentId(modelStore, user);
			
		}else if(user instanceof Teacher) {
			
			initializeTeacherModels(modelStore, user);
			initializeStudentModels(modelStore, user);

			storeTeacherId(modelStore, user);
			storeStudentId(modelStore, user);

		} else if(user instanceof Student){

			initializeStudentModels(modelStore, user);
			
			storeStudentId(modelStore, user);
		}
	}

	private static void initializeStudentModels(ModelStoreSync modelStore, User user) throws BackendError {
		T.call(UserManager.class);

		DashboardManager.createDashboardForUser(modelStore, DashboardModelStudent.class, user);

		CourseListManager.createCourseListForUser(modelStore, CourseListModelStudent.class, user);
	}

	private static void storeStudentId(ModelStoreSync modelStore, User user) throws BackendError {
		T.call(UserManager.class);

		modelStore.updateModel(UserList.class, "admin", Constants.STUDENT_LIST_MODEL_ID, new ModelUpdater<UserList>() {
			@Override
			public void update(UserList existingModel) {
				T.call(this);
				existingModel.addUserId(user.getId());
			}
		});
	}

	private static void initializeAdminModels(ModelStoreSync modelStore, User user) {
		T.call(UserManager.class);
	}


	private static void initializeTeacherModels(ModelStoreSync modelStore, User user) throws BackendError {
		T.call(UserManager.class);

		QueueManager.createQueueForUser(modelStore, user);

		DashboardManager.createDashboardForUser(modelStore, DashboardModelTeacher.class, user);

		CourseListManager.createCourseListForUser(modelStore, CourseListModelTeacher.class, user);

		GroupListManager.createGroupListForUser(modelStore, user);

		SemesterListManager.createSemesterListForUser(modelStore, SemesterListModelTeacher.class, user);
		SemesterListManager.addManagedSemestersForTeacher(modelStore, user);
	}

	private static void storeTeacherId(ModelStoreSync modelStore, User user) throws BackendError {
		T.call(UserManager.class);

		modelStore.updateModel(UserList.class, "admin", Constants.TEACHER_LIST_MODEL_ID, new ModelUpdater<UserList>() {
			@Override
			public void update(UserList existingModel) {
				T.call(this);
				existingModel.addUserId(user.getId());
			}
		});
	}

	private static void createStoredUser(ModelStoreSync modelStore, User user) {
		T.call(UserManager.class);

		DocumentPath documentPath = new DocumentPath();
		documentPath.setCollection(Ntro.introspector().getSimpleNameForClass(User.class));
		documentPath.setDocumentId(user.getId());
		modelStore.saveJsonString(documentPath, Ntro.jsonService().toString(user));
	}

	public static void updateScreenName(ModelStoreSync modelStore, String screenName, User user) throws BackendError {
		T.call(UserManager.class);
		
		modelStore.updateModel(User.class, "admin", user.getId(), new ModelUpdater<User>() {
			@Override
			public void update(User existingModel) {
				T.call(this);
				
				existingModel.setFirstname(screenName);
				existingModel.setLastname("");
			}
		});
	}

	public static void toggleStudentMode(ModelStoreSync modelStore, User user) throws BackendError {
		T.call(UserManager.class);

		modelStore.updateModel(User.class, "admin", user.getId(), new ModelUpdater<User>() {
			@Override
			public void update(User userModel) {
				T.call(this);
				
				if(userModel instanceof Teacher) {
					Teacher teacher = (Teacher) userModel;
					teacher.toggleStudentMode();
				}
			}
		});
	}

	public static void toggleAdminMode(ModelStoreSync modelStore, User user) throws BackendError {
		T.call(UserManager.class);

		modelStore.updateModel(User.class, "admin", user.getId(), new ModelUpdater<User>() {
			@Override
			public void update(User userModel) {
				T.call(this);
				
				if(userModel instanceof Admin) {
					Admin admin = (Admin) userModel;
					admin.toggleAdminMode();
				}
			}
		});
	}


	public static void resetUserAfterLogout(ModelStoreSync modelStore, User user) throws BackendError {
		T.call(UserManager.class);

		modelStore.updateModel(User.class, "admin", user.getId(), new ModelUpdater<User>() {
			@Override
			public void update(User userModel) {
				T.call(this);

				userModel.resetAfterLogout();
			}
		});
	}

	public static void initialize(ModelStoreSync modelStore) throws BackendError {
		T.call(UserManager.class);

		if(!modelStore.ifModelExists(UserList.class, "admin", Constants.ADMIN_LIST_MODEL_ID)) {
			modelStore.createModel(UserList.class, "admin", Constants.ADMIN_LIST_MODEL_ID, new ModelInitializer<UserList>() {
				@Override
				public void initialize(UserList newModel) {
					T.call(this);
					
					AquiletourConfig config = (AquiletourConfig) Ntro.config();
					for(String adminId : config.getAdminRegistrationIds()){
						newModel.addUserId(adminId);
					}
				}
			});
		}

		if(!modelStore.ifModelExists(UserList.class, "admin", Constants.TEACHER_LIST_MODEL_ID)) {
			modelStore.createModel(UserList.class, "admin", Constants.TEACHER_LIST_MODEL_ID, new ModelInitializer<UserList>() {
				@Override
				public void initialize(UserList newModel) {
					T.call(this);
				}
			});
		}

		if(!modelStore.ifModelExists(UserList.class, "admin", Constants.STUDENT_LIST_MODEL_ID)) {
			modelStore.createModel(UserList.class, "admin", Constants.STUDENT_LIST_MODEL_ID, new ModelInitializer<UserList>() {
				@Override
				public void initialize(UserList newModel) {
					T.call(this);
				}
			});
		}
	}

	@SuppressWarnings("unchecked")
	public static Set<String> getAdminRegistrationIds(ModelStoreSync modelStore) throws BackendError {
		T.call(UserManager.class);
		
		Set<String> userIds = new HashSet<>();

		modelStore.readModel(UserList.class, "admin", Constants.ADMIN_LIST_MODEL_ID, model -> {
			userIds.addAll(model.userIds());
		});
		
		return userIds;
	}

	public static Student createStudentUsingRegistrationId(ModelStoreSync modelStore, 
									                       String firstName, 
									                       String lastName, 
									                       String userId, 
									                       String programId, 
									                       String phoneNumber, 
									                       String email) throws BackendError {
		T.call(UserManager.class);
		
		String uuid = modelStore.extractFromModel(UuidByUserId.class, "admin", userId, String.class, model -> {
			return model.getUuid();
		});
		
		if(uuid == null) {

			uuid = generateAndStoreUuid(modelStore, userId);
		}

		return createStudentForUserId(modelStore, 
									  userId,
									  uuid, 
									  firstName, 
									  lastName, 
									  programId, 
									  phoneNumber, 
									  email);
	}

	public static Student createStudentForUserId(ModelStoreSync modelStore, 
												 String userId,
												 String uuid,
												 String firstName, 
												 String lastName, 
												 String programId, 
												 String phoneNumber,
												 String email) throws BackendError {
		T.call(UserManager.class);
		
		Student student = createUser(modelStore, 
				                     Student.class, 
				                     userId, 
				                     uuid,
				                     firstName, 
				                     lastName, 
				                     email);
		
		student.updatePhoneNumberIfEmpty(phoneNumber);
		student.updateProgramIdIfEmpty(programId);
		
		return student;
	}

	public static <U extends User> U createUser(ModelStoreSync modelStore, 
										        Class<U> modelClass,
										        String userId,
										        String uuid,
										        String firstName, 
										        String lastName, 
										        String email) throws BackendError {
		T.call(UserManager.class);
		
		U user = null;
		
		if(modelStore.ifModelExists(User.class, "admin", userId)) {
			
			modelStore.updateModel(User.class, "admin", userId, storedUser -> {

				storedUser.updateInfoIfEmpty(firstName, lastName, email);
			});
			
			user = modelStore.extractFromModel(User.class, "admin", userId, modelClass, storedUser -> {
				return (U) storedUser;
			});
			
		}else {

			user = Ntro.factory().newInstance(modelClass);
			user.setId(userId);
			user.setUuid(uuid);
			user.updateInfoIfEmpty(firstName, lastName, email);
			createUser(modelStore, user);
		}

		return user;
	}

	public static void forEachTeacherId(ModelStoreSync modelStore, UserIdLambda lambda) throws BackendError {
		T.call(UserManager.class);

		modelStore.readModel(UserList.class, "admin", Constants.TEACHER_LIST_MODEL_ID, model -> {
			for(String teacherId : model.userIds()) {
				try {
					lambda.execute(teacherId);
				} catch (Break e) {
					break;
				}
			}
		});
	}

	public static boolean ifTeacherExists(ModelStoreSync modelStore, String teacherId) {
		T.call(UserManager.class);
		
		boolean ifExists = false;
		
		
		return ifExists;
	}

	public static boolean ifStudentExistsForRegistrationId(ModelStoreSync modelStore, String registrationId) throws BackendError {
		T.call(UserManager.class);
		
		boolean ifExists = false;

		if(modelStore.ifModelExists(UserIdByUuid.class, "admin", registrationId)) {
			ifExists = true;
		}
		
		return ifExists;
	}

	public static boolean ifStudentExistsForId(ModelStoreSync modelStore, String studentId) throws BackendError {
		T.call(UserManager.class);
		
		boolean ifExists = false;

		if(modelStore.ifModelExists(Student.class, "admin", studentId)) {
			ifExists = true;
		}
		
		return ifExists;
	}

	public static User getUserById(ModelStoreSync modelStore, String userId) throws BackendError {
		T.call(UserManager.class);
		
		return modelStore.extractFromModel(User.class, "admin", userId, User.class, storedUser -> {

			return storedUser;

		});
	}

	public static User getUserByUuid(ModelStoreSync modelStore, String uuid) throws BackendError {
		T.call(UserManager.class);
		
		User user = null;
		String userId = modelStore.extractFromModel(UserIdByUuid.class, "admin", uuid, String.class, userIdByUuid -> {
			return userIdByUuid.getUserId();
		});

		if(userId != null) {
			user = getUserById(modelStore, userId);
		}

		return user;
	}

	public static boolean ifStoredUserExists(ModelStoreSync modelStore, User user) throws BackendError {
		T.call(UserManager.class);
		
		return modelStore.ifModelExists(User.class, "admin", user.getId());
	}

	public static User getStoredUser(ModelStoreSync modelStore, User user) throws BackendError {
		T.call(UserManager.class);
		
		return getUserById(modelStore, user.getId());
		
	}

	public static User createGuestUser(ModelStoreSync modelStore, 
									   Class<? extends User> guestUserClass,
									   String userId) throws BackendError {
		T.call(UserManager.class);
		
		User newUser = Ntro.factory().newInstance(guestUserClass);
		newUser.setId(userId);
		
		User existingUser = UserManager.getUserById(modelStore, userId);

		if(existingUser != null) {

			newUser.copyPublicInfomation(existingUser);
			newUser.setId(existingUser.getId());
			newUser.setUuid(existingUser.getUuid());

		}else {

			String uuid = generateAndStoreUuid(modelStore, userId);
			newUser.setId(userId);
			newUser.setUuid(uuid);
			newUser.setFirstname(userId);
			newUser.setEmail(userId + "@" + Constants.EMAIL_HOST);
		}
		

		return newUser;
	}

	public static void updateUserWithStoredUserInfo(ModelStoreSync modelStore, 
												    User userToUpdate,
			                                        String userId) throws BackendError {
		T.call(UserManager.class);
		
		modelStore.readModel(User.class, "admin", userId, storedUser -> {

			userToUpdate.copyPublicInfomation(storedUser);
		});
	}

	public static void updateUserName(ModelStoreSync modelStore, 
			                          String firstName, 
			                          String lastName, 
			                          String userId) throws BackendError {
		T.call(UserManager.class);
		
		modelStore.updateModel(User.class, "admin", userId, user -> {
			
			if(!user.getHasName()) {
				
				if(firstName != null 
						&& !firstName.isEmpty()) {

					user.setFirstname(firstName);
					user.setHasName(true);
				}
				
				if(lastName != null
						&& !lastName.isEmpty()) {

					user.setLastname(lastName);
					user.setHasName(true);
				}
			}
		});
	}
}
