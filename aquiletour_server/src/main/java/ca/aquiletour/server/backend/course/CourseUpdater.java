package ca.aquiletour.server.backend.course;

import java.util.List;

import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.models.courses.base.OnTaskAdded;
import ca.aquiletour.core.models.courses.base.OnTaskRemoved;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.courses.model.Course;
import ca.aquiletour.core.models.dates.CourseDate;
import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.aquiletour.core.models.user.Student;
import ca.aquiletour.core.models.user.User;
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
		
		modelStore.updateModel(Course.class, "admin", courseId, new ModelUpdater<Course>() {
			@Override
			public void update(Course course) {
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
		
		modelStore.updateModel(Course.class, "admin", coursePath, new ModelUpdater<Course>() {
			@Override
			public void update(Course course) {
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
		
		modelStore.updateModel(Course.class, "admin", courseId, new ModelUpdater<Course>() {
			@Override
			public void update(Course course) {
				T.call(this);
				course.removePreviousTask(taskToModify, taskToDelete);
			}
		});
	}

	public static void deleteTask(ModelStoreSync modelStore, 
			                      String courseId, 
			                      Path taskToDelete) {

		T.call(CourseUpdater.class);
		
		modelStore.updateModel(Course.class, "admin", courseId, new ModelUpdater<Course>() {
			@Override
			public void update(Course course) {
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
	
	public static void addPreviousTask(ModelStoreSync modelStore, 
			                           CoursePath coursePath, 
			                           Path nextPath, 
			                           Task previousTask) {

		T.call(CourseUpdater.class);
		
		modelStore.updateModel(Course.class, "admin", coursePath, new ModelUpdater<Course>() {
			@Override
			public void update(Course course) {
				T.call(this);

				course.addPreviousTaskTo(nextPath, previousTask, new OnTaskAdded() {
					@Override
					public void onTaskAdded() {
						T.call(this);
						
						GitMessages.registerExercice(coursePath, previousTask.getPath());
					}
				});
			}
		});
	}

	public static void addNextTask(ModelStoreSync modelStore, 
								   CoursePath coursePath,
			                       Path previousPath, 
			                       Task nextTask) {

		T.call(CourseUpdater.class);
		
		modelStore.updateModel(Course.class, "admin", coursePath, new ModelUpdater<Course>() {
			@Override
			public void update(Course course) {
				T.call(this);

				course.addNextTaskTo(previousPath, nextTask, new OnTaskAdded() {
					@Override
					public void onTaskAdded() {
						T.call(this);

						GitMessages.registerExercice(coursePath, nextTask.getPath());
					}
				});
			}
		});
	}
	

	public static void addSubTask(ModelStoreSync modelStore, 
			                      CoursePath coursePath, 
			                      Path parentTaskPath, 
			                      Task task) {

		T.call(CourseUpdater.class);
		
		modelStore.updateModel(Course.class, "admin", coursePath, new ModelUpdater<Course>() {
			@Override
			public void update(Course course) {
				T.call(this);

				course.addSubTaskTo(parentTaskPath, task, new OnTaskAdded() {
					@Override
					public void onTaskAdded() {
						T.call(this);

						GitMessages.registerExercice(coursePath, task.getPath());
					}
				});
			}
		});
	}

	public static void updateCourseTitle(ModelStoreSync modelStore, 
			                             CoursePath coursePath, 
			                             String courseTitle) {

		T.call(CourseUpdater.class);

		modelStore.updateModel(Course.class, "admin", coursePath, new ModelUpdater<Course>() {
			@Override
			public void update(Course course) {
				T.call(this);
				
				course.updateCourseTitle(courseTitle);
				Task rootTask = course.findTaskByPath(new Path("/"));
				rootTask.updateTitle(courseTitle);
			}
		});
	}

	public static void createCourseForUserId(ModelStoreSync modelStore, 
			                                 CoursePath coursePath,
			                                 String courseTitle,
			                                 String userId) {
		T.call(CourseUpdater.class);

		modelStore.createModel(Course.class, "admin", coursePath, new ModelInitializer<Course>() {
			@Override
			public void initialize(Course course) {
				T.call(this);
				
				Task rootTask = new Task();
				rootTask.setPath(new Path("/"));

				course.registerRootTask(rootTask);
				course.setCoursePath(coursePath);
				course.updateCourseTitle(courseTitle);
			}
		});

	}

	public static void createCourseForUser(ModelStoreSync modelStore, 
			                               CoursePath coursePath,
			                               String courseTitle,
			                               User user) {
		T.call(CourseUpdater.class);
		
		createCourseForUserId(modelStore, coursePath, courseTitle, user.getId());
	}

	public static void addGroup(ModelStoreSync modelStore, 
			                    CoursePath coursePath,
			                    String groupId,
			                    List<User> studentsToAdd,
			                    User user) {

		T.call(CourseUpdater.class);

		modelStore.updateModel(Course.class, "admin", coursePath, new ModelUpdater<Course>() {
			@Override
			public void update(Course course) {
				T.call(this);

				course.addGroup(groupId, studentsToAdd);
			}
		});
	}

	public static void updateTaskInfo(ModelStoreSync modelStore, 
			                          CoursePath coursePath, 
		                              Path taskPath, 
	                                  String taskTitle, 
			                          String taskDescription, 
			                          CourseDate endTime, 
			                          User user) {
		
		T.call(CourseUpdater.class);

		modelStore.updateModel(Course.class, "admin", coursePath, new ModelUpdater<Course>() {
			@Override
			public void update(Course course) {
				T.call(this);
				
				course.updateTaskInfo(taskPath, taskTitle, taskDescription, endTime);
			}
		});

	}

	public static void updateCourseSchedule(ModelStoreSync modelStore, 
                         	                CoursePath coursePath,
						                    SemesterSchedule semesterSchedule, 
						                    TeacherSchedule teacherSchedule) {
		T.call(CourseUpdater.class);

		modelStore.updateModel(Course.class, "admin", coursePath, new ModelUpdater<Course>() {
			@Override
			public void update(Course course) {
				T.call(this);
				
				course.updateSchedule(semesterSchedule, teacherSchedule);
			}
		});
	}

	public static void taskCompletedByUser(ModelStoreSync modelStore, 
			                               CoursePath coursePath, 
			                               Path taskPath, 
			                               User user) {
		T.call(CourseUpdater.class);

		modelStore.updateModel(Course.class, "admin", coursePath, new ModelUpdater<Course>() {
			@Override
			public void update(Course course) {
				T.call(this);
				
				course.taskCompletedByStudent(taskPath, user.getId());
			}
		});
	}

	public static Course getCourse(ModelStoreSync modelStore, Class<Course> courseModelClass, CoursePath coursePath) {
		T.call(CourseUpdater.class);
		
		return modelStore.getModel(courseModelClass, "admin", coursePath);
	}
}
