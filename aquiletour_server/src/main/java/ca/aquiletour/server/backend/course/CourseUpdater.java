package ca.aquiletour.server.backend.course;

import ca.aquiletour.core.pages.course.models.CourseModel;
import ca.aquiletour.core.pages.course.models.OnTaskAdded;
import ca.aquiletour.core.pages.course.models.OnTaskRemoved;
import ca.aquiletour.core.pages.course.models.Task;
import ca.aquiletour.server.backend.git.GitMessages;
import ca.ntro.core.Path;
import ca.ntro.core.models.ModelInitializer;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.trace.T;

public class CourseUpdater {

	public static void removeNextTask(ModelStoreSync modelStore, 
			                          String courseId, 
			                          Path taskToModify,
			                          Path taskToDelete) {
		T.call(CourseUpdater.class);
		
		modelStore.updateModel(CourseModel.class, "admin", courseId, new ModelUpdater<CourseModel>() {
			@Override
			public void update(CourseModel course) {
				T.call(this);
				course.removeNextTask(taskToModify, taskToDelete);
			}
		});
	}

	public static void removeSubTask(ModelStoreSync modelStore, 
			                              String courseId, 
			                              Path taskToModify,
			                              Path taskToDelete) {
		T.call(CourseUpdater.class);
		
		modelStore.updateModel(CourseModel.class, "admin", courseId, new ModelUpdater<CourseModel>() {
			@Override
			public void update(CourseModel course) {
				T.call(this);
				course.removeSubTask(taskToModify, taskToDelete);
			}
		});
	}

	public static void removePreviousTask(ModelStoreSync modelStore, 
			                              String courseId, 
			                              Path taskToModify,
			                              Path taskToDelete) {
		T.call(CourseUpdater.class);
		
		modelStore.updateModel(CourseModel.class, "admin", courseId, new ModelUpdater<CourseModel>() {
			@Override
			public void update(CourseModel course) {
				T.call(this);
				course.removePreviousTask(taskToModify, taskToDelete);
			}
		});
	}

	public static void deleteTask(ModelStoreSync modelStore, String courseId, Path taskToDelete) {
		T.call(CourseUpdater.class);
		
		modelStore.updateModel(CourseModel.class, "admin", courseId, new ModelUpdater<CourseModel>() {
			@Override
			public void update(CourseModel course) {
				T.call(this);
				course.deleteTask(taskToDelete, new OnTaskRemoved() {
					@Override
					public void onTaskRemoved(Task task) {
						T.call(this);
						
						GitMessages.deRegisterExercice(courseId, task.getPath());
					}
				});
			}
		});
	}
	
	public static void addPreviousTask(ModelStoreSync modelStore, String courseId, Path nextPath, Task previousTask) {
		T.call(CourseUpdater.class);
		
		modelStore.updateModel(CourseModel.class, "admin", courseId, new ModelUpdater<CourseModel>() {
			@Override
			public void update(CourseModel course) {
				T.call(this);

				course.addPreviousTaskTo(nextPath, previousTask, new OnTaskAdded() {
					@Override
					public void onTaskAdded() {
						T.call(this);
						
						GitMessages.registerExercice(courseId, previousTask.getPath());
					}
				});
			}
		});
	}

	public static void addNextTask(ModelStoreSync modelStore, String courseId, Path previousPath, Task nextTask) {
		T.call(CourseUpdater.class);
		
		modelStore.updateModel(CourseModel.class, "admin", courseId, new ModelUpdater<CourseModel>() {
			@Override
			public void update(CourseModel course) {
				T.call(this);

				course.addNextTaskTo(previousPath, nextTask, new OnTaskAdded() {
					@Override
					public void onTaskAdded() {
						T.call(this);

						GitMessages.registerExercice(courseId, nextTask.getPath());
					}
				});
			}
		});
	}
	

	public static void addSubTask(ModelStoreSync modelStore, String courseId, Path parentPath, Task task) {
		T.call(CourseUpdater.class);
		
		if(modelStore.ifModelExists(CourseModel.class, "admin", courseId)) {
			
			modelStore.updateModel(CourseModel.class, "admin", courseId, new ModelUpdater<CourseModel>() {
				@Override
				public void update(CourseModel course) {
					T.call(this);

					course.addSubTaskTo(parentPath, task, new OnTaskAdded() {
						@Override
						public void onTaskAdded() {
							T.call(this);

							GitMessages.registerExercice(courseId, task.getPath());
						}
					});
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
