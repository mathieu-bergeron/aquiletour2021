package ca.aquiletour.server.backend.course;

import java.util.List;

import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.models.courses.CoursePathStudent;
import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTask;
import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTaskCompletion;
import ca.aquiletour.core.models.courses.base.CourseModel;
import ca.aquiletour.core.models.courses.base.OnAtomicTaskAdded;
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
		
		addTask(modelStore, coursePath, nextPath, previousTask, TaskType.PREVIOUS_TASK);
	}

	public static void addNextTask(ModelStoreSync modelStore, 
								   CoursePath coursePath,
			                       Path previousPath, 
			                       Task nextTask) {

		T.call(CourseManager.class);
		
		addTask(modelStore, coursePath, previousPath, nextTask, TaskType.NEXT_TASK);
	}
	

	public static void addSubTask(ModelStoreSync modelStore, 
			                      CoursePath coursePath, 
			                      Path parentTaskPath, 
			                      Task task) {

		T.call(CourseManager.class);
		
		addTask(modelStore, coursePath, parentTaskPath, task, TaskType.SUBTASK);
	}

	public static void addTask(ModelStoreSync modelStore, 
			                   CoursePath coursePath, 
			                   Path parentTaskPath, 
			                   Task task, 
			                   TaskType taskType) {

		T.call(CourseManager.class);
		
		modelStore.updateModel(CourseModelTeacher.class, "admin", coursePath, new ModelUpdater<CourseModelTeacher>() {
			@Override
			public void update(CourseModelTeacher course) {
				T.call(this);
				addTaskToCourseModel(parentTaskPath, task, course, taskType);
			}
		});
	}

	public static void addPreviousTaskForStudents(ModelStoreSync modelStore, 
			                      			      CoursePath coursePath, 
			                      			      Path nextTaskPath, 
			                      			      Task task) {

		T.call(CourseManager.class);

		addTaskForStudents(modelStore, coursePath, nextTaskPath, task, TaskType.PREVIOUS_TASK);
	}

	public static void addSubTaskForStudents(ModelStoreSync modelStore, 
			                      			 CoursePath coursePath, 
			                      			 Path parentTaskPath, 
			                      			 Task task) {

		T.call(CourseManager.class);
		
		addTaskForStudents(modelStore, coursePath, parentTaskPath, task, TaskType.SUBTASK);
	}

	public static void addNextTaskForStudents(ModelStoreSync modelStore, 
			                      			  CoursePath coursePath, 
			                      			  Path previousTaskPath, 
			                      			  Task task) {

		T.call(CourseManager.class);

		addTaskForStudents(modelStore, coursePath, previousTaskPath, task, TaskType.NEXT_TASK);
	}

	private static void addTaskForStudents(ModelStoreSync modelStore, 
			                               CoursePath coursePath, 
			                               Path anchorTaskPath, 
			                               Task task,
			                               TaskType taskType) {
		T.call(CourseManager.class);
		
		CourseModelTeacher courseTeacher = modelStore.getModel(CourseModelTeacher.class, "admin", coursePath);
		
		if(courseTeacher != null) {
			courseTeacher.getGroups().forEachItem((i, group) -> {
				group.getStudents().forEachItem((j, studentId) -> {
					
					CoursePathStudent coursePathStudent = CoursePathStudent.fromCoursePath(coursePath, studentId);

					modelStore.updateModel(CourseModelStudent.class, "admin", coursePathStudent, new ModelUpdater<CourseModelStudent>() {
						@Override
						public void update(CourseModelStudent courseStudent) {
							T.call(this);

							addTaskToCourseModel(anchorTaskPath, task, courseStudent, taskType);
						}
					});
				});
			});
		}
	}

	private static void addTaskToCourseModel(Path anchorTaskPath, Task task, CourseModel courseModel, TaskType taskType) {
		T.call(CourseManager.class);

		switch(taskType) {
			case PREVIOUS_TASK:
				courseModel.addPreviousTaskTo(anchorTaskPath, task);
				break;

			case SUBTASK:
				courseModel.addSubTaskTo(anchorTaskPath, task);
				break;

			case NEXT_TASK:
				courseModel.addNextTaskTo(anchorTaskPath, task);
				break;
		}
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
				
				course.updateTaskInfo(taskPath, taskTitle, taskDescription, endTime, new OnAtomicTaskAdded() {
					@Override
					public void onTaskAdded(AtomicTask task) {
						T.call(this);
						
					}
				});
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

	public static void removeAtomicTaskCompletionTeacher(ModelStoreSync modelStore, 
			                                             CoursePath coursePath, 
			                                             String studentId, 
			                                             Path taskPath, 
			                                             String atomicTaskId) {
		T.call(CourseManager.class);

		modelStore.updateModel(CourseModelTeacher.class, "admin", coursePath, new ModelUpdater<CourseModelTeacher>() {
			@Override
			public void update(CourseModelTeacher existingModel) {
				T.call(this);
				
				existingModel.removeAtomicTaskCompletion(taskPath, studentId, atomicTaskId);
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

	public static void removeAtomicTaskCompletionStudent(ModelStoreSync modelStore, 
			                                             CoursePath coursePath, 
			                                             String studentId, 
			                                             Path taskPath, 
			                                             String atomicTaskId) {
		T.call(CourseManager.class);

		CoursePathStudent coursePathStudent = CoursePathStudent.fromCoursePath(coursePath, studentId);
		
		modelStore.updateModel(CourseModelStudent.class, "admin", coursePathStudent, new ModelUpdater<CourseModelStudent>() {
			@Override
			public void update(CourseModelStudent existingModel) {
				T.call(this);
				
				existingModel.removeAtomicTaskCompletion(taskPath, atomicTaskId);
			}
		});
	}

	public static AtomicTaskCompletion getAtomicTaskCompletionStudent(ModelStoreSync modelStore, 
			                                                          CoursePath coursePath, 
			                                                          String studentId, 
			                                                          Path taskPath, 
			                                                          String atomicTaskId) {
		T.call(CourseManager.class);

		CoursePathStudent coursePathStudent = CoursePathStudent.fromCoursePath(coursePath, studentId);
		
		CourseModelStudent model = modelStore.getModel(CourseModelStudent.class, "admin", coursePathStudent);
		
		return model.atomicTaskCompletion(taskPath, atomicTaskId);
	}
}
