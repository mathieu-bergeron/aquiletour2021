package ca.aquiletour.server.backend;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.backend.QueueBackendController;
import ca.aquiletour.core.messages.AddStudentCsvMessage;
import ca.aquiletour.core.models.users.Student;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.aquiletour.core.pages.dashboards.values.ObservableCourseList;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.queue.student.messages.AddAppointmentMessage;
import ca.aquiletour.core.pages.users.UsersModel;
import ca.ntro.core.Ntro;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskSync;
import ca.ntro.jdk.messages.BackendMessageHandler;
import ca.ntro.jdk.models.ModelStoreSync;

public class AddStudentCsvHandler extends BackendMessageHandler<AddStudentCsvMessage> {

	@Override
	public void handle(ModelStoreSync modelStore, AddStudentCsvMessage message) {
		T.call(this);
		
		String csvString = message.getCsvString();
		String queueId = message.getQueueId();
		User fromUser = message.getUser();
		//T.values(csvString);
		//TODO parse csv, get user info, make a userModel and dashboard for each.
		//parse csv: trouver le bon fichier qui commence avec le queueId, chercher: (prenom;nom;DA9chiffres;numeroProgramme6characters;phoneNumber;;;;;
		UsersModel usersModel = modelStore.getModel(UsersModel.class, 
                "adminToken",
                "allUsers");
		ArrayList<Student> usersToAdd = new ArrayList<Student>();
		if(usersModel != null) {
			String[] cutByLine = csvString.split("\\r?\\n");// cut by each line
			
			for (int i = 0; i < cutByLine.length; i++) {
				String line = cutByLine[i];
				//T.values(line);
				Student newUser = new Student();
				if(i == 0) {//first line is not a student, its the class name
					
				} else {
					String[] cutBySeparator = line.split(";");
					newUser.setName(cutBySeparator[0]);//FamilleA
					newUser.setSurname(cutBySeparator[1]);//PrenomA
					newUser.setRegistrationId(cutBySeparator[2]);//DA
					newUser.setProgramId(cutBySeparator[3]);//program number
					newUser.setId(newUser.getSurname());
//					newUser.setName(cutBySeparator[4]);//?
					newUser.setPhoneNumber(cutBySeparator[5]);//phone
					newUser.setAuthToken(newUser.getName() + "Token");
					newUser.setUserEmail(newUser.getName() + "." + newUser.getSurname() + "@test.ca");
					newUser.setUserPassword(newUser.getName() + "password");
					usersModel.addUser(newUser);
					usersToAdd.add(newUser);
					usersModel.save();
					
				}		
			}
			Ntro.threadService().executeLater(new NtroTaskSync() {
				@Override
				protected void runTask() {
					for (int i = 0; i < usersToAdd.size(); i++) {
						Student newUser = usersToAdd.get(i);
						DashboardModel dashboardModel = modelStore.getModel(DashboardModel.class, 
								newUser.getAuthToken(),
	                            newUser.getId());
						CourseSummary newCourse = new CourseSummary();
						newCourse.setTitle(queueId);
						newCourse.setCourseId(queueId);
						dashboardModel.addCourse(newCourse);
						dashboardModel.save();
						QueueModel queueModel = modelStore.getModel(QueueModel.class, 
								newUser.getAuthToken(),
								queueId);
						queueModel.getStudentIds().add(newUser.getName());
						queueModel.save();
					}
					
				}

				@Override
				protected void onFailure(Exception e) {
				}
			});
		} else {
			
			// TODO: error handling
		}
	}

}
