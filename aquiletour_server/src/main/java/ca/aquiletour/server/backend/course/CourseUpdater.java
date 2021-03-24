package ca.aquiletour.server.backend.course;

import ca.aquiletour.core.pages.course.models.CourseModel;
import ca.aquiletour.core.pages.course.models.Task;
import ca.ntro.core.models.ModelInitializer;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.trace.T;

public class CourseUpdater {

	public static void addTaskToCourse(ModelStoreSync modelStore, String courseId, Task task) {
		T.call(CourseUpdater.class);
		
		if(modelStore.ifModelExists(CourseModel.class, "admin", courseId)) {
			
			modelStore.updateModel(CourseModel.class, "admin", courseId, new ModelUpdater<CourseModel>() {
				@Override
				public void update(CourseModel course) {
					T.call(this);
					course.addTask(task);
				}

			});

		}else {
			
			// FIXME: here we need to create AND save the model
			//        because we need to load the model in order to install
			//        valuePath() and modelStore() in the model values
			modelStore.createModel(CourseModel.class, "admin", courseId, new ModelInitializer<CourseModel>() {
				@Override
				public void initialize(CourseModel course) {
					T.call(this);
					
					Task rootTask = new Task();
					rootTask.setTitle(courseId);
					course.addTask(rootTask);
				}
			});

			addTaskToCourse(modelStore, courseId, task);
		}
	}
}
