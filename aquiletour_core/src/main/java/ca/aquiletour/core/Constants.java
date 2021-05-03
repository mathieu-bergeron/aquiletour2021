package ca.aquiletour.core;

public class Constants {
	
	public static String LANG = "fr";
	
	public static final String HOST = "http://localhost:8080";
	
	public static final String GITHOST = "http://localhost:8000";
	
	public static final String GIT_API_URL_PATH = "/_git_api";
	
	public static final String GIT_API_URL = GITHOST + GIT_API_URL_PATH;
	
	
	public static final String DASHBOARD_URL_SEGMENT = "tableau_de_bord";
	public static final String QUEUE_URL_SEGMENT = "salles";
	public static final String QUEUES_URL_SEGMENT = "profs_disponibles";
	public static final String COURSE_URL_SEGMENT = "cours";
	public static final String GROUP_URL_SEGMENT = "groupes";
	public static final String SEMESTER_URL_SEGMENT = "sessions";
	public static final String USERS_URL_SEGMENT = "usagers";
	public static final String LOGIN_URL_SEGMENT = "connexion";
	public static final String LOGOUT_URL_SEGMENT = "deconnexion";
	public static final String HOME_URL_SEGMENT = "accueil";
	public static final String GIT_COMMIT_LIST_URL_SEGMENT = "gitProgression";
	public static final String GIT_LATE_STUDENTS_URL_SEGMENT = "gitRetards";
	public static final String GIT_STUDENT_SUMMARIES_URL_SEGMENT = "gitSommaires";

	public static final String USER_URL_PARAM = "u";
	public static final String SEMESTER_URL_PARAM = "s";
	public static final String DELETE_QUEUE_URL_PARAM = "dQ";
	public static final String CLOSE_QUEUE_URL_PARAM = "cQ";

}
