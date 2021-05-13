package ca.aquiletour.server.backend.group_list;

import java.util.List;

import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.group_list.models.GroupList;
import ca.ntro.core.models.ModelInitializer;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.trace.T;

public class GroupListManager {

	public static void addGroupForUser(ModelStoreSync modelStore, 
			                           String semesterId, 
			                           String courseId, 
			                           String groupId, 
			                           List<User> studentsToAdd,
			                           User user) {

		T.call(GroupListManager.class);
		
		addGroupForUserId(modelStore, semesterId, courseId, groupId, studentsToAdd, user.getId());
	}

	public static void addGroupForUserId(ModelStoreSync modelStore, 
			                             String semesterId, 
			                             String courseId, 
			                             String groupId,
										 List<User> studentsToAdd,
			                             String userId) {

		T.call(GroupListManager.class);

		if(!modelStore.ifModelExists(GroupList.class, "admin", userId)) {
		}

		modelStore.updateModel(GroupList.class, "admin", userId, new ModelUpdater<GroupList>() {
			@Override
			public void update(GroupList existingModel) {
				T.call(this);

				existingModel.addGroup(semesterId, courseId, groupId, studentsToAdd);
			}
		});
	}
	
	
	public static void addSemesterForUserId(ModelStoreSync modelStore, 
			                                String semesterId, 
			                                String userId) {

		T.call(GroupListManager.class);

		if(!modelStore.ifModelExists(GroupList.class, "admin", userId)) {
			modelStore.createModel(GroupList.class, "admin", userId, new ModelInitializer<GroupList>() {
				@Override
				public void initialize(GroupList newModel) {
					T.call(this);
				}
			});
		}

		modelStore.updateModel(GroupList.class, 
							   "admin",
							   userId,
							   new ModelUpdater<GroupList>() {

			@Override
			public void update(GroupList groupListModel) {
				T.call(this);
				
				groupListModel.addSemester(semesterId);
			}
		});
	}
	
	public static void addSemesterForUser(ModelStoreSync modelStore, 
			                              String semesterId, 
			                              User user) {

		T.call(GroupListManager.class);
		
		addSemesterForUserId(modelStore, semesterId, user.getId());
	}
	
	
	public static void addCourseForUserId(ModelStoreSync modelStore, 
			                              String semesterId, 
			                              String courseId, 
			                              String userId) {

		T.call(GroupListManager.class);

		modelStore.updateModel(GroupList.class, 
							   "admin",
							   userId,
							   new ModelUpdater<GroupList>() {

			@Override
			public void update(GroupList groupListModel) {
				T.call(this);
				
				groupListModel.addCourse(semesterId, courseId);
			}
		});
	}
	
	public static void addCourseForUser(ModelStoreSync modelStore, 
			                            String semesterId, 
			                            String courseId, 
			                            User user) {

		T.call(GroupListManager.class);
		
		addCourseForUserId(modelStore, semesterId, courseId, user.getId());
	}

	public static void createGroupListForModelId(ModelStoreSync modelStore, String modelId) {
		T.call(GroupListManager.class);

		modelStore.createModel(GroupList.class, "admin", modelId, new ModelInitializer<GroupList>() {
			@Override
			public void initialize(GroupList newModel) {
				T.call(this);
			}
		});
	}

	public static void createGroupListForUser(ModelStoreSync modelStore, User user) {
		T.call(GroupListManager.class);

		createGroupListForModelId(modelStore, user.getId());
	}
}
