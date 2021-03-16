package ca.aquiletour.core.models.users;

public class StudentGuest extends Student {

	@Override
	public void copyPublicInformation(User otherUser) {
		super.copyPublicInformation(otherUser);

		setName(otherUser.getId());
		setSurname("");
	}

}
