package ca.aquiletour.core;

public class Constants {

	public static final int DEFAULT_USER_ID_LENGTH = 3;

	public static final String CSV_FILE_ENCODING = "latin1";
	
	public static final boolean DISABLE_EMAILS = true;
	
	public static final int APPOINTMENT_DURATION_MINUTES = 5;
	public static final int APPOINTMENT_TIME_INCREMENT_SECONDS = 5*60;
	public static final int APPOINTMENT_DURATION_INCREMENT_SECONDS = 60;
	
	public static final int MAX_PASSWORD_ATTEMPS = 3;

	
	public static String LANG = "fr";
	
	public static final String EMAIL_HOST = "cmontmorency.qc.ca";

	public static final String HOST = "http://localhost:8080";
	
	public static final String GITHOST = "http://localhost:8000";
	
	public static final String GIT_API_URL_PATH = "/_git_api";
	
	public static final String GIT_API_URL = GITHOST + GIT_API_URL_PATH;
	
	public static final String DASHBOARD_URL_SEGMENT = "tableau_de_bord";
	public static final String QUEUE_URL_SEGMENT = "file_d_attente";
	public static final String QUEUES_URL_SEGMENT = "profs_disponibles";
	public static final String COURSE_URL_SEGMENT = "cours";
	public static final String COURSE_LIST_URL_SEGMENT = "mescours";
	public static final String GROUP_LIST_URL_SEGMENT = "mesgroupes";
	public static final String SEMESTER_URL_SEGMENT = "session";
	public static final String USERS_URL_SEGMENT = "usagers";
	public static final String LOGIN_URL_SEGMENT = "connexion";
	public static final String LOGOUT_URL_SEGMENT = "deconnexion";
	public static final String HOME_URL_SEGMENT = "accueil";
	public static final String SEMESTER_LIST_URL_SEGMENT = "sessions";
	public static final String GIT_COMMIT_LIST_URL_SEGMENT = "progression_git";
	public static final String GIT_LATE_STUDENTS_URL_SEGMENT = "retards_git";
	public static final String GIT_STUDENT_SUMMARIES_URL_SEGMENT = "sommaires_git";

	public static final String USER_URL_PARAM = "u";
	public static final String SEMESTER_URL_PARAM = "s";
	public static final String COURSE_URL_PARAM = "c";
	public static final String GROUP_URL_PARAM = "g";
	public static final String DELETE_QUEUE_URL_PARAM = "dQ";
	public static final String CLOSE_QUEUE_URL_PARAM = "cQ";

	public static final String ADD_SEMESTER_URL_PARAM = "aS";
	public static final String ADD_COURSE_URL_PARAM = "aC";

	public static final String DRAFTS_SEMESTER_ID = "_b";
	public static final String DRAFTS_SEMESTER_TEXT = "Brouillons";

	public static final String COURSE_STRUCTURE_ID = "_s";
	public static final String COURSE_STRUCTURE_TEXT = "Structure du cours";

	public static final String ALL_GROUPS_ID = "_g";
	public static final String ALL_GROUPS_TEXT = "Tous les groupes";

	public static final String ALL_COURSES_ID = "_c";
	public static final String ALL_COURSES_TEXT = "Tous les cours";

	public static final String ACTIVE_SEMESTERS_ID = "_as";
	public static final String ACTIVE_SEMESTERS_TEXT = "Sessions actives";

	public static final String ALL_TEACHERS_ID = "_t";
	public static final String ALL_TEACHERS_TEXT = "Tous les profs";

	public static final String ALL_USERS_ID = "_u";
	public static final String ALL_USERS_TEXT = "Tout le monde";

	public static final String CREATE_COURSE_TEXT = "Ajouter un cours";

	public static final String ADMIN_CONTROLLED_SEMESTER_LIST_ID = "all";

	public static final String STUDENT_LIST_MODEL_ID = "students";
	public static final String TEACHER_LIST_MODEL_ID = "teachers";
	public static final String ADMIN_LIST_MODEL_ID = "admins";
	
	public static final String[] RESERVED_IDS = {DRAFTS_SEMESTER_ID, 
		                         				 COURSE_STRUCTURE_ID, 
		                         				 ALL_GROUPS_ID, 
		                         				 ACTIVE_SEMESTERS_ID, 
		                         				 ALL_TEACHERS_ID, 
		                         				 ALL_USERS_ID};

	public static final int NUMBER_OF_CURRENT_TASKS_TEACHER = 3;
}
