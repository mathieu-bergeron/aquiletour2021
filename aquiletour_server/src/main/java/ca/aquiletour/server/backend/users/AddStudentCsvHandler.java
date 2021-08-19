package ca.aquiletour.server.backend.users;


import java.util.ArrayList;
import java.util.List;


import ca.aquiletour.core.Constants;
import ca.aquiletour.core.messages.AddStudentCsvMessage;
import ca.aquiletour.core.models.user.Student;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.server.backend.log.LogManagerQueue;
import ca.aquiletour.server.backend.queue.QueueManager;
import ca.ntro.backend.BackendError;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.ntro_messages.NtroErrorMessage;
import ca.ntro.services.ModelStoreSync;
import ca.ntro.services.Ntro;

public class AddStudentCsvHandler extends BackendMessageHandler<AddStudentCsvMessage> {
	
	private List<User> studentsToAdd;
	private String groupId;

	@Override
	public void handleNow(ModelStoreSync modelStore, AddStudentCsvMessage message) throws BackendError {
		T.call(this);
		
		String csvString = message.getCsvString();
		String csvFilename = message.getCsvFilename();

		groupId = groupNameFromCsvFileName(csvFilename);
		
		studentsToAdd = parseCsv(modelStore, csvString);

		UserManager.createUsers(modelStore, studentsToAdd);

		NtroErrorMessage feedback = Ntro.messages().create(NtroErrorMessage.class);
		
		if(studentsToAdd.size() > 0) {

			feedback.setMessage("" + studentsToAdd.size() + " inscriptions réussies.");

		}else {

			feedback.setMessage("Aucune données valides trouvée dans le .csv");
		}
		
		Ntro.messages().send(feedback);
		
		/*
		CourseListManager.addGroupForUser(modelStore, 
										  CourseListModelTeacher.class,
										  message.coursePath(),
				                          groupId, 
				                          message.getUser());
		*/
	}

	private String groupNameFromCsvFileName(String csvFilename) {
		T.call(this);
		
		String groupName = null;
		
		String[] dashSegments = csvFilename.split("-");
		
		if(dashSegments.length >= 2) {
			
			String afterDash = dashSegments[1];
			
			String[] dotSegments = afterDash.split("\\.");
			
			if(dotSegments.length >= 2) {

				String beforeDot = dotSegments[0];
				String groupNumberRaw = beforeDot;

				if(beforeDot.contains("(")) {
					groupNumberRaw = beforeDot.split("\\(")[0];
				}

				groupNumberRaw = groupNumberRaw.trim();

				int groupNumber = Integer.parseInt(groupNumberRaw);
				groupName = String.format("%02d", groupNumber);
			}
		}
		
		return groupName;
	}

	private List<User> parseCsv(ModelStoreSync modelStore, String csvString) throws BackendError {
		T.call(this);
		
		List<User> usersToAdd = new ArrayList<>();
		
		String[] lines = csvString.split(System.lineSeparator());
		
		for (int i = 0; i < lines.length; i++) {

			String line = lines[i];

			String[] fields = line.split(Constants.CSV_SEPARATOR);

			String lastname = "";
			String firstname = "";
			String registrationId = "";
			String programId = "";
			String phoneNumber = "";
			
			if(fields.length > 0) {
				lastname = fields[0];
			}

			if(fields.length > 1) {
				firstname = fields[1];
			}

			if(fields.length > 2) {
				String registrationIdRaw = fields[2].trim();

				if(registrationIdRaw.length() == 9) {
					registrationIdRaw = registrationIdRaw.substring(2);
				}

				if(UserManager.isStudentId(registrationIdRaw)) {
					registrationId = registrationIdRaw;
				}
			}

			if(fields.length > 3) {
				programId = fields[3];
			}

			if(fields.length > 4) {
				phoneNumber = fields[4];
			}

			String email = registrationId + "@" + Constants.EMAIL_HOST;
			
			if(!firstname.isEmpty()
					&& !lastname.isEmpty()
					&& !registrationId.isEmpty()) {
				
				Student newUser = UserManager.createStudentUsingRegistrationId(modelStore, 
																			   firstname, 
																			   lastname, 
																			   registrationId, 
																			   programId, 
																			   phoneNumber, 
																			   email);
				usersToAdd.add(newUser);
			}
		}

		return usersToAdd;
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, AddStudentCsvMessage message) throws BackendError {
		T.call(this);
		
		for(User user : studentsToAdd) {
			T.values(user.getFirstname());
			
			QueueManager.renameUser(modelStore, user.getId(), user.getFirstname(), user.getLastname());
			LogManagerQueue.renameUser(modelStore, user.getId(), user.getFirstname(), user.getLastname());
		}

		/*
		 

		User teacher = message.getUser();

		GroupListManager.addGroupForUser(modelStore, 
				                         message.getSemesterId(), 
				                         message.getCourseId(), 
				                         groupId, 
				                         studentsToAdd,
				                         teacher);

		SemesterListManager.addCourseGroupForUser(modelStore, 
				                                  message.getSemesterId(), 
				                                  message.getCourseId(), 
				                                  groupId, 
				                                  teacher);
		
		CourseListItem courseItem = CourseListManager.getCourseItem(modelStore, 
															        CourseListModelTeacher.class,
															        message.coursePath(),
				                                                    teacher.getId());

		CourseManager.addGroup(modelStore, 
							   message.coursePath(),
							   groupId,
							   studentsToAdd,
							   teacher);
	   */
		
		/*
		QueueManager.addGroup(modelStore, 
						      message.getCourseId(),
				              groupId, 
				              teacher);
	    */
		
		/*

		for(User student : studentsToAdd) {

			CourseManager.createStudentCourse(modelStore, message.coursePath(), groupId, student);
			
			//CourseListManager.addSemesterForUser(modelStore, CourseListModelStudent.class, courseItem.getSemesterId(), student);

			CourseListManager.addCourseToCategory(modelStore, 
					                              CourseListModelStudent.class,
					                              Constants.CATEGORY_ID_CURRENT, // FIXME: select actual category
					                              courseItem, 
					                              student);

			DashboardManager.addDashboardItemForUser(modelStore, DashboardModelStudent.class, courseItem, student);
		}
		
		DashboardManager.updateCurrentTasks(modelStore, message.coursePath());

		*/
	}
}
