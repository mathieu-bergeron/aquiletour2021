package ca.aquiletour.server.backend.users;

import ca.aquiletour.core.messages.user.UserChangesPasswordMessage;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendError;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.ModelStoreSync;

public class UserChangesPasswordHandler extends BackendMessageHandler<UserChangesPasswordMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, UserChangesPasswordMessage message) throws BackendError {
		T.call(UserChangesPasswordHandler.class);
		
		if(message.getUser().hasPassword()
				&& message.getCurrentPassword() != null 
				&& !message.getCurrentPassword().isEmpty()
				&& !UserManager.isUserPasswordValid(modelStore, message.getCurrentPassword(), message.getUser())) {
			
			throw new BackendError("Le mot de passe courant est erroné.");

		}else if(message.getNewPasswordA() == null
				|| message.getNewPasswordA().length() < 6){

			throw new BackendError("SVP entrer un mot de passe d'au moins 6 caractères.");

		}else if(!message.getNewPasswordA().equals(message.getNewPasswordB())) {

			throw new BackendError("Les mots de passes ne correspondent pas.");
			
		}else {
			
			UserManager.setUserPassword(modelStore, message.getNewPasswordA(), message.getUser());
		}
		
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, UserChangesPasswordMessage message) {
		T.call(UserChangesPasswordHandler.class);
		
	}

}
