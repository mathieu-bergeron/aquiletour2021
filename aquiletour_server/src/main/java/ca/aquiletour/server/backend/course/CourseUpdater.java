package ca.aquiletour.server.backend.course;

import ca.aquiletour.core.pages.course.models.CourseModel;
import ca.aquiletour.core.pages.course.models.Task;
import ca.ntro.core.Path;
import ca.ntro.core.models.ModelInitializer;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.trace.T;

public class CourseUpdater {

	public static void addSubTask(ModelStoreSync modelStore, String courseId, Path parentPath, Task task) {
		T.call(CourseUpdater.class);
		
		if(modelStore.ifModelExists(CourseModel.class, "admin", courseId)) {
			
			modelStore.updateModel(CourseModel.class, "admin", courseId, new ModelUpdater<CourseModel>() {
				@Override
				public void update(CourseModel course) {
					T.call(this);
					course.addSubTaskTo(parentPath, task);
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
					rootTask.setPath(new Path("/"));
					rootTask.setTitle(courseId); // FIXME: we need a real title
					course.setRootTask(rootTask);
					course.setCourseId(courseId);
				}
			});

			addSubTask(modelStore, courseId, parentPath, task);
		}
	}
}
