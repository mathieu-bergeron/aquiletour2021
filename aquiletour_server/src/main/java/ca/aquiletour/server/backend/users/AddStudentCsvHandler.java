package ca.aquiletour.server.backend.users;

import java.util.ArrayList;

import ca.aquiletour.core.messages.AddStudentCsvMessage;
import ca.aquiletour.core.models.users.Student;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.users.UsersModel;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskSync;
import ca.ntro.services.Ntro;

public class AddStudentCsvHandler extends BackendMessageHandler<AddStudentCsvMessage> {

	@Override
	public void handle(ModelStoreSync modelStore, AddStudentCsvMessage message) {
		T.call(this);
		
		String csvString = message.getCsvString();
		
		System.out.println(csvString);
		
		
		String queueId = message.getQueueId();
		User fromUser = message.getUser();

		UsersModel usersModel = modelStore.getModel(UsersModel.class, 
                "adminToken",
                "allUsers");
		ArrayList<Student> usersToAdd = new ArrayList<Student>();
		if(usersModel != null) {
			String[] cutByLine = csvString.split(System.lineSeparator());// cut by each line
			
			for (int i = 0; i < cutByLine.length; i++) {
				String line = cutByLine[i];
				//T.values(line);
				Student newUser = new Student();
				if(i == 0) {//first line is not a student, its the class name
					
				} else {
					String[] cutBySeparator = line.split(";");
					newUser.setName(cutBySeparator[0]);//FamilleA
					newUser.setSurname(cutBySeparator[1]);//PrenomA
					newUser.setId(cutBySeparator[2]);//DA
					newUser.setProgramId(cutBySeparator[3]);//program number
//					newUser.setName(cutBySeparator[4]);//?
					newUser.setPhoneNumber(cutBySeparator[5]);//phone
					newUser.setAuthToken(newUser.getName() + "Token");
					newUser.setEmail(newUser.getName() + "." + newUser.getSurname() + "@test.ca");

					usersModel.addUser(newUser);
					usersToAdd.add(newUser);
				}		
			}

			modelStore.save(usersModel);

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
						modelStore.save(dashboardModel);
						
						QueueModel queueModel = modelStore.getModel(QueueModel.class, 
								newUser.getAuthToken(),
								queueId);
						queueModel.addStudentToClass(newUser.getName());
						modelStore.save(queueModel);
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
