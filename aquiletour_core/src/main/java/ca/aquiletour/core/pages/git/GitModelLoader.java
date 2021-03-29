package ca.aquiletour.core.pages.git;

import ca.ntro.core.models.ModelLoader;
import ca.ntro.services.ModelStore;

public abstract class GitModelLoader extends ModelLoader {
	
	/*
	 *  TODO: deux versions de ça
	 *  
	 *  On fait la requête à /_git_api/
	 *  
	 *  1. sur le serveur: bloquant      (ça bloque la requête jusqu'à ce qu'on ait le modèle)
	 *  2. sur le client: non-bloquant
	 * 
	 */

	public GitModelLoader(ModelStore modelStore) {
		super(modelStore);
	}

}
