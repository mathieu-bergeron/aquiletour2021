package ca.aquiletour.server.backend.users;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.messages.AddStudentCsvMessage;
import ca.aquiletour.core.models.users.Student;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.values.CourseDashboard;
import ca.aquiletour.server.backend.course.CourseUpdater;
import ca.aquiletour.server.backend.course_list.CourseListUpdater;
import ca.aquiletour.server.backend.dashboard.DashboardUpdater;
import ca.aquiletour.server.backend.queue.QueueUpdater;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class AddStudentCsvHandler extends BackendMessageHandler<AddStudentCsvMessage> {
	
	private List<User> studentsToAdd;

	@Override
	public void handleNow(ModelStoreSync modelStore, AddStudentCsvMessage message) {
		T.call(this);
		
		String csvString = message.getCsvString();
		String csvFilename = message.getCsvFilename();
		String queueId = message.getCourseId();
		User teacher = message.getUser();
		
		String groupId = groupNameFromCsvFileName(csvFilename);
		
		studentsToAdd = parseCsv(csvString);
		
		CourseListUpdater.addGroupForUser(modelStore, 
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
				newUser.setSurname(cutBySeparator[0]);//FamilleA
				newUser.setName(cutBySeparator[1]);//PrenomA
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
		
		UserUpdater.addUsers(modelStore, studentsToAdd);

		String queueId = message.getCourseId();

		// FIXME: we need a real id
		CourseDashboard queueSummary = DashboardUpdater.createQueueSummary(queueId, queueId);

		DashboardUpdater.addQueueForUsers(modelStore, queueSummary, studentsToAdd);
	}
}
