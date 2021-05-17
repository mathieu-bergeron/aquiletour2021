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
import ca.aquiletour.core.models.user_registration.RegistrationId;
import ca.aquiletour.core.models.user_registration.UserId;
import ca.aquiletour.core.pages.course_list.student.CourseListStudent;
import ca.aquiletour.core.pages.course_list.teacher.CourseListTeacher;
import ca.aquiletour.core.pages.dashboard.student.models.DashboardStudent;
import ca.aquiletour.core.pages.dashboard.teacher.models.DashboardTeacher;
import ca.aquiletour.core.pages.semester_list.teacher.models.SemesterListTeacher;
import ca.aquiletour.server.AquiletourConfig;
import ca.aquiletour.server.backend.course_list.CourseListManager;
import ca.aquiletour.server.backend.dashboard.DashboardManager;
import ca.aquiletour.server.backend.group_list.GroupListManager;
import ca.aquiletour.server.backend.queue.QueueManager;
import ca.aquiletour.server.backend.semester_list.SemesterListManager;
import ca.ntro.core.models.ModelInitializer;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.jdk.digest.PasswordDigest;
import ca.ntro.jdk.random.SecureRandomString;
import ca.ntro.services.Ntro;
import ca.ntro.stores.DocumentPath;

public class UserManager {
	
	private static int USER_ID_LENGTH = Constants.DEFAULT_USER_ID_LENGTH;
	private static int NUMBER_OF_COLLISIONS_BEFORE_INCREMENTING_USER_ID_LENGTH = 10;

	private synchronized static String generateUniqueUserId(ModelStoreSync modelStore) {
		T.call(UserManager.class);
		
		String uniqueId;
		int numberOfCollisions = -1;
		
		do {
			
			numberOfCollisions++;
			if(numberOfCollisions >= NUMBER_OF_COLLISIONS_BEFORE_INCREMENTING_USER_ID_LENGTH) {
				numberOfCollisions = -1;
				USER_ID_LENGTH++;
			}

			uniqueId = SecureRandomString.generate(USER_ID_LENGTH);

		} while(modelStore.ifModelExists(User.class, "admin", uniqueId));

		return uniqueId;
	}

	private static String generateAndStoreUserId(ModelStoreSync modelStore, String registrationId) {
		T.call(UserManager.class);
		
		String userId = generateUniqueUserId(modelStore);
		
		modelStore.createModel(RegistrationId.class, "admin", userId, new ModelInitializer<RegistrationId>() {
			@Override
			public void initialize(RegistrationId newModel) {
				T.call(this);
				newModel.setRegistrationId(registrationId);
			}
		});

		modelStore.createModel(UserId.class, "admin", registrationId, new ModelInitializer<UserId>() {
			@Override
			public void initialize(UserId newModel) {
				T.call(this);
				newModel.setUserId(userId);
			}
		});

		return userId;
	}

	public static void setUserPassword(ModelStoreSync modelStore, String newPassword, User user) {
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

	public static boolean isUserPasswordValid(ModelStoreSync modelStore, String password, User user) {
		T.call(UserManager.class);
		
		boolean isValid = false;

		if(modelStore.ifModelExists(User.class, "admin", user.getId())) {
			
			User userModel = modelStore.getModel(User.class, "admin", user.getId());

			if(!userModel.getHasPassword()
					|| userModel.getPasswordHash().equals(PasswordDigest.passwordDigest(password))) {
				isValid = true;
			}

		}else {
			
			Log.warning("Model not found in isUserPasswordValid: " + user.getId());
		}
		
		return isValid;
	}

	public static void createUsers(ModelStoreSync modelStore, List<User> usersToAdd) {
		T.call(UserManager.class);

		for(User user : usersToAdd) {
			createUser(modelStore, user);
		}
	}

	public static void createUser(ModelStoreSync modelStore, User user) {
		T.call(UserManager.class);

		if(modelStore.ifModelExists(User.class, "admin", user.getId())) {

			updateExistingUser(modelStore, user);

		}else{

            createStoredUser(modelStore, user);
			initializeUserModels(modelStore, user);
		}
	}

	private static void updateExistingUser(ModelStoreSync modelStore, User user) {
		T.call(UserManager.class);
		
		modelStore.updateModel(User.class, "admin", user.getId(), new ModelUpdater<User>() {
			@Override
			public void update(User existingUser) {
				existingUser.updateInfoIfEmpty(user);
			}
		});
	}

	private static void initializeUserModels(ModelStoreSync modelStore, User user) {
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

	private static void initializeStudentModels(ModelStoreSync modelStore, User user) {
		T.call(UserManager.class);

		DashboardManager.createDashboardForUser(modelStore, DashboardStudent.class, user);

		CourseListManager.createCourseListForUser(modelStore, CourseListStudent.class, user);
	}

	private static void storeStudentId(ModelStoreSync modelStore, User user) {
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


	private static void initializeTeacherModels(ModelStoreSync modelStore, User user) {
		T.call(UserManager.class);

		QueueManager.createQueue(modelStore, user.getRegistrationId(), user);

		DashboardManager.createDashboardForUser(modelStore, DashboardTeacher.class, user);

		CourseListManager.createCourseListForUser(modelStore, CourseListTeacher.class, user);

		GroupListManager.createGroupListForUser(modelStore, user);

		SemesterListManager.createSemesterListForUser(modelStore, SemesterListTeacher.class, user);
		SemesterListManager.addManagedSemestersForTeacher(modelStore, user);
	}

	private static void storeTeacherId(ModelStoreSync modelStore, User user) {
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

	public static void updateScreenName(ModelStoreSync modelStore, String screenName, User user) {
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

	public static void toggleStudentMode(ModelStoreSync modelStore, User user) {
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

	public static void toggleAdminMode(ModelStoreSync modelStore, User user) {
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


	public static void resetUserAfterLogout(ModelStoreSync modelStore, User user) {
		T.call(UserManager.class);

		modelStore.updateModel(User.class, "admin", user.getId(), new ModelUpdater<User>() {
			@Override
			public void update(User userModel) {
				T.call(this);

				userModel.resetAfterLogout();
			}
		});
	}

	public static void initialize(ModelStoreSync modelStore) {
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

	public static Set<String> getAdminRegistrationIds(ModelStoreSync modelStore) {
		T.call(UserManager.class);
		
		Set<String> adminIds = new HashSet<>();

		if(modelStore.ifModelExists(UserList.class, "admin", Constants.ADMIN_LIST_MODEL_ID)) {
			adminIds = modelStore.getModel(UserList.class, "admin", Constants.ADMIN_LIST_MODEL_ID).userIds();
		}

		return adminIds;
	}

	public static Student createStudentUsingRegistrationId(ModelStoreSync modelStore, 
									                       String firstName, 
									                       String lastName, 
									                       String registrationId, 
									                       String programId, 
									                       String phoneNumber, 
									                       String email) {
		T.call(UserManager.class);
		
		String studentId = null;

		if(modelStore.ifModelExists(UserId.class, "admin", registrationId)) {

			studentId = modelStore.getModel(UserId.class, "admin", registrationId).getUserId();

		}else {
			
			String newId = generateUniqueUserId(modelStore);
			
			modelStore.createModel(UserId.class, "admin", registrationId, new ModelInitializer<UserId>() {
				@Override
				public void initialize(UserId newModel) {
					T.call(this);
					newModel.setUserId(newId);
				}
			});

			studentId = newId;

			modelStore.createModel(RegistrationId.class, "admin", studentId, new ModelInitializer<RegistrationId>() {
				@Override
				public void initialize(RegistrationId newModel) {
					T.call(this);
					newModel.setRegistrationId(registrationId);
				}
			});
		}

		return createStudentForUserId(modelStore, 
									  studentId, 
									  registrationId,
									  firstName, 
									  lastName, 
									  programId, 
									  phoneNumber, 
									  email);
	}

	public static Student createStudentForUserId(ModelStoreSync modelStore, 
												 String studentId,
												 String registrationId,
												 String firstName, 
												 String lastName, 
												 String programId, 
												 String phoneNumber,
												 String email) {
		T.call(UserManager.class);
		
		Student student = createUser(modelStore, 
				                     Student.class, 
				                     studentId, 
				                     registrationId,
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
										        String registrationId,
										        String firstName, 
										        String lastName, 
										        String email) {
		T.call(UserManager.class);
		
		U user = null;
		
		if(modelStore.ifModelExists(modelClass, "admin", userId)) {

			user =  modelStore.getModel(modelClass, "admin", userId);
			user.updateInfoIfEmpty(firstName, lastName, email);
			modelStore.save(user);

		}else {

			user = Ntro.factory().newInstance(modelClass);
			user.setId(userId);
			user.setRegistrationId(registrationId);
			user.updateInfoIfEmpty(firstName, lastName, email);
			createUser(modelStore, user);
		}

		return user;
	}

	public static void forEachTeacherId(ModelStoreSync modelStore, UserIdLambda lambda) {
		T.call(UserManager.class);
		
		UserList teacherList = modelStore.getModel(UserList.class, 
				                                        "admin", 
				                                        Constants.TEACHER_LIST_MODEL_ID);

		for(String teacherId : teacherList.userIds()) {
			lambda.execute(teacherId);
		}
	}

	public static boolean ifTeacherExists(ModelStoreSync modelStore, String teacherId) {
		T.call(UserManager.class);
		
		boolean ifExists = false;
		
		
		return ifExists;
	}

	public static boolean ifStudentExistsForRegistrationId(ModelStoreSync modelStore, String registrationId) {
		T.call(UserManager.class);
		
		boolean ifExists = false;

		if(modelStore.ifModelExists(UserId.class, "admin", registrationId)) {
			ifExists = true;
		}
		
		return ifExists;
	}

	public static boolean ifStudentExistsForId(ModelStoreSync modelStore, String studentId) {
		T.call(UserManager.class);
		
		boolean ifExists = false;

		if(modelStore.ifModelExists(Student.class, "admin", studentId)) {
			ifExists = true;
		}
		
		return ifExists;
	}

	public static User getUserById(ModelStoreSync modelStore, String userId) {
		T.call(UserManager.class);
		
		return modelStore.getModel(User.class, "admin", userId);
	}

	public static User getUserByRegistrationId(ModelStoreSync modelStore, String registrationId) {
		T.call(UserManager.class);
		
		User user = null;

		if(modelStore.ifModelExists(UserId.class, "admin", registrationId)) {
			String userId = modelStore.getModel(UserId.class, "admin", registrationId).getUserId();
			user = getUserById(modelStore, userId);
		}

		return user;
	}

	public static boolean ifStoredUserExists(ModelStoreSync modelStore, User user) {
		T.call(UserManager.class);
		
		return modelStore.ifModelExists(User.class, "admin", user.getId());
	}

	public static User getStoredUser(ModelStoreSync modelStore, User user) {
		T.call(UserManager.class);
		
		return getUserById(modelStore, user.getId());
		
	}

	public static User createGuestUser(ModelStoreSync modelStore, 
									   Class<? extends User> guestUserClass,
									   String registrationId) {
		T.call(UserManager.class);
		
		User newUser = Ntro.factory().newInstance(guestUserClass);
		newUser.setRegistrationId(registrationId);
		
		User existingUser = UserManager.getUserByRegistrationId(modelStore, registrationId);
		
		if(existingUser != null) {

			newUser.copyPublicInfomation(existingUser);
			newUser.setId(existingUser.getId());

		}else {

			String userId = generateAndStoreUserId(modelStore, registrationId);
			newUser.setFirstname(registrationId);
			newUser.setEmail(registrationId + "@" + Constants.EMAIL_HOST);
			newUser.setId(userId);
		}

		return newUser;
	}

}
