package ca.aquiletour.server.backend.users;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.messages.AddStudentCsvMessage;
import ca.aquiletour.core.models.users.Student;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class AddStudentCsvHandler extends BackendMessageHandler<AddStudentCsvMessage> {

	@Override
	public void handle(ModelStoreSync modelStore, AddStudentCsvMessage message) {
		T.call(this);
		
		String csvString = message.getCsvString();
		String queueId = message.getQueueId();
		User teacher = message.getUser();
		
		QueueModel queueModel = modelStore.getModel(QueueModel.class, teacher.getAuthToken(), queueId);
		DashboardModel teacherDashboard = modelStore.getModel(DashboardModel.class, teacher.getAuthToken(), teacher.getId());
		
		if(queueModel != null && teacherDashboard != null) {

			List<Student> usersToAdd = parseCsv(csvString);

			int numberOfStudentAdded = updateQueue(modelStore, queueModel, usersToAdd);

			updateTeacherDashboard(modelStore, teacherDashboard, queueId, numberOfStudentAdded);

			Ntro.threadService().executeLater(new AddStudentCsvBackgroundTask(teacher, queueId, usersToAdd));
		}
	}

	private int updateQueue(ModelStoreSync modelStore, QueueModel queueModel, List<Student> usersToAdd) {
		int numberOfStudentAdded = 0;
		
		for(Student student : usersToAdd) {
			String studentId = student.getId();
			
			if(!queueModel.getStudentIds().contains(studentId)) {
				queueModel.getStudentIds().add(studentId);
				numberOfStudentAdded++;
			}
		}
		
		if(numberOfStudentAdded > 0) {
			modelStore.save(queueModel);
		}

		return numberOfStudentAdded;
	}

	private void updateTeacherDashboard(ModelStoreSync modelStore, DashboardModel teacherDashboard, String queueId, int numberOfStudentAdded) {
		T.call(this);
		
		CourseSummary courseSummary = teacherDashboard.findCourseById(queueId);

		if(courseSummary != null) {
			
			if(numberOfStudentAdded > 0) {
				courseSummary.updateNumberOfStudents(courseSummary.getNumberOfStudents().getValue() + numberOfStudentAdded);
				modelStore.save(teacherDashboard);
			}
			
		}else {
			
			Log.warning("Cannot find courseSummary for " + queueId);
		}
	}

	private List<Student> parseCsv(String csvString) {
		T.call(this);
		
		List<Student> usersToAdd = new ArrayList<>();
		
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

}
