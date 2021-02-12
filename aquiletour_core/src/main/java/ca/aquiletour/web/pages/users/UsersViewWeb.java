package ca.aquiletour.web.pages.users;

import ca.aquiletour.core.pages.users.UsersView;
import ca.aquiletour.core.pages.users.UserView;
import ca.aquiletour.core.pages.users.values.User;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class UsersViewWeb extends NtroViewWeb implements UsersView {

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void appendUser(User user, UserView userView) {
		T.call(this);
		T.here();
		
		HtmlElement container = this.getRootElement().children("#showUsers-container").get(0);
		
		MustNot.beNull(container);
		
		UserViewWeb userViewWeb = (UserViewWeb) userView;
		
		container.appendElement(userViewWeb.getRootElement());
	}

	@Override
	public void deleteUser(String userId) {
		T.call(this);

		HtmlElement container = this.getRootElement().children("#showUsers-container").get(0);

		String selector = "#user-" + userId;

		HtmlElement userElement = container.children(selector).get(0);
		
		userElement.remove();

	}

}
