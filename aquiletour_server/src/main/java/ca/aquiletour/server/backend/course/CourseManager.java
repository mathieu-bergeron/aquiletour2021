package ca.aquiletour.server.backend.course;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTask;
import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTaskCompletion;
import ca.aquiletour.core.models.courses.atomic_tasks.git_exercice.GitExerciseTask;
import ca.aquiletour.core.models.courses.atomic_tasks.git_repo.GitRepoTask;
import ca.aquiletour.core.models.courses.base.CourseModel;
import ca.aquiletour.core.models.courses.base.CycleDetectedError;
import ca.aquiletour.core.models.courses.base.OnAtomicTaskAdded;
import ca.aquiletour.core.models.courses.base.OnTaskRemoved;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.courses.student.CourseModelStudent;
import ca.aquiletour.core.models.courses.teacher.CourseModelTeacher;
import ca.aquiletour.core.models.courses.teacher.GroupDescription;
import ca.aquiletour.core.models.dates.AquiletourDate;
import ca.aquiletour.core.models.logs.LogModelCourse;
import ca.aquiletour.core.models.paths.CoursePath;
import ca.aquiletour.core.models.paths.CoursePathStudent;
import ca.aquiletour.core.models.paths.TaskPath;
import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.aquiletour.core.models.session.SessionData;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.dashboard.models.CurrentTask;
import ca.aquiletour.core.pages.dashboard.student.models.CurrentTaskStudent;
import ca.aquiletour.server.backend.git.GitMessages;
import ca.ntro.backend.BackendError;
import ca.ntro.core.Path;
import ca.ntro.core.models.ModelInitializer;
import ca.ntro.core.models.ModelReader;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.models.ValueReader;
import ca.ntro.core.models.lambdas.Break;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.wrappers.options.EmptyOptionException;
import ca.ntro.core.wrappers.options.Optionnal;
import ca.ntro.services.ModelStoreSync;

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
		
		Optionnal<BackendError> backendError = new Optionnal<>();
		
		modelStore.readModel(CourseModelTeacher.class, "admin", coursePath, courseTeacher -> {

			courseTeacher.getGroups().forEachItem((i, group) -> {
				group.getStudents().forEachItem((j, studentId) -> {
					
					CoursePathStudent coursePathStudent = CoursePathStudent.fromCoursePath(coursePath, studentId);
					
					try {

						modelStore.updateModel(CourseModelStudent.class, "admin", coursePathStudent, new ModelUpdater<CourseModelStudent>() {
							@Override
							public void update(CourseModelStudent courseStudent) throws BackendError {
								T.call(this);
								
								addTaskToCourseModel(anchorTaskPath, task, courseStudent, taskType);

								courseStudent.updateStatuses();
							}
						});

					} catch(BackendError e) {
						backendError.set(e);
						throw new Break();
					}
				});
			});
		});
		
		try {

			throw backendError.get();

		} catch (EmptyOptionException e) {}
	}

	private static void addTaskToCourseModel(Path anchorTaskPath, 
											 Task task, 
											 CourseModel<?> courseModel, 
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
				Task rootTask = course.findTaskByPath(Path.fromRawPath("/"));
				rootTask.updateTitle(courseTitle);
			}
		});
	}

	public static void createCourseForUserId(ModelStoreSync modelStore, 
			                                 CoursePath coursePath,
			                                 String courseTitle,
			                                 String userId) throws BackendError {
		T.call(CourseManager.class);

		modelStore.createModel(CourseModelTeacher.class, "admin", coursePath, new ModelInitializer<CourseModelTeacher>() {
			@Override
			public void initialize(CourseModelTeacher course) {
				T.call(this);
				
				Task rootTask = new Task();
				rootTask.setPath(TaskPath.fromRawPath("/"));

				course.registerRootTask(rootTask);
				course.setCoursePath(coursePath);
				course.updateCourseTitle(courseTitle);
			}
		});
		
		modelStore.createModel(LogModelCourse.class, "admin", coursePath, new ModelInitializer<LogModelCourse>() {
			@Override
			public void initialize(LogModelCourse newModel) {
				T.call(this);
			}
		});
	}

	public static void createCourseForUser(ModelStoreSync modelStore, 
			                               CoursePath coursePath,
			                               String courseTitle,
			                               User user) throws BackendError {
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
			                          AquiletourDate endTime, 
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

						}else if(atomicTask instanceof GitRepoTask) {

							GitMessages.registerGitRepoForCourse(course, coursePath, task, (GitRepoTask) atomicTask);

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
			                          			  AquiletourDate endTime, 
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

				courseModel.updateStatuses();
			}
		} );
	}

	public static void updateTaskInfoForStudents(ModelStoreSync modelStore, 
			 								     CoursePath coursePath, 
			 								     Path taskPath, 
			 								     String taskTitle, 
			 								     String taskDescription, 
			 								     AquiletourDate endTime, 
			 								     User user) throws BackendError {
		T.call(CourseManager.class);
		
		Set<String> studentIds = getStudentIds(modelStore, coursePath);
		
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

	public static Set<String> getStudentIds(ModelStoreSync modelStore, 
			 								CoursePath coursePath) throws BackendError {

		T.call(CourseManager.class);
		
		Set<String> studentIds = new HashSet<>();
		
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

	public static void updateCourseScheduleForStudents(ModelStoreSync modelStore, 
                         	                           CoursePath coursePath,
						                               SemesterSchedule semesterSchedule, 
						                               TeacherSchedule teacherSchedule) throws BackendError {
		T.call(CourseManager.class);

		Set<String> studentIds = CourseManager.getStudentIds(modelStore, coursePath);
		
		for(String studentId : studentIds) {

			
			CoursePathStudent coursePathStudent = CoursePathStudent.fromCoursePath(coursePath, studentId);
			
			modelStore.updateModel(CourseModelStudent.class, "admin", coursePathStudent, new ModelUpdater<CourseModelStudent>(){
				@Override
				public void update(CourseModelStudent courseModelStudent) throws BackendError {
					T.call(this);
					
					courseModelStudent.updateSchedule(semesterSchedule, teacherSchedule);
				}
			});
		}
	}

	public static void updateStudentTaskCompletions(ModelStoreSync modelStore, 
			                                        CoursePath coursePath, 
			                                        Path taskPath, 
			                                        String atomicTaskId,
			                                        AtomicTaskCompletion completion,
			                                        User student) throws BackendError {
		T.call(CourseManager.class);
		
		CoursePathStudent coursePathStudent = CoursePathStudent.fromCoursePath(coursePath, student.getId());

		modelStore.updateModel(CourseModelStudent.class, "admin", coursePathStudent, new ModelUpdater<CourseModelStudent>() {
			@Override
			public void update(CourseModelStudent course) {
				T.call(this);
				
				course.updateCompletions(taskPath, atomicTaskId, completion);
				course.updateStatuses();
			}
		});
	}

	public static void updateCourseTaskCompletions(ModelStoreSync modelStore, 
			                                       CoursePath coursePath, 
			                                       Path taskPath, 
			                                       String atomicTaskId,
			                                       AtomicTaskCompletion completion,
			                                       User student) throws BackendError {
		T.call(CourseManager.class);
		
		modelStore.updateModel(CourseModelTeacher.class, "admin", coursePath, new ModelUpdater<CourseModelTeacher>() {
			@Override
			public void update(CourseModelTeacher course) {
				T.call(this);
				
				course.taskCompletedByStudent(taskPath, atomicTaskId, student.getId());
			}
		});
	}

	public static void updateCourseLog(ModelStoreSync modelStore, 
			                           CoursePath coursePath, 
			                           User user,
			                           TaskPath taskPath, 
			                           String groupId,
			                           String atomicTaskId,
			                           AtomicTaskCompletion completion) throws BackendError {
		T.call(CourseManager.class);
		
		modelStore.updateModel(LogModelCourse.class, "admin", coursePath, new ModelUpdater<LogModelCourse>() {
			@Override
			public void update(LogModelCourse courseLog) {
				T.call(this);
				
				courseLog.addAtomicTaskCompletion(taskPath, 
												  user,
						                          groupId, 
						                          atomicTaskId, 
						                          completion);
			}
		});
	}

	public static void createStudentCourse(ModelStoreSync modelStore, 
										   CoursePath coursePath,
			                               String groupId,
			                               User student) throws BackendError {
		T.call(CourseManager.class);
		
		CoursePathStudent coursePathStudent = CoursePathStudent.fromCoursePath(coursePath, student.getId());
		
		modelStore.readModel(CourseModelTeacher.class, "admin", coursePath, courseModelTeacher -> {

			modelStore.createModel(CourseModelStudent.class, "admin", coursePathStudent, new ModelInitializer<CourseModelStudent>() {
				@Override
				public void initialize(CourseModelStudent newModel) {
					T.call(this);
					
					newModel.updateGroupId(groupId);
					newModel.copyCourse(courseModelTeacher);
				}
			});
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
			                                           ValueReader<AtomicTaskCompletion> valueReader) throws BackendError {
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

	@SuppressWarnings("unchecked")
	public static <CM extends CourseModel<CT>, CT extends CurrentTask> 
	        List<CT> getCurrentTasks(ModelStoreSync modelStore, 
	        		                 Class<CM> courseModelClass, 
	        		                 CoursePath coursePath) throws BackendError {

		T.call(CourseManager.class);
		
		List<CT> currentTasks = new ArrayList<>();

		modelStore.readModel(courseModelClass, "admin", coursePath, new ModelReader<CM>() {
			@Override
			public void read(CM courseModel) {
				T.call(this);

				for(Object currentTask : courseModel.currentTasks()) {
					currentTasks.add((CT) currentTask);
				}
			}
		});
		
		return currentTasks;
	}

	public static void updateSessionData(ModelStoreSync modelStore, 
			                             SessionData sessionData, 
			                             CoursePath coursePath, 
			                             User user) throws BackendError {

		T.call(CourseManager.class);
		
		CoursePathStudent coursePathStudent = CoursePathStudent.fromCoursePath(coursePath, user.getId());
		
		List<CurrentTaskStudent> currentTasks = getCurrentTasks(modelStore, CourseModelStudent.class, coursePathStudent);
		
		sessionData.updateCurrentTasks(coursePath, currentTasks);
	}

}
