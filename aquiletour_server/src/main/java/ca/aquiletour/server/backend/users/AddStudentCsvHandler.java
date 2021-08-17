package ca.aquiletour.server.backend.users;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.messages.AddStudentCsvMessage;
import ca.aquiletour.core.models.courses.teacher.CourseModelTeacher;
import ca.aquiletour.core.models.user.Student;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.course_list.models.CourseListItem;
import ca.aquiletour.core.pages.course_list.student.CourseListModelStudent;
import ca.aquiletour.core.pages.course_list.teacher.CourseListModelTeacher;
import ca.aquiletour.core.pages.dashboard.student.models.DashboardModelStudent;
import ca.aquiletour.server.backend.course.CourseManager;
import ca.aquiletour.server.backend.course_list.CourseListManager;
import ca.aquiletour.server.backend.dashboard.DashboardManager;
import ca.aquiletour.server.backend.group_list.GroupListManager;
import ca.aquiletour.server.backend.queue.QueueManager;
import ca.aquiletour.server.backend.semester_list.SemesterListManager;
import ca.ntro.backend.BackendError;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.ModelStoreSync;

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
		
		CourseListManager.addGroupForUser(modelStore, 
										  CourseListModelTeacher.class,
										  message.coursePath(),
				                          groupId, 
				                          message.getUser());
	}

	private String groupNameFromCsvFileName(String csvFilename) {
		T.call(this);
		
		String afterDash = csvFilename.split("-")[1];
		String beforeDot = afterDash.split("\\.")[0];
		
		int groupNumber = Integer.parseInt(beforeDot);
		
		String groupName = String.format("%02d", groupNumber);
		
		return groupName;
	}

	private List<User> parseCsv(ModelStoreSync modelStore, String csvString) throws BackendError {
		T.call(this);
		
		List<User> usersToAdd = new ArrayList<>();
		
		String[] cutByLine = csvString.split(System.lineSeparator());// cut by each line
		
		for (int i = 0; i < cutByLine.length; i++) {
			String line = cutByLine[i];
			if(i > 0){ //first line is not a student, its the class name
				String[] cutBySeparator = line.split(Constants.CSV_SEPARATOR);
				String lastName = cutBySeparator[0];
				String firstName = cutBySeparator[1];
				String registrationId = cutBySeparator[2].substring(2);
				String programId = cutBySeparator[3];
				String phoneNumber = cutBySeparator[5];
				String email = registrationId + "@" + Constants.EMAIL_HOST;

				Student newUser = UserManager.createStudentUsingRegistrationId(modelStore, 
																			   firstName, 
																			   lastName, 
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
		
		User teacher = message.getUser();

		UserManager.createUsers(modelStore, studentsToAdd);

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
		
		/*
		QueueManager.addGroup(modelStore, 
						      message.getCourseId(),
				              groupId, 
				              teacher);
	    */

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
	}
}
