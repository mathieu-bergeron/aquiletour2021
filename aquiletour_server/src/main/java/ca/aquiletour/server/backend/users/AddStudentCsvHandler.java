package ca.aquiletour.server.backend.users;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.messages.AddStudentCsvMessage;
import ca.aquiletour.core.models.users.Student;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.aquiletour.server.backend.dashboard.DashboardModels;
import ca.aquiletour.server.backend.queue.QueueModels;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class AddStudentCsvHandler extends BackendMessageHandler<AddStudentCsvMessage> {
	
	private List<User> studentsToAdd;

	@Override
	public void handleNow(ModelStoreSync modelStore, AddStudentCsvMessage message) {
		T.call(this);
		
		String csvString = message.getCsvString();
		String queueId = message.getQueueId();
		User teacher = message.getUser();
		
		studentsToAdd = parseCsv(csvString);

		int numberOfStudentAdded = QueueModels.addStudentsToQueue(modelStore, queueId, studentsToAdd);
			
		DashboardModels.incrementNumberOfStudents(modelStore, queueId, teacher.getId(), numberOfStudentAdded);

		// FIXME: we need a real id
		CourseSummary queueSummary = DashboardModels.createQueueSummary(queueId, queueId);

		DashboardModels.addQueueForUser(modelStore, queueSummary, teacher);
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
		
		UserModels.addUsers(modelStore, studentsToAdd);

		String queueId = message.getQueueId();

		// FIXME: we need a real id
		CourseSummary queueSummary = DashboardModels.createQueueSummary(queueId, queueId);

		DashboardModels.addQueueForUsers(modelStore, queueSummary, studentsToAdd);
	}
}
