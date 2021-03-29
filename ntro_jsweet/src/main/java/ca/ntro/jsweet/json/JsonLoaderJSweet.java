package ca.ntro.jsweet.json;

import ca.ntro.core.json.JsonLoader;

import ca.ntro.core.Constants;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.ntro_messages.GetModelNtroMessage;
import ca.ntro.services.Ntro;
import ca.ntro.stores.DocumentPath;
import def.es6.Globals;
import def.js.Promise;

import static def.es6.Globals.fetch;

public class JsonLoaderJSweet extends JsonLoader {

    private       String       jsonString;
    private final DocumentPath documentPath;

    public JsonLoaderJSweet(DocumentPath documentPath) {
        super();
        T.call(this);

        this.documentPath = documentPath;
    }

    @Override
    protected void runTaskAsync() {
        T.call(this);
        
        GetModelNtroMessage message = Ntro.messages().create(GetModelNtroMessage.class);
        message.setDocumentPath(documentPath);
        
        String messageText = Ntro.jsonService().toString(message);

		def.js.Object options = new def.js.Object();
		options.$set("method","POST");
		options.$set("body",messageText);

        fetch(Constants.MODELS_URL_PREFIX + "/", options).then((Globals.FetchResponse response) -> {
            if (response.ok) {
                return response.text()
                        .then(text -> {
                            this.jsonString = text;

                            notifyTaskFinished();
                        })
                        .Catch((java.lang.Object error) -> {
                            System.err.println("[NetworkStore] for " + documentPath.toString() + ", " + error);
                        	System.err.println(error);
                        });
            } else {
                return Promise.reject("[NetworkStore] return code != 200");
            }
        });
    }

    @Override
    protected void onFailure(Exception e) {
        // TODO Auto-generated method stub

    }

    private String fullId(DocumentPath documentPath) {
        return documentPath.getCollection() + "/" + documentPath.getDocumentId();
    }

    @Override
    public String getJsonString() {
        T.call(this);

        return jsonString;
    }

    @Override
    public DocumentPath getDocumentPath() {
        T.call(this);

        return documentPath;
    }


}
