package ca.aquiletour.server.backend.users;

import ca.aquiletour.core.messages.user.UserChangesPassword;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendMessageHandlerError;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class UserChangesPasswordHandler extends BackendMessageHandler<UserChangesPassword> {

	@Override
	public void handleNow(ModelStoreSync modelStore, UserChangesPassword message) throws BackendMessageHandlerError {
		T.call(UserChangesPasswordHandler.class);
		
		if(message.getCurrentPassword() != null 
				&& !message.getCurrentPassword().isEmpty()
				&& !UserManager.isUserPasswordValid(modelStore, message.getCurrentPassword(), message.getUser())) {
			
			throw new BackendMessageHandlerError("Le mot de passe courant est erroné.");

		}else if(message.getNewPasswordA() == null
				|| message.getNewPasswordA().length() < 6){

			throw new BackendMessageHandlerError("SVP entrer un mot de passe d'au moins 6 caractères.");

		}else if(!message.getNewPasswordA().equals(message.getNewPasswordB())) {

			throw new BackendMessageHandlerError("Les mots de passes ne correspondent pas.");
			
		}else {
			
			UserManager.setUserPassword(modelStore, message.getNewPasswordA(), message.getUser());
		}
		
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, UserChangesPassword message) {
		T.call(UserChangesPasswordHandler.class);
		
	}

}
