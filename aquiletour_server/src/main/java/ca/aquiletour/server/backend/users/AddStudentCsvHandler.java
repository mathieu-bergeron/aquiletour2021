package ca.aquiletour.server.backend.users;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.messages.AddStudentCsvMessage;
import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.models.courses.model.CourseModel;
import ca.aquiletour.core.models.users.Student;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.course_list.models.CourseListItem;
import ca.aquiletour.core.pages.course_list.student.CourseListModelStudent;
import ca.aquiletour.core.pages.course_list.teacher.CourseListModelTeacher;
import ca.aquiletour.core.pages.dashboard.student.models.CurrentTaskStudent;
import ca.aquiletour.core.pages.dashboard.student.models.DashboardModelStudent;
import ca.aquiletour.core.pages.dashboard.teacher.models.CurrentTaskTeacher;
import ca.aquiletour.core.pages.dashboard.teacher.models.DashboardModelTeacher;
import ca.aquiletour.server.backend.course.CourseUpdater;
import ca.aquiletour.server.backend.course_list.CourseListUpdater;
import ca.aquiletour.server.backend.dashboard.DashboardUpdater;
import ca.aquiletour.server.backend.group_list.GroupListUpdater;
import ca.aquiletour.server.backend.semester_list.SemesterListUpdater;
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
		studentsToAdd = parseCsv(csvString);
		
		CourseListUpdater.addGroupForUser(modelStore, 
										  CourseListModelTeacher.class,
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

	private List<User> parseCsv(String csvString) {
		T.call(this);
		
		List<User> usersToAdd = new ArrayList<>();
		
		String[] cutByLine = csvString.split(System.lineSeparator());// cut by each line
		
		for (int i = 0; i < cutByLine.length; i++) {
			String line = cutByLine[i];
			Student newUser = new Student();
			if(i == 0) {//first line is not a student, its the class name
				
			} else {
				String[] cutBySeparator = line.split(";");
				newUser.setLastname(cutBySeparator[0]);//FamilleA
				newUser.setFirstname(cutBySeparator[1]);//PrenomA
				// FIXME: hide DA
				//        use a SHA1 digest as userId
				newUser.setId(cutBySeparator[2].substring(2));//DA 
				newUser.setProgramId(cutBySeparator[3]);//program number
//					newUser.setName(cutBySeparator[4]);//?
				newUser.setPhoneNumber(cutBySeparator[5]);//phone
				newUser.setEmail(newUser.getId() + "@cmontmorency.qc.ca");
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

		UserUpdater.addUsers(modelStore, studentsToAdd);

		GroupListUpdater.addGroupForUser(modelStore, 
				                         message.getSemesterId(), 
				                         message.getCourseId(), 
				                         groupId, 
				                         studentsToAdd,
				                         teacher);

		SemesterListUpdater.addCourseGroupForUser(modelStore, 
				                                  message.getSemesterId(), 
				                                  message.getCourseId(), 
				                                  groupId, 
				                                  teacher);
		
		CourseListItem courseItem = CourseListUpdater.getCourseItem(modelStore, 
															    CourseListModelTeacher.class,
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
			CourseListUpdater.addSemesterForUser(modelStore, CourseListModelStudent.class, courseItem.getSemesterId(), student);
			CourseListUpdater.addCourseForUser(modelStore, CourseListModelStudent.class, courseItem, student);
			DashboardUpdater.addDashboardItemForUser(modelStore, DashboardModelStudent.class, courseItem, student);
			
			List<CurrentTaskStudent> currentTasksStudent = course.currentTasksStudent(student.getId());
			
			DashboardUpdater.updateCurrentTasksForUser(modelStore, DashboardModelStudent.class, CurrentTaskStudent.class, message.coursePath(), currentTasksStudent, student);
		}
		
		List<CurrentTaskTeacher> currentTasksTeacher = course.currentTasksTeacher();
		
		DashboardUpdater.updateCurrentTasksForUserId(modelStore, DashboardModelTeacher.class, CurrentTaskTeacher.class, message.coursePath(), currentTasksTeacher, message.getTeacherId());
	}
}
