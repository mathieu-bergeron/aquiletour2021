package ca.aquiletour.server.backend.course;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.models.courses.CoursePathStudent;
import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTask;
import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTaskCompletion;
import ca.aquiletour.core.models.courses.atomic_tasks.git_exercice.GitExerciseTask;
import ca.aquiletour.core.models.courses.base.CourseModel;
import ca.aquiletour.core.models.courses.base.CycleDetectedError;
import ca.aquiletour.core.models.courses.base.OnAtomicTaskAdded;
import ca.aquiletour.core.models.courses.base.OnTaskRemoved;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.courses.base.TaskPath;
import ca.aquiletour.core.models.courses.student.CourseModelStudent;
import ca.aquiletour.core.models.courses.teacher.CourseModelTeacher;
import ca.aquiletour.core.models.courses.teacher.GroupDescription;
import ca.aquiletour.core.models.dates.CourseDate;
import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.server.backend.git.GitMessages;
import ca.ntro.backend.BackendError;
import ca.ntro.core.Path;
import ca.ntro.core.models.ModelInitializer;
import ca.ntro.core.models.ModelReader;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.models.StoredProperty;
import ca.ntro.core.models.ValueReader;
import ca.ntro.core.models.lambdas.Break;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.wrappers.options.EmptyOptionException;
import ca.ntro.core.wrappers.options.Optionnal;

public class CourseManager {

	public static void removeNextTask(ModelStoreSync modelStore, 
			                          String courseId, 
			                          Path taskToModify,
                                      Path taskToDelete) throws BackendError {

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
			                         Path taskToDelete) throws BackendError {

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
			                              Path taskToDelete) throws BackendError {
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
			                      Path taskToDelete) throws BackendError {

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
			                           Task previousTask) throws BackendError {

		T.call(CourseManager.class);
		
		addTask(modelStore, coursePath, nextPath, previousTask, TaskType.PREVIOUS_TASK);
	}

	public static void addNextTask(ModelStoreSync modelStore, 
								   CoursePath coursePath,
			                       Path previousPath, 
			                       Task nextTask) throws BackendError {

		T.call(CourseManager.class);
		
		addTask(modelStore, coursePath, previousPath, nextTask, TaskType.NEXT_TASK);
	}
	

	public static void addSubTask(ModelStoreSync modelStore, 
			                      CoursePath coursePath, 
			                      Path parentTaskPath, 
			                      Task task) throws BackendError {

		T.call(CourseManager.class);
		
		addTask(modelStore, coursePath, parentTaskPath, task, TaskType.SUBTASK);
	}

	public static void addTask(ModelStoreSync modelStore, 
			                   CoursePath coursePath, 
			                   Path parentTaskPath, 
			                   Task task, 
			                   TaskType taskType) throws BackendError {

		T.call(CourseManager.class);
		
		modelStore.updateModel(CourseModelTeacher.class, "admin", coursePath, new ModelUpdater<CourseModelTeacher>() {
			@Override
			public void update(CourseModelTeacher course) throws BackendError {
				T.call(this);
				addTaskToCourseModel(parentTaskPath, task, course, taskType);
			}
		});
	}

	public static void addPreviousTaskForStudents(ModelStoreSync modelStore, 
			                      			      CoursePath coursePath, 
			                      			      Path nextTaskPath, 
			                      			      Task task) throws BackendError {

		T.call(CourseManager.class);

		addTaskForStudents(modelStore, coursePath, nextTaskPath, task, TaskType.PREVIOUS_TASK);
	}

	public static void addSubTaskForStudents(ModelStoreSync modelStore, 
			                      			 CoursePath coursePath, 
			                      			 Path parentTaskPath, 
			                      			 Task task) throws BackendError {

		T.call(CourseManager.class);
		
		addTaskForStudents(modelStore, coursePath, parentTaskPath, task, TaskType.SUBTASK);
	}

	public static void addNextTaskForStudents(ModelStoreSync modelStore, 
			                      			  CoursePath coursePath, 
			                      			  Path previousTaskPath, 
			                      			  Task task) throws BackendError {

		T.call(CourseManager.class);

		addTaskForStudents(modelStore, coursePath, previousTaskPath, task, TaskType.NEXT_TASK);
	}

	private static void addTaskForStudents(ModelStoreSync modelStore, 
			                               CoursePath coursePath, 
			                               Path anchorTaskPath, 
			                               Task task,
			                               TaskType taskType) throws BackendError {
		T.call(CourseManager.class);
		
		CourseModelTeacher courseTeacher = modelStore.getModel(CourseModelTeacher.class, "admin", coursePath);
		
		Optionnal<BackendError> backendError = new Optionnal<BackendError>(null);
		
		if(courseTeacher != null) {
			courseTeacher.getGroups().forEachItem((i, group) -> {
				group.getStudents().forEachItem((j, studentId) -> {
					
					CoursePathStudent coursePathStudent = CoursePathStudent.fromCoursePath(coursePath, studentId);
					
					try {

						modelStore.updateModel(CourseModelStudent.class, "admin", coursePathStudent, new ModelUpdater<CourseModelStudent>() {
							@Override
							public void update(CourseModelStudent courseStudent) throws BackendError {
								T.call(this);
								
								addTaskToCourseModel(anchorTaskPath, task, courseStudent, taskType);
							}
						});

					} catch(BackendError e) {
						backendError.set(e);
						throw new Break();
					}
				});
			});
		}
		
		if(!backendError.isEmpty()) {
			try {

				throw backendError.get();

			} catch (EmptyOptionException e) {}
		}
	}

	private static void addTaskToCourseModel(Path anchorTaskPath, 
											 Task task, 
											 CourseModel courseModel, 
											 TaskType taskType) throws BackendError {
		T.call(CourseManager.class);
		
		try {

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

		}catch(CycleDetectedError e){
			throw new BackendError("Ce lien créerait une dépendance cyclique et ne peut donc pas être ajouté.");
		}
	}

	private static void registerGitExercise(CourseModelTeacher course, CoursePath coursePath, Task task, GitExerciseTask gitTask) {
		T.call(CourseManager.class);
		
		course.getGroups().forEachItem((i, group) -> {
			String groupId = group.getGroupId();
			GitMessages.registerExercise(coursePath, 
										 groupId, 
										 task.getPath(), 
										 gitTask);
		});
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
			                             String courseTitle) throws BackendError {

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
				rootTask.setPath(new TaskPath("/"));

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
			                    User user) throws BackendError {

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
			                          User user) throws BackendError {
		
		T.call(CourseManager.class);

		modelStore.updateModel(CourseModelTeacher.class, "admin", coursePath, new ModelUpdater<CourseModelTeacher>() {
			@Override
			public void update(CourseModelTeacher course) {
				T.call(this);
				
				course.updateTaskInfo(taskPath, taskTitle, taskDescription, endTime, new OnAtomicTaskAdded() {
					@Override
					public void onAtomicTaskAdded(Task task, AtomicTask atomicTask) {
						T.call(this);

						if(atomicTask instanceof GitExerciseTask) {
							registerGitExercise(course, coursePath, task, (GitExerciseTask) atomicTask);
						}
					}
				});
			}
		});
	}

	public static void updateTaskInfoForStudentId(ModelStoreSync modelStore, 
			                          			  CoursePath coursePath, 
			                          			  Path taskPath, 
			                          			  String taskTitle, 
			                          			  String taskDescription, 
			                          			  CourseDate endTime, 
			                          			  String studentId) throws BackendError {
		
		T.call(CourseManager.class);
		
		CoursePathStudent coursePathStudent = CoursePathStudent.fromCoursePath(coursePath, studentId);
		
		modelStore.updateModel(CourseModelStudent.class, "admin", coursePathStudent, new ModelUpdater<CourseModelStudent>() {
			@Override
			public void update(CourseModelStudent courseModel) throws BackendError {
				T.call(this);
				
				courseModel.updateTaskInfo(taskPath, taskTitle, taskDescription, endTime, new OnAtomicTaskAdded() {
					@Override
					public void onAtomicTaskAdded(Task task, AtomicTask atomicTask) {
						T.call(this);
					}
				});
			}
		} );
	}

	public static void updateTaskInfoForStudents(ModelStoreSync modelStore, 
			 								     CoursePath coursePath, 
			 								     Path taskPath, 
			 								     String taskTitle, 
			 								     String taskDescription, 
			 								     CourseDate endTime, 
			 								     User user) throws BackendError {
		T.call(CourseManager.class);
		
		List<String> studentIds = getStudentIds(modelStore, coursePath);
		
		for(String studentId : studentIds) {
			
			updateTaskInfoForStudentId(modelStore, 
					                   coursePath, 
					                   taskPath, 
					                   taskTitle, 
					                   taskDescription, 
					                   endTime, 
					                   studentId);
		}
	}

	public static List<String> getStudentIds(ModelStoreSync modelStore, 
			 								 CoursePath coursePath) {

		T.call(CourseManager.class);
		
		List<String> studentIds = new ArrayList<>();
		
		modelStore.readModel(CourseModelTeacher.class, "admin", coursePath, new ModelReader<CourseModelTeacher>() {
			@Override
			public void read(CourseModelTeacher courseModel) {
				T.call(this);

				courseModel.getGroups().forEachItem((i, g) -> {
					g.getStudents().forEachItem((j, studentId) ->{
						if(!studentIds.contains(studentId)) {
							studentIds.add(studentId);
						}
					});
				});
			}
		});

		return studentIds;
	}

	public static void updateCourseSchedule(ModelStoreSync modelStore, 
                         	                CoursePath coursePath,
						                    SemesterSchedule semesterSchedule, 
						                    TeacherSchedule teacherSchedule) throws BackendError {
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
			                               User user) throws BackendError {
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
			                               String groupId,
			                               User student) {
		T.call(CourseManager.class);
		
		CoursePathStudent coursePathStudent = CoursePathStudent.fromCoursePath(coursePath, student.getId());
		
		System.out.println(coursePathStudent.toFileName());
		
		modelStore.createModel(CourseModelStudent.class, "admin", coursePathStudent, new ModelInitializer<CourseModelStudent>() {
			@Override
			public void initialize(CourseModelStudent newModel) {
				T.call(this);
				
				newModel.updateGroupId(groupId);
				newModel.copyCourse(courseTeacher);
			}
		});
	}

	public static void updateAtomicTaskCompletionTeacher(ModelStoreSync modelStore, 
			                                              CoursePath coursePath, 
			                                              String studentId, 
			                                              Path taskPath, 
			                                              String atomicTaskId, 
			                                              AtomicTaskCompletion newCompletion) throws BackendError {
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
			                                             String atomicTaskId) throws BackendError {
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
			                                              AtomicTaskCompletion newCompletion) throws BackendError {
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
			                                             String atomicTaskId) throws BackendError {
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

	public static void readAtomicTaskCompletionStudent(ModelStoreSync modelStore, 
			                                           CoursePath coursePath, 
			                                           String studentId, 
			                                           Path taskPath, 
			                                           String atomicTaskId,
			                                           ValueReader<AtomicTaskCompletion> valueReader) {
		T.call(CourseManager.class);

		CoursePathStudent coursePathStudent = CoursePathStudent.fromCoursePath(coursePath, studentId);
		
		modelStore.readModel(CourseModelStudent.class, "admin", coursePathStudent, new ModelReader<CourseModelStudent>() {
			@Override
			public void read(CourseModelStudent model) {
				T.call(this);
				
				valueReader.read(model.atomicTaskCompletion(taskPath, atomicTaskId));
			}
		});
	}

}
