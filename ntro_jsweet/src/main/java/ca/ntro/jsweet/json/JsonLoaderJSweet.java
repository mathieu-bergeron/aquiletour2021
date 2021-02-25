package ca.ntro.jsweet.json;

import ca.ntro.core.json.JsonLoader;
import ca.ntro.core.json.JsonObject;
import ca.ntro.core.json.JsonParser;
import ca.ntro.core.services.stores.DocumentPath;
import ca.ntro.core.system.trace.T;
import def.es6.Globals;
import def.js.JSON;
import def.js.Promise;

import static def.es6.Globals.fetch;

public class JsonLoaderJSweet extends JsonLoader {

    private       JsonObject   jsonObject;
    private final DocumentPath documentPath;

    public JsonLoaderJSweet(DocumentPath documentPath) {
        super();
        T.call(this);

        this.documentPath = documentPath;
    }

    @Override
    protected void runTaskAsync() {
        T.call(this);

        fetch("/_B/" + fullId(documentPath)).then((Globals.FetchResponse response) -> {
            if (response.ok) {
                return response.json()
                        .then(json -> {
                            // J'aime pas cette ligne! Comment passer d'objet JS en JsonObject?
                            this.jsonObject = JsonParser.fromString(JSON.stringify(json));

                            notifyTaskFinished();
                        })
                        .Catch((java.lang.Object error) -> {
                            System.err.println("[NetworkStore] Erreur lors du chargement du modèle (JSON invalide)");
                        });
            } else {
                return Promise.reject("[NetworkStore] Erreur lors du chargement du modèle (code non-200 du serveur)");
            }
        });
    }

    @Override
    protected void onFailure(Exception e) {
        // TODO Auto-generated method stub

    }

    private String fullId(DocumentPath documentPath) {
        return documentPath.getCollection() + "/" + documentPath.getId();
    }

    @Override
    public JsonObject getJsonObject() {
        T.call(this);

        return jsonObject;
    }

    @Override
    public DocumentPath getDocumentPath() {
        T.call(this);

        return documentPath;
    }


}
