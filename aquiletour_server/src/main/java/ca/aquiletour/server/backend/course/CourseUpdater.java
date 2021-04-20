package ca.aquiletour.server.backend.course;

import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.models.courses.base.CourseModel;
import ca.aquiletour.core.models.courses.base.OnTaskAdded;
import ca.aquiletour.core.models.courses.base.OnTaskRemoved;
import ca.aquiletour.core.models.courses.base.Task;
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
			                              CoursePath coursePath, 
			                              Path taskToModify,
			                              Path taskToDelete) {
		T.call(CourseUpdater.class);
		
		modelStore.updateModel(CourseModel.class, "admin", coursePath, new ModelUpdater<CourseModel>() {
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
	

	public static void addSubTask(ModelStoreSync modelStore, CoursePath coursePath, Path parentTaskPath, Task task) {
		T.call(CourseUpdater.class);
		
		if(modelStore.ifModelExists(CourseModel.class, "admin", coursePath)) {
			
			modelStore.updateModel(CourseModel.class, "admin", coursePath, new ModelUpdater<CourseModel>() {
				@Override
				public void update(CourseModel course) {
					T.call(this);

					course.addSubTaskTo(parentTaskPath, task, new OnTaskAdded() {
						@Override
						public void onTaskAdded() {
							T.call(this);

							GitMessages.registerExercice("FIXME", task.getPath());
						}
					});
				}
			});

		}else {
			
			modelStore.createModel(CourseModel.class, "admin", coursePath, new ModelInitializer<CourseModel>() {
				@Override
				public void initialize(CourseModel course) {
					T.call(this);
					
					Task rootTask = new Task();
					rootTask.setPath(new Path("/"));
					rootTask.setTitle(coursePath.courseId());
					course.setRootTask(rootTask);
					course.setCoursePath(coursePath);
				}
			});

			addSubTask(modelStore, coursePath, parentTaskPath, task);
		}
	}
}
