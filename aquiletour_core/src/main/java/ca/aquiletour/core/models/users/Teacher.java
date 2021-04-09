package ca.aquiletour.core.models.users;

public class Teacher extends User {

	@Override
	public User toSessionUser() {
		Teacher sessionUser = new Teacher();
		
		copySessionOnlyInfo(sessionUser);
		
		return sessionUser;
	}

}
