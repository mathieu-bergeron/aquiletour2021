package ca.aquiletour.core.pages.course_list.models;

import ca.aquiletour.core.models.paths.CoursePath;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.models.lambdas.Break;
import ca.ntro.core.system.trace.T;

public abstract class CourseListModel implements NtroModel {
	
	private SemesterIds activeSemesters = new SemesterIds();

	private CourseListItemsByCategoryId courseListItemsByCategoryId = new CourseListItemsByCategoryId();
	
	public SemesterIds getActiveSemesters() {
		return activeSemesters;
	}

	public void setActiveSemesters(SemesterIds activeSemesters) {
		this.activeSemesters = activeSemesters;
	}

	public CourseListItemsByCategoryId getCourseListItemsByCategoryId() {
		return courseListItemsByCategoryId;
	}

	public void setCourseListItemsByCategoryId(CourseListItemsByCategoryId courseListItemsByCategoryId) {
		this.courseListItemsByCategoryId = courseListItemsByCategoryId;
	}

	public void addCourseToCategory(String categoryId, CourseListItem courseListItem) {
		T.call(this);
		
		CourseListItems items = getCourseListItemsByCategoryId().valueOf(categoryId);
		if(items == null) {
			items = new CourseListItems();
			getCourseListItemsByCategoryId().putEntry(categoryId, items);
		}
		
		if(!items.contains(courseListItem)) {
			items.addItem(courseListItem);
		}
	}

	public void addGroup(CoursePath coursePath, String groupId) {
		T.call(this);
		
		CourseListItem course = courseByPath(coursePath);
		
		if(course != null) {
			course.addGroup(groupId);
		}
	}

	public CourseListItem courseByPath(CoursePath coursePath) {
		T.call(this);
		
		return getCourseListItemsByCategoryId().reduceTo(CourseListItem.class, null, (categoryId, courseItems, accumulator) -> {
			if(accumulator != null) {
				throw new Break();
			}
			
			accumulator = courseItems.reduceTo(CourseListItem.class, null, (index, courseItem, innerAccumulator) -> {
				if(innerAccumulator != null) {
					throw new Break();
				}
				
				if(courseItem.coursePath().equals(coursePath)) {
					innerAccumulator = courseItem;
				}

				return innerAccumulator;
			});
			
			return accumulator;
		});
	}

	public void addTask(CoursePath coursePath, TaskDescription task) {
		T.call(this);
		
		CourseListItem course = courseByPath(coursePath);
		
		if(course != null) {
			course.addTask(task);
		}
	}

	public void updateQueueOpen(CoursePath coursePath, boolean queueOpen) {
		T.call(this);

		CourseListItem course = courseByPath(coursePath);
		
		if(course != null) {

			course.updateQueueOpen(queueOpen);
		}
	}

	public boolean isCourseInCategory(CoursePath coursePath, String currentCategoryId) {
		T.call(this);
		
		boolean isInCategory = false;
		
		CourseListItems items = getCourseListItemsByCategoryId().valueOf(currentCategoryId);
		
		if(items != null) {
			
			isInCategory = items.reduceTo(Boolean.class, false, (index, courseItem, accumulator) -> {
				if(accumulator) {
					throw new Break();
				}
				
				if(courseItem.coursePath().equals(coursePath)) {
					accumulator = true;
				}
				
				return accumulator;
			});
		}
		
		return isInCategory;
	}

	public void addActiveSemester(String semesterId) {
		T.call(this);

		if(!getActiveSemesters().contains(semesterId)) {
			getActiveSemesters().addItem(semesterId);
		}
	}

	public void removeActiveSemester(String semesterId) {
		T.call(this);

		getActiveSemesters().removeItem(semesterId);
	}
}
