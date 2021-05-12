package ca.aquiletour.server.backend.users;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.user.Admin;
import ca.aquiletour.core.models.user.Student;
import ca.aquiletour.core.models.user.Teacher;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.models.user_list.UserListModel;
import ca.aquiletour.core.models.user_registration.RegistrationIdModel;
import ca.aquiletour.core.models.user_registration.StudentIdModel;
import ca.aquiletour.core.pages.dashboard.student.models.DashboardModelStudent;
import ca.aquiletour.core.pages.dashboard.teacher.models.DashboardModelTeacher;
import ca.aquiletour.server.AquiletourConfig;
import ca.aquiletour.server.backend.dashboard.DashboardUpdater;
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
	
	private static int USER_ID_LENGTH = Constants.DEFAULT_STUDENT_ID_LENGTH;
	private static int NUMBER_OF_COLLISIONS_BEFORE_INCREMENTING_USER_ID_LENGTH = 10;

	private synchronized static <U extends User> String generateUniqueUserId(ModelStoreSync modelStore, 
			                                                                 Class<U> modelClass) {
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

	public static void addUsers(ModelStoreSync modelStore, List<User> usersToAdd) {
		T.call(UserManager.class);

		for(User user : usersToAdd) {
			addUser(modelStore, user);
		}
	}

	public static void addUser(ModelStoreSync modelStore, User user) {
		T.call(UserManager.class);

		if(!modelStore.ifModelExists(User.class, "admin", user.getId())) {
            createUser(modelStore, user);
		}
		
		if(user instanceof Teacher) {

			DashboardUpdater.createDashboardForUser(modelStore, DashboardModelTeacher.class, user);
			DashboardUpdater.createDashboardForUser(modelStore, DashboardModelStudent.class, user);
			
			modelStore.updateModel(UserListModel.class, "admin", Constants.TEACHER_LIST_MODEL_ID, new ModelUpdater<UserListModel>() {
				@Override
				public void update(UserListModel existingModel) {
					T.call(this);
					existingModel.addUserId(user.getId());
				}
			});

		}else {

			DashboardUpdater.createDashboardForUser(modelStore, DashboardModelStudent.class, user);

			modelStore.updateModel(UserListModel.class, "admin", Constants.STUDENT_LIST_MODEL_ID, new ModelUpdater<UserListModel>() {
				@Override
				public void update(UserListModel existingModel) {
					T.call(this);
					existingModel.addUserId(user.getId());
				}
			});
		}
	}

	private static void createUser(ModelStoreSync modelStore, User user) {
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

		if(!modelStore.ifModelExists(UserListModel.class, "admin", Constants.ADMIN_LIST_MODEL_ID)) {
			modelStore.createModel(UserListModel.class, "admin", Constants.ADMIN_LIST_MODEL_ID, new ModelInitializer<UserListModel>() {
				@Override
				public void initialize(UserListModel newModel) {
					T.call(this);
					
					AquiletourConfig config = (AquiletourConfig) Ntro.config();
					for(String adminId : config.getAdminIds()){
						newModel.addUserId(adminId);
					}
				}
			});
		}

		if(!modelStore.ifModelExists(UserListModel.class, "admin", Constants.TEACHER_LIST_MODEL_ID)) {
			modelStore.createModel(UserListModel.class, "admin", Constants.TEACHER_LIST_MODEL_ID, new ModelInitializer<UserListModel>() {
				@Override
				public void initialize(UserListModel newModel) {
					T.call(this);
				}
			});
		}

		if(!modelStore.ifModelExists(UserListModel.class, "admin", Constants.STUDENT_LIST_MODEL_ID)) {
			modelStore.createModel(UserListModel.class, "admin", Constants.STUDENT_LIST_MODEL_ID, new ModelInitializer<UserListModel>() {
				@Override
				public void initialize(UserListModel newModel) {
					T.call(this);
				}
			});
		}
	}

	public static Set<String> getAdminIds(ModelStoreSync modelStore) {
		T.call(UserManager.class);
		
		Set<String> adminIds = new HashSet<>();

		if(modelStore.ifModelExists(UserListModel.class, "admin", Constants.ADMIN_LIST_MODEL_ID)) {
			adminIds = modelStore.getModel(UserListModel.class, "admin", Constants.ADMIN_LIST_MODEL_ID).userIds();
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

		if(modelStore.ifModelExists(StudentIdModel.class, "admin", registrationId)) {

			studentId = modelStore.getModel(StudentIdModel.class, "admin", registrationId).getUserId();

		}else {
			
			String newId = generateUniqueUserId(modelStore, Student.class);
			
			modelStore.createModel(StudentIdModel.class, "admin", registrationId, new ModelInitializer<StudentIdModel>() {
				@Override
				public void initialize(StudentIdModel newModel) {
					T.call(this);
					newModel.setUserId(newId);
				}
			});

			studentId = newId;

			modelStore.createModel(RegistrationIdModel.class, "admin", studentId, new ModelInitializer<RegistrationIdModel>() {
				@Override
				public void initialize(RegistrationIdModel newModel) {
					T.call(this);
					newModel.setRegistrationId(registrationId);
				}
			});
		}

		return createStudentForUserId(modelStore, 
									  studentId, 
									  firstName, 
									  lastName, 
									  programId, 
									  phoneNumber, 
									  email);
	}

	public static Student createStudentForUserId(ModelStoreSync modelStore, 
												 String studentId,
												 String firstName, 
												 String lastName, 
												 String email,
												 String programId, 
												 String phoneNumber) {
		T.call(UserManager.class);
		
		Student student = createUser(modelStore, 
				                     Student.class, 
				                     studentId, 
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
			user.updateInfoIfEmpty(firstName, lastName, email);
			addUser(modelStore, user);
		}

		return user;
	}

	public static void forEachTeacherId(ModelStoreSync modelStore, UserIdLambda lambda) {
		T.call(UserManager.class);
		
		UserListModel teacherList = modelStore.getModel(UserListModel.class, 
				                                        "admin", 
				                                        Constants.TEACHER_LIST_MODEL_ID);

		for(String teacherId : teacherList.userIds()) {
			lambda.execute(teacherId);
		}
	}
}
