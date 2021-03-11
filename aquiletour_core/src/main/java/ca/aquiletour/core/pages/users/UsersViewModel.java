package ca.aquiletour.core.pages.users;


import java.util.Map;

import ca.aquiletour.core.models.users.User;
import ca.ntro.core.models.MapObserver;
import ca.ntro.core.mvc.ModelViewSubViewHandler;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public class UsersViewModel 
       extends ModelViewSubViewHandler<UsersModel, UsersView>  {

	@Override
	protected void handle(UsersModel model, UsersView view, ViewLoader subViewLoader) {
		T.call(this);
		
		model.getUsers().observe(new MapObserver<User>() {

			@Override
			public void onValueChanged(Map<String, User> oldValue, Map<String, User> value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onValue(Map<String, User> value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onDeleted(Map<String, User> lastValue) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onEntryAdded(String key, User value) {
				T.call(this);

				UserView userView = (UserView) subViewLoader.createView();
				
				userView.displayUser(value);
				
				view.appendUser(value, userView);
			}

			@Override
			public void onEntryRemoved(String key, User value) {
				T.call(this);

				view.deleteUser(key);
			}
		});
	}
}
