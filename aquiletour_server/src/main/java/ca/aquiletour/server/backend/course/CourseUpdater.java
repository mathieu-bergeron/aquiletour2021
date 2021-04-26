package ca.aquiletour.server.backend.course;

import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.models.courses.base.CourseModel;
import ca.aquiletour.core.models.courses.base.OnTaskAdded;
import ca.aquiletour.core.models.courses.base.OnTaskRemoved;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.courses.teacher.CourseModelTeacher;
import ca.aquiletour.core.models.dates.CourseDate;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.server.backend.git.GitMessages;
import ca.ntro.core.Path;
import ca.ntro.core.models.ModelInitializer;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.trace.T;

public class CourseUpdater {

	public static <CM extends CourseModel> void removeNextTask(ModelStoreSync modelStore, 
								                               Class<CM> courseModelClass,
			                                                   String courseId, 
			                                                   Path taskToModify,
			                                                   Path taskToDelete) {
		T.call(CourseUpdater.class);
		
		modelStore.updateModel(courseModelClass, "admin", courseId, new ModelUpdater<CM>() {
			@Override
			public void update(CM course) {
				T.call(this);
				course.removeNextTask(taskToModify, taskToDelete);
			}
		});
	}

	public static <CM extends CourseModel> void removeSubTask(ModelStoreSync modelStore, 
			                                                  Class<CM> courseModelClass, 
			                                                  CoursePath coursePath, 
			                                                  Path taskToModify, 
			                                                  Path taskToDelete) {
		T.call(CourseUpdater.class);
		
		modelStore.updateModel(courseModelClass, "admin", coursePath, new ModelUpdater<CM>() {
			@Override
			public void update(CM course) {
				T.call(this);
				course.removeSubTask(taskToModify, taskToDelete);
			}
		});
	}

	public static <CM extends CourseModel> void removePreviousTask(ModelStoreSync modelStore, 
			                                                       Class<CM> courseModelClass,
			                                                       String courseId, 
			                                                       Path taskToModify, 
			                                                       Path taskToDelete) {
		T.call(CourseUpdater.class);
		
		modelStore.updateModel(courseModelClass, "admin", courseId, new ModelUpdater<CM>() {
			@Override
			public void update(CM course) {
				T.call(this);
				course.removePreviousTask(taskToModify, taskToDelete);
			}
		});
	}

	public static <CM extends CourseModel> void deleteTask(ModelStoreSync modelStore, 
			                                               Class<CM> courseModelClass,
			                                               String courseId, 
			                                               Path taskToDelete) {
		T.call(CourseUpdater.class);
		
		modelStore.updateModel(courseModelClass, "admin", courseId, new ModelUpdater<CM>() {
			@Override
			public void update(CM course) {
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
	
	public static <CM extends CourseModel> void addPreviousTask(ModelStoreSync modelStore, 
																Class<CM> courseModelClass,
			                                                    String courseId, 
			                                                    Path nextPath, 
			                                                    Task previousTask) {
		T.call(CourseUpdater.class);
		
		modelStore.updateModel(courseModelClass, "admin", courseId, new ModelUpdater<CM>() {
			@Override
			public void update(CM course) {
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

	public static <CM extends CourseModel> void addNextTask(ModelStoreSync modelStore, 
															Class<CM> courseModelClass,
			                                                String courseId, 
			                                                Path previousPath, 
			                                                Task nextTask) {
		T.call(CourseUpdater.class);
		
		modelStore.updateModel(courseModelClass, "admin", courseId, new ModelUpdater<CM>() {
			@Override
			public void update(CM course) {
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
	

	public static <CM extends CourseModel> void addSubTask(ModelStoreSync modelStore, 
			 											   Class<CM> courseModelClass,
			                                               CoursePath coursePath, 
			                                               Path parentTaskPath, 
			                                               Task task) {
		T.call(CourseUpdater.class);
		
		modelStore.updateModel(courseModelClass, "admin", coursePath, new ModelUpdater<CM>() {
			@Override
			public void update(CM course) {
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
	}
	public static <CM extends CourseModel> void updateCourseTitle(ModelStoreSync modelStore, 
			 											          Class<CM> courseModelClass,
			                                                      CoursePath coursePath, 
			                                                      String courseTitle) {
		T.call(CourseUpdater.class);

		modelStore.updateModel(courseModelClass, "admin", coursePath, new ModelUpdater<CM>() {
			@Override
			public void update(CM course) {
				T.call(this);
				
				course.updateCourseTitle(courseTitle);
				Task rootTask = course.findTaskByPath(new Path("/"));
				rootTask.updateTitle(courseTitle);
			}
		});
	}

	public static <CM extends CourseModel> void createCourseForUserId(ModelStoreSync modelStore, 
			                                                          Class<CM> courseModelClass, 
			                                                          CoursePath coursePath,
			                                                          String courseTitle,
			                                                          String userId) {
		T.call(CourseUpdater.class);

		modelStore.createModel(courseModelClass, "admin", coursePath, new ModelInitializer<CM>() {
			@Override
			public void initialize(CM course) {
				T.call(this);
				
				Task rootTask = new Task();
				rootTask.setPath(new Path("/"));

				course.setRootTask(rootTask);
				course.setCoursePath(coursePath);
				course.updateCourseTitle(courseTitle);
			}
		});

	}

	public static <CM extends CourseModel> void createCourseForUser(ModelStoreSync modelStore, 
			                                                        Class<CM> courseModelClass, 
			                                                        CoursePath coursePath,
			                                                        String courseTitle,
			                                                        User user) {
		T.call(CourseUpdater.class);
		
		createCourseForUserId(modelStore, courseModelClass, coursePath, courseTitle, user.getId());
	}

	public static void addGroup(ModelStoreSync modelStore, 
			                    CoursePath coursePath,
			                    String groupId,
			                    User user) {

		T.call(CourseUpdater.class);

		modelStore.updateModel(CourseModelTeacher.class, "admin", coursePath, new ModelUpdater<CourseModelTeacher>() {
			@Override
			public void update(CourseModelTeacher course) {
				T.call(this);

				course.addGroup(groupId);
			}
		});
	}

	public static  <CM extends CourseModel> void updateTaskInfo(ModelStoreSync modelStore, 
			                                                    Class<CM> courseModelClass, 
			                                                    CoursePath coursePath, 
			                                                    Path taskPath, 
			                                                    String taskTitle, 
			                                                    String taskDescription, 
			                                                    CourseDate endTime, 
			                                                    User user) {
		
		T.call(CourseUpdater.class);

		modelStore.updateModel(courseModelClass, "admin", coursePath, new ModelUpdater<CM>() {
			@Override
			public void update(CM course) {
				T.call(this);
				
				course.updateTaskInfo(taskPath, taskTitle, taskDescription, endTime);
			}
		});

	}
	
	
}
