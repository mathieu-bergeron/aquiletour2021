package ca.ntro.jsweet.json;

import ca.ntro.core.json.JsonLoader;

import ca.ntro.core.Constants;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;
import ca.ntro.messages.NtroModelMessage;
import ca.ntro.messages.ntro_messages.GetModelNtroMessage;
import ca.ntro.services.Ntro;
import ca.ntro.stores.DocumentPath;
import def.es6.Globals;
import def.js.Promise;

import static def.es6.Globals.fetch;

public class JsonLoaderJSweet extends JsonLoader {

    private String jsonString;
    private final String serviceUrl;
    private final NtroModelMessage request;

    public JsonLoaderJSweet(String serviceUrl, NtroModelMessage request) {
        super();
        T.call(this);

        this.serviceUrl = serviceUrl;
        this.request = request;
    }

    @Override
    protected void runTaskAsync() {
        T.call(this);
        
        String messageText = Ntro.jsonService().toString(request);

		def.js.Object options = new def.js.Object();
		options.$set("method","POST");
		options.$set("body",messageText);

        fetch(serviceUrl, options).then((Globals.FetchResponse response) -> {
            if (response.ok) {
                return response.text()
                        .then(text -> {
                            this.jsonString = text;

                            notifyTaskFinished();
                        })
                        .Catch((java.lang.Object error) -> {
                            System.err.println("[NetworkStore] for " + messageText + ", " + error);
                        	System.err.println(error);
                        });
            } else {
                return Promise.reject("[NetworkStore] return code != 200 for " + messageText);
            }
        });
    }

    @Override
    protected void onFailure(Exception e) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getJsonString() {
        T.call(this);

        return jsonString;
    }
}
