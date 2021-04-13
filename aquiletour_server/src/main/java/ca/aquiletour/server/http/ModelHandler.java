package ca.aquiletour.server.http;

import ca.aquiletour.server.RegisteredSockets;
import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;
import ca.ntro.messages.ntro_messages.GetModelNtroMessage;
import ca.ntro.messages.ntro_messages.SetModelNtroMessage;
import ca.ntro.services.Ntro;
import ca.ntro.stores.DocumentPath;
import ca.ntro.users.NtroUser;

import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class ModelHandler extends AbstractHandler {
    public static ContextHandler createModelHandler(String modelsUrlPrefix) {
        T.call(ModelHandler.class);

        // Http handler
        ContextHandler dynamicContext = new ContextHandler();
        dynamicContext.setContextPath(modelsUrlPrefix);

        // XXX: dev-only, disable all caching
        dynamicContext.setInitParameter("cacheControl", "no-store,no-cache,must-revalidate,max-age=0,public");
        dynamicContext.setInitParameter("maxCacheSize", "0");

        dynamicContext.setHandler(new ModelHandler(modelsUrlPrefix));

        return dynamicContext;
    }


    private String modelsUrlPrefix;

    public ModelHandler(String modelsUrlPrefix) {

        T.call(this);

        this.modelsUrlPrefix = modelsUrlPrefix;
    }

    private String[] uriParts(String uri) {
        String[] parts = uri.split("/");
        String[] ret = new String[parts.length - 1];

        System.arraycopy(parts, 1, ret, 0, parts.length - 1);

        return ret;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if (request.getMethod().equals("POST")) {

			NtroMessage message = Ntro.jsonService().fromString(NtroMessage.class, readBody(baseRequest));
			
			if(message instanceof GetModelNtroMessage) {

				handleGetModelMessage(baseRequest, response, (GetModelNtroMessage) message);

			} else if(message instanceof SetModelNtroMessage) {

				handleSetModelMessage(baseRequest, response, (SetModelNtroMessage) message);
				
			}else {

				Log.error("[ModelHandler] Unsupported message '" + Ntro.introspector().ntroClassFromObject(message).simpleName() + "'");
				response.setStatus(HttpStatus.NO_CONTENT_204);
				baseRequest.setHandled(true);
			}

        }else {

            Log.error("[ModelHandler] Invalid HTTP method '" + request.getMethod() + "'!");
            response.setStatus(HttpStatus.METHOD_NOT_ALLOWED_405);
            baseRequest.setHandled(true);
        }

        // XXX: prepare for next request
        Ntro.reset();
    }

	@SuppressWarnings("unchecked")
	private void handleGetModelMessage(Request baseRequest, HttpServletResponse response,
			GetModelNtroMessage getModelNtroMessage) throws IOException {

		DocumentPath documentPath = getModelNtroMessage.getDocumentPath();
		NtroUser user = getModelNtroMessage.getUser();

		Class<? extends NtroModel> modelClass = (Class<? extends NtroModel>) Ntro.serializableClass(documentPath.getCollection());
		
        ModelLoader modelLoader = Ntro.modelStore().getLoader(modelClass, getModelNtroMessage.getAuthToken(), documentPath.getDocumentId());
        modelLoader.execute();
        
        NtroModel model = modelLoader.getModel();
        
        // FIXME: user observation needs to be global (not specific to a single modelStore as there is one per thread!)
        // NOTE:  we do not need to connect that model to the store
        //        only models in the backend
        RegisteredSockets.registerModelObserver(user, documentPath);
        // ??
        // Ntro.backendService().registerThatUserObservesModel(user, documentPath);

        response.getOutputStream().write(Ntro.jsonService().toString(modelLoader.getModel()).getBytes());
        response.flushBuffer();

        baseRequest.setHandled(true);
	}

	static String readBody(Request baseRequest) throws IOException {
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = baseRequest.getReader();
		String line;
		while((line = reader.readLine()) != null) {
			builder.append(line);
		}
		return builder.toString();
	}

	private void handleSetModelMessage(Request baseRequest, HttpServletResponse response,
			SetModelNtroMessage setModelNtroMessage) throws IOException {

		DocumentPath documentPath = setModelNtroMessage.getDocumentPath();
		NtroModel model = setModelNtroMessage.getModel();

        Ntro.modelStore().saveDocument(documentPath, Ntro.jsonService().toString(model));

        response.setStatus(HttpStatus.OK_200);
        baseRequest.setHandled(true);
	}

    @Override
    public String dumpSelf() {
        return null;
    }
}
