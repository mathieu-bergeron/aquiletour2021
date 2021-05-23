package ca.aquiletour.server.backend.users;

import ca.ntro.core.models.lambdas.Break;

public interface UserIdLambda {

	void execute(String id) throws Break;
}
