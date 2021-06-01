package ca.aquiletour.server.backend.semester_list;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.course_list.teacher.CourseListModelTeacher;
import ca.aquiletour.core.pages.semester_list.admin.models.SemesterListModelAdmin;
import ca.aquiletour.core.pages.semester_list.admin.models.SemesterModelAdmin;
import ca.aquiletour.core.pages.semester_list.messages.AddSemesterMessage;
import ca.aquiletour.core.pages.semester_list.teacher.models.SemesterListModelTeacher;
import ca.aquiletour.core.pages.semester_list.teacher.models.SemesterModelTeacher;
import ca.aquiletour.server.backend.course_list.CourseListManager;
import ca.aquiletour.server.backend.group_list.GroupListManager;
import ca.aquiletour.server.backend.login.SessionManager;
import ca.aquiletour.server.backend.users.UserManager;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendError;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.wrappers.options.EmptyOptionException;
import ca.ntro.core.wrappers.options.Optionnal;

public class AddSemesterHandler extends BackendMessageHandler<AddSemesterMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, AddSemesterMessage message) throws BackendError {
		T.call(this);

		if(!SessionManager.isUserAuthenticated(modelStore, message.getUser())) {
			throw new BackendError("Permission refusée");
		}
		
		if(message.getUser().actsAsAdmin()) {

			boolean adminControlledSemester = true;
			
			SemesterListManager.addSemesterToModel(modelStore, 
					                               SemesterListModelAdmin.class, 
					                               SemesterModelAdmin.class,
					                               message.getSemesterId(), 
					                               adminControlledSemester,
					                               Constants.ADMIN_CONTROLLED_SEMESTER_LIST_ID);
			
		}else if(message.getUser().actsAsTeacher()){
			
			boolean adminControlledSemester = false;

			SemesterListManager.addSemesterForUser(modelStore, 
												   SemesterListModelTeacher.class, 
												   SemesterModelTeacher.class,
												   message.getSemesterId(), 
												   adminControlledSemester,
												   message.getUser());
		}else {

			throw new BackendError("Permission refusée");
		}
		
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, AddSemesterMessage message) throws BackendError {
		T.call(this);

		if(message.getUser().actsAsAdmin()) {
			
			boolean adminControlledSemester = true;
			
			Optionnal<BackendError> backendError = new Optionnal<>();
			
			UserManager.forEachTeacherId(modelStore, teacherId -> {
				try {
					SemesterListManager.addSemesterToModel(modelStore, 
														   SemesterListModelTeacher.class, 
														   SemesterModelTeacher.class,
														   message.getSemesterId(), 
														   adminControlledSemester,
														   teacherId);
					
					CourseListManager.addSemesterForUserId(modelStore, 
							                               CourseListModelTeacher.class, 
							                               message.getSemesterId(), 
							                               teacherId);
				}catch(BackendError e) {

				}
			});
			
			if(!backendError.isEmpty()) {
				try {
					throw backendError.get();
				} catch (EmptyOptionException e) {}
			}
			

		}else if(message.getUser().actsAsTeacher()){

			CourseListManager.addSemesterForUser(modelStore, CourseListModelTeacher.class, message.getSemesterId(), message.getUser());
			GroupListManager.addSemesterForUser(modelStore, message.getSemesterId(), message.getUser());
		}
	}

}
