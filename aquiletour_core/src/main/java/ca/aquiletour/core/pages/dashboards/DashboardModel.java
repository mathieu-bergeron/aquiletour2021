package ca.aquiletour.core.pages.dashboards;


import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.aquiletour.core.pages.dashboards.values.ObservableCourseList;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;

public class DashboardModel extends NtroModel {
	private static final long serialVersionUID = -7945536015118582973L;

	// XXX: Must be initialized, otherwise we cannot properly install observers
	private ObservableCourseList courses = new ObservableCourseList();

	@Override
	public void initializeStoredValues() {
		
		/* Par introspection:
		 *  parcourir le graphe d'objet (avec un localHeap pour éviter les cycles, comme pour JsonSerialization)
		 *  prendre en note le valuePath de chaque objets du graphe
		 *  Log.fatalError si un getter retourne un objet null (car alors on peut pas continuer à explorer)
		 *  
		 *  pour chaque valeur qui implante Observable (ou NtroModelValue?):
		 *  + appeler setValuePath() et setOrigin() 
		 *  + comme ça le NtroModelValue est prêt à appeler le modelStore en cas de update
		 *  
		 * 
		 */
		
	}

	public void emptyCourses() {
		ObservableCourseList newList = courses;
		for (int i = 0; i < newList.size(); i++) {
			CourseSummary course = newList.item(i);
			courses.removeItem(course);;
		}
	}

	public void addCourse(CourseSummary course) {
		T.call(this);
		boolean alreadyExists = false;
		if (courses != null) {
			for (int i = 0; i < courses.size(); i++) {
				if (courses.item(i).getCourseId().equals(course.getCourseId())) {
					alreadyExists = true;
				}
			}
			if (!alreadyExists) {
				courses.addItem(course);
			}
		}
	}

	public void deleteCourse(CourseSummary course) {
		T.call(this);

		courses.removeItem(course);
	}

	public void deleteCourseById(String courseId) {
		T.call(this);

		for (int i = 0; i < courses.size(); i++) {
			CourseSummary currentCourse = courses.item(i);
			if (currentCourse.getTitle().equals(courseId)) {
				courses.removeItem(currentCourse);
			}
		}
	}

	public ObservableCourseList getCourses() {
		return courses;
	}

	public void setCourses(ObservableCourseList courses) {
		this.courses = courses;
	}

	public void updateNbAppointmentOfCourse(String courseId, int nbAppointment) {
		for (int i = 0; i < courses.size(); i++) {
			CourseSummary currentCourse = courses.item(i);
			if (currentCourse.getTitle().equals(courseId)) {
				currentCourse.setNumberOfAppointments(nbAppointment);
			}
		}
	}

	public void updateMyAppointment(String courseId, Boolean state) {
		for (int i = 0; i < courses.size(); i++) {
			CourseSummary currentCourse = courses.item(i);
			if (currentCourse.getTitle().equals(courseId)) {
				currentCourse.setMyAppointment(state);
			}
		}

	}
	
	public void setTeacherAvailability(boolean availabilty, String courseId) {
		for (int i = 0; i < courses.size(); i++) {
			CourseSummary currentCourse = courses.item(i);
			if(currentCourse.getCourseId().equals(courseId)) {
				currentCourse.setIsQueueOpen(availabilty);
			}
		}
	}
}
