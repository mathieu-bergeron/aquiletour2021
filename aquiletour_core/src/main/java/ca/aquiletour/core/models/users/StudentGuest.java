package ca.aquiletour.core.models.users;

public class StudentGuest extends Student {

	@Override
	public void copyPublicInfomation(User otherUser) {
		super.copyPublicInfomation(otherUser);

		setName(otherUser.getId());
		setSurname("");
	}

}
