package ca.aquiletour.server.backend.users;


import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.models.users.Student;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskSync;
import ca.ntro.services.Ntro;
import ca.ntro.stores.DocumentPath;

public class AddStudentCsvBackgroundTask extends NtroTaskSync {
	
	private User teacher;
	private String queueId;
	private List<Student> usersToAdd;
	
	public AddStudentCsvBackgroundTask(User teacher, String courseId, List<Student> usersToAdd) {
		this.teacher = teacher;
		this.queueId = courseId;
		this.usersToAdd = usersToAdd;
	}

	@Override
	protected void runTask() {
		T.call(this);

		ModelStoreSync modelStore = new ModelStoreSync(Ntro.modelStore());

		addUsers(modelStore, usersToAdd);

		updateStudentDashboards(modelStore, usersToAdd);
	}

	private void updateStudentDashboards(ModelStoreSync modelStore, List<Student> usersToAdd) {
		T.call(this);
		
		for(Student student : usersToAdd) {
			updateStudentDashboard(modelStore, student);
		}
	}

	private void updateStudentDashboard(ModelStoreSync modelStore, Student student) {
		T.call(this);
		
		DashboardModel studentDashboard = modelStore.getModel(DashboardModel.class, "admin", student.getId());
		
		CourseSummary courseSummary = studentDashboard.findCourseById(queueId);
		
		System.out.println("courseSummary " + courseSummary); 
		
		if(courseSummary == null) {
			courseSummary = new CourseSummary();
			courseSummary.setTitle(queueId); // FIXME: we need real title
			courseSummary.setCourseId(queueId);
			courseSummary.updateQueueOpen(false);
			courseSummary.updateMyAppointment(false);
			courseSummary.updateNumberOfStudents(0);
			
			studentDashboard.addCourse(courseSummary);
			
			modelStore.save(studentDashboard);
		}
	}

	private void addUsers(ModelStoreSync modelStore, List<Student> usersToAdd) {
		T.call(this);

		for(Student student : usersToAdd) {
			if(!modelStore.ifModelExists(User.class, "admin", student.getId())) {
				DocumentPath documentPath = new DocumentPath();
				documentPath.setCollection(Ntro.introspector().getSimpleNameForClass(User.class));
				documentPath.setDocumentId(student.getId());
				modelStore.saveJsonString(documentPath, Ntro.jsonService().toString(student));
			}
		}
	}

	@Override
	protected void onFailure(Exception e) {
	}

}
