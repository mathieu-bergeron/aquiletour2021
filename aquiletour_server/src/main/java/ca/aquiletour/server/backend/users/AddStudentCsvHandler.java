package ca.aquiletour.server.backend.users;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.messages.AddStudentCsvMessage;
import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.models.courses.model.CourseModel;
import ca.aquiletour.core.models.user.Student;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.course_list.models.CourseListItem;
import ca.aquiletour.core.pages.course_list.student.CourseListStudent;
import ca.aquiletour.core.pages.course_list.teacher.CourseListTeacher;
import ca.aquiletour.core.pages.dashboard.student.models.CurrentTaskStudent;
import ca.aquiletour.core.pages.dashboard.student.models.DashboardStudent;
import ca.aquiletour.core.pages.dashboard.teacher.models.CurrentTaskTeacher;
import ca.aquiletour.core.pages.dashboard.teacher.models.DashboardTeacher;
import ca.aquiletour.server.backend.course.CourseUpdater;
import ca.aquiletour.server.backend.course_list.CourseListUpdater;
import ca.aquiletour.server.backend.dashboard.DashboardManager;
import ca.aquiletour.server.backend.group_list.GroupListUpdater;
import ca.aquiletour.server.backend.semester_list.SemesterListManager;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class AddStudentCsvHandler extends BackendMessageHandler<AddStudentCsvMessage> {
	
	private List<User> studentsToAdd;
	private String groupId;

	@Override
	public void handleNow(ModelStoreSync modelStore, AddStudentCsvMessage message) {
		T.call(this);
		
		String csvString = message.getCsvString();
		String csvFilename = message.getCsvFilename();
		
		groupId = groupNameFromCsvFileName(csvFilename);
		studentsToAdd = parseCsv(modelStore, csvString);
		
		CourseListUpdater.addGroupForUser(modelStore, 
										  CourseListTeacher.class,
				                          message.getSemesterId(), 
				                          message.getCourseId(), 
				                          groupId, 
				                          message.getUser());


		/*
		int numberOfStudentAdded = QueueUpdater.addStudentsToQueue(modelStore, queueId, studentsToAdd);
			
		DashboardUpdater.incrementNumberOfStudents(modelStore, queueId, teacher.getId(), numberOfStudentAdded);

		// FIXME: we need a real id
		CourseDashboard queueSummary = DashboardUpdater.createQueueSummary(queueId, queueId);

		DashboardUpdater.addQueueForUser(modelStore, queueSummary, teacher);
		*/
	}

	private String groupNameFromCsvFileName(String csvFilename) {
		T.call(this);
		
		String afterDash = csvFilename.split("-")[1];
		String beforeDot = afterDash.split("\\.")[0];
		
		int groupNumber = Integer.parseInt(beforeDot);
		
		String groupName = String.format("%02d", groupNumber);
		
		return groupName;
	}

	private List<User> parseCsv(ModelStoreSync modelStore, String csvString) {
		T.call(this);
		
		List<User> usersToAdd = new ArrayList<>();
		
		String[] cutByLine = csvString.split(System.lineSeparator());// cut by each line
		
		for (int i = 0; i < cutByLine.length; i++) {
			String line = cutByLine[i];
			if(i > 0){ //first line is not a student, its the class name
				String[] cutBySeparator = line.split(";");
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
	public void handleLater(ModelStoreSync modelStore, AddStudentCsvMessage message) {
		T.call(this);
		
		User teacher = message.getUser();

		CoursePath coursePath = new CoursePath(teacher.getId(), message.getSemesterId(), message.getCourseId());

		UserManager.createUsers(modelStore, studentsToAdd);

		GroupListUpdater.addGroupForUser(modelStore, 
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
		
		CourseListItem courseItem = CourseListUpdater.getCourseItem(modelStore, 
															    CourseListTeacher.class,
				                                                message.getSemesterId(),
				                                                message.getCourseId(),
				                                                teacher.getId());

		CourseUpdater.addGroup(modelStore, 
							   coursePath,
							   groupId,
							   studentsToAdd,
							   teacher);

		CourseModel course = CourseUpdater.getCourse(modelStore, 
													 CourseModel.class,
													 message.coursePath());
		
		for(User student : studentsToAdd) {
			CourseListUpdater.addSemesterForUser(modelStore, CourseListStudent.class, courseItem.getSemesterId(), student);
			CourseListUpdater.addCourseForUser(modelStore, CourseListStudent.class, courseItem, student);
			DashboardManager.addDashboardItemForUser(modelStore, DashboardStudent.class, courseItem, student);
			
			List<CurrentTaskStudent> currentTasksStudent = course.currentTasksStudent(student.getId());
			
			DashboardManager.updateCurrentTasksForUser(modelStore, DashboardStudent.class, CurrentTaskStudent.class, message.coursePath(), currentTasksStudent, student);
		}
		
		List<CurrentTaskTeacher> currentTasksTeacher = course.currentTasksTeacher();
		
		DashboardManager.updateCurrentTasksForUserId(modelStore, DashboardTeacher.class, CurrentTaskTeacher.class, message.coursePath(), currentTasksTeacher, message.getTeacherId());
	}
}
