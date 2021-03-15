package ca.aquiletour.web.pages.users;

import ca.aquiletour.core.models.users.AnonUser;
import ca.aquiletour.core.models.users.Student;
import ca.aquiletour.core.models.users.SuperUser;
import ca.aquiletour.core.models.users.Teacher;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.users.UserView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class UserViewWeb extends NtroViewWeb implements UserView {

	@Override
	public void initializeViewWeb(NtroContext<?> context) {
	}

	@Override
	public void displayUser(User user) {
		T.call(this);

		HtmlElement userId = this.getRootElement().find("#userId").get(0);
		HtmlElement close = this.getRootElement().find("#close").get(0);
		HtmlElement userEmail = this.getRootElement().find("#userEmail").get(0);
		HtmlElement userPassword = this.getRootElement().find("#userPassword").get(0);
		HtmlElement role = this.getRootElement().find("#role").get(0);

		MustNot.beNull(userId);
		MustNot.beNull(close);
		MustNot.beNull(userEmail);
		MustNot.beNull(userPassword);
		MustNot.beNull(role);
		if(user instanceof Student) {
			role.appendHtml("Student");
		} else if (user instanceof Teacher) {
			role.appendHtml("Teacher");
		} else if (user instanceof AnonUser) {
			role.appendHtml("AnonUser");
		} else if (user instanceof SuperUser) {
			role.appendHtml("SuperUser");
		}//TODO might be a better way to do this

		userId.appendHtml(user.getId());
		userEmail.appendHtml(user.getUserEmail());
		userPassword.appendHtml(user.getUserPassword());
		close.setAttribute("href", "/usagers?deleteUser=" + user.getId());

		getRootElement().setAttribute("id", "user-" + user.getId());
	}



}
