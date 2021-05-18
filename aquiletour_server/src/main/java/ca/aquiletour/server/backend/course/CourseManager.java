package ca.aquiletour.server.backend.course;

import java.util.List;

import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.models.courses.CoursePathStudent;
import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTaskCompletion;
import ca.aquiletour.core.models.courses.atomic_tasks.git_repo.GitRepoSubmitted;
import ca.aquiletour.core.models.courses.base.OnTaskAdded;
import ca.aquiletour.core.models.courses.base.OnTaskRemoved;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.courses.student.CourseModelStudent;
import ca.aquiletour.core.models.courses.teacher.CourseModelTeacher;
import ca.aquiletour.core.models.courses.teacher.GroupDescription;
import ca.aquiletour.core.models.dates.CourseDate;
import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.server.backend.git.GitMessages;
import ca.ntro.core.Path;
import ca.ntro.core.models.ModelInitializer;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;

public class CourseManager {

	public static void removeNextTask(ModelStoreSync modelStore, 
			                          String courseId, 
			                          Path taskToModify,
                                      Path taskToDelete) {

		T.call(CourseManager.class);
		
		modelStore.updateModel(CourseModelTeacher.class, "admin", courseId, new ModelUpdater<CourseModelTeacher>() {
			@Override
			public void update(CourseModelTeacher course) {
				T.call(this);
				course.removeNextTask(taskToModify, taskToDelete);
			}
		});
	}

	public static void removeSubTask(ModelStoreSync modelStore, 
			                         CoursePath coursePath, 
			                         Path taskToModify, 
			                         Path taskToDelete) {

		T.call(CourseManager.class);
		
		modelStore.updateModel(CourseModelTeacher.class, "admin", coursePath, new ModelUpdater<CourseModelTeacher>() {
			@Override
			public void update(CourseModelTeacher course) {
				T.call(this);
				course.removeSubTask(taskToModify, taskToDelete);
			}
		});
	}

	public static void removePreviousTask(ModelStoreSync modelStore, 
			                              String courseId, 
			                              Path taskToModify, 
			                              Path taskToDelete) {
		T.call(CourseManager.class);
		
		modelStore.updateModel(CourseModelTeacher.class, "admin", courseId, new ModelUpdater<CourseModelTeacher>() {
			@Override
			public void update(CourseModelTeacher course) {
				T.call(this);
				course.removePreviousTask(taskToModify, taskToDelete);
			}
		});
	}

	public static void deleteTask(ModelStoreSync modelStore, 
								  CoursePath coursePath,
			                      Path taskToDelete) {

		T.call(CourseManager.class);
		
		modelStore.updateModel(CourseModelTeacher.class, "admin", coursePath, new ModelUpdater<CourseModelTeacher>() {
			@Override
			public void update(CourseModelTeacher course) {
				T.call(this);
				course.deleteTask(taskToDelete, new OnTaskRemoved() {
					@Override
					public void onTaskRemoved(Task task) {
						T.call(this);

						deleteExercice(coursePath, task, course);
					}
				});
			}
		});
	}
	
	public static void addPreviousTask(ModelStoreSync modelStore, 
			                           CoursePath coursePath, 
			                           Path nextPath, 
			                           Task previousTask) {

		T.call(CourseManager.class);
		
		modelStore.updateModel(CourseModelTeacher.class, "admin", coursePath, new ModelUpdater<CourseModelTeacher>() {
			@Override
			public void update(CourseModelTeacher course) {
				T.call(this);

				course.addPreviousTaskTo(nextPath, previousTask, new OnTaskAdded() {
					@Override
					public void onTaskAdded() {
						T.call(this);
						
						registerExercice(coursePath, previousTask, course);
					}
				});
			}
		});
	}

	public static void addNextTask(ModelStoreSync modelStore, 
								   CoursePath coursePath,
			                       Path previousPath, 
			                       Task nextTask) {

		T.call(CourseManager.class);
		
		modelStore.updateModel(CourseModelTeacher.class, "admin", coursePath, new ModelUpdater<CourseModelTeacher>() {
			@Override
			public void update(CourseModelTeacher course) {
				T.call(this);

				course.addNextTaskTo(previousPath, nextTask, new OnTaskAdded() {
					@Override
					public void onTaskAdded() {
						T.call(this);

						registerExercice(coursePath, nextTask, course);
					}
				});
			}
		});
	}
	

	public static void addSubTask(ModelStoreSync modelStore, 
			                      CoursePath coursePath, 
			                      Path parentTaskPath, 
			                      Task task) {

		T.call(CourseManager.class);
		
		modelStore.updateModel(CourseModelTeacher.class, "admin", coursePath, new ModelUpdater<CourseModelTeacher>() {
			@Override
			public void update(CourseModelTeacher course) {
				T.call(this);

				course.addSubTaskTo(parentTaskPath, task, new OnTaskAdded() {
					@Override
					public void onTaskAdded() {
						T.call(this);
						
						registerExercice(coursePath, task, course);
					}
				});
			}
		});
	}

	private static void registerExercice(CoursePath coursePath, Task task, CourseModelTeacher course) {
		T.call(CourseManager.class);

		for(GroupDescription groupDescription : course.getGroups().getValue()) {
			String groupId = groupDescription.getGroupId();
			GitMessages.registerExercice(coursePath, groupId, task.getPath());
		}
	}

	private static void deleteExercice(CoursePath coursePath, Task task, CourseModelTeacher course) {
		T.call(CourseManager.class);

		for(GroupDescription groupDescription : course.getGroups().getValue()) {
			String groupId = groupDescription.getGroupId();
			GitMessages.deleteExercise(coursePath, groupId, task.getPath());
		}
	}

	public static void updateCourseTitle(ModelStoreSync modelStore, 
			                             CoursePath coursePath, 
			                             String courseTitle) {

		T.call(CourseManager.class);

		modelStore.updateModel(CourseModelTeacher.class, "admin", coursePath, new ModelUpdater<CourseModelTeacher>() {
			@Override
			public void update(CourseModelTeacher course) {
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
		T.call(CourseManager.class);

		modelStore.createModel(CourseModelTeacher.class, "admin", coursePath, new ModelInitializer<CourseModelTeacher>() {
			@Override
			public void initialize(CourseModelTeacher course) {
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
		T.call(CourseManager.class);
		
		createCourseForUserId(modelStore, coursePath, courseTitle, user.getId());
	}

	public static void addGroup(ModelStoreSync modelStore, 
			                    CoursePath coursePath,
			                    String groupId,
			                    List<User> studentsToAdd,
			                    User user) {

		T.call(CourseManager.class);

		modelStore.updateModel(CourseModelTeacher.class, "admin", coursePath, new ModelUpdater<CourseModelTeacher>() {
			@Override
			public void update(CourseModelTeacher course) {
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
		
		T.call(CourseManager.class);

		modelStore.updateModel(CourseModelTeacher.class, "admin", coursePath, new ModelUpdater<CourseModelTeacher>() {
			@Override
			public void update(CourseModelTeacher course) {
				T.call(this);
				
				course.updateTaskInfo(taskPath, taskTitle, taskDescription, endTime);
			}
		});

	}

	public static void updateCourseSchedule(ModelStoreSync modelStore, 
                         	                CoursePath coursePath,
						                    SemesterSchedule semesterSchedule, 
						                    TeacherSchedule teacherSchedule) {
		T.call(CourseManager.class);

		modelStore.updateModel(CourseModelTeacher.class, "admin", coursePath, new ModelUpdater<CourseModelTeacher>() {
			@Override
			public void update(CourseModelTeacher course) {
				T.call(this);
				
				course.updateSchedule(semesterSchedule, teacherSchedule);
			}
		});
	}

	public static void taskCompletedByUser(ModelStoreSync modelStore, 
			                               CoursePath coursePath, 
			                               Path taskPath, 
			                               String atomicTaskId,
			                               User user) {
		T.call(CourseManager.class);

		modelStore.updateModel(CourseModelTeacher.class, "admin", coursePath, new ModelUpdater<CourseModelTeacher>() {
			@Override
			public void update(CourseModelTeacher course) {
				T.call(this);
				
				course.taskCompletedByStudent(taskPath, atomicTaskId, user.getId());
			}
		});
	}

	public static CourseModelTeacher getCourse(ModelStoreSync modelStore, Class<CourseModelTeacher> courseModelClass, CoursePath coursePath) {
		T.call(CourseManager.class);
		
		return modelStore.getModel(courseModelClass, "admin", coursePath);
	}

	public static void createStudentCourse(ModelStoreSync modelStore, 
										   CoursePath coursePath,
			                               CourseModelTeacher courseTeacher, 
			                               User student) {
		T.call(CourseManager.class);
		
		CoursePathStudent coursePathStudent = CoursePathStudent.fromCoursePath(coursePath, student.getRegistrationId());
		
		System.out.println(coursePathStudent.toFileName());
		
		modelStore.createModel(CourseModelStudent.class, "admin", coursePathStudent, new ModelInitializer<CourseModelStudent>() {
			@Override
			public void initialize(CourseModelStudent newModel) {
				T.call(this);
			
					
				newModel.copyCourse(courseTeacher);
			}
		});
	}

	public static void updateAtomicTaskCompletionTeacher(ModelStoreSync modelStore, 
			                                              CoursePath coursePath, 
			                                              String studentId, 
			                                              Path taskPath, 
			                                              String atomicTaskId, 
			                                              AtomicTaskCompletion newCompletion) {
		T.call(CourseManager.class);

		modelStore.updateModel(CourseModelTeacher.class, "admin", coursePath, new ModelUpdater<CourseModelTeacher>() {
			@Override
			public void update(CourseModelTeacher existingModel) {
				T.call(this);
				
				existingModel.updateAtomicTaskCompletion(taskPath, studentId, atomicTaskId, newCompletion);
			}
		});
	}

	public static void updateAtomicTaskCompletionStudent(ModelStoreSync modelStore, 
			                                              CoursePath coursePath, 
			                                              String studentId, 
			                                              Path taskPath, 
			                                              String atomicTaskId, 
			                                              AtomicTaskCompletion newCompletion) {
		T.call(CourseManager.class);

		CoursePathStudent coursePathStudent = CoursePathStudent.fromCoursePath(coursePath, studentId);
		
		modelStore.updateModel(CourseModelStudent.class, "admin", coursePathStudent, new ModelUpdater<CourseModelStudent>() {
			@Override
			public void update(CourseModelStudent existingModel) {
				T.call(this);
				
				existingModel.updateAtomicTaskCompletion(taskPath, atomicTaskId, newCompletion);
			}
		});
	}
}
