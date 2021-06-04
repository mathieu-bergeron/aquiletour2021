package ca.aquiletour.server.http;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.messages.git.GetCommitsForPath;
import ca.aquiletour.server.registered_sockets.RegisteredSockets;
import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;
import ca.ntro.messages.ntro_messages.NtroGetModelMessage;
import ca.ntro.messages.ntro_messages.NtroSetModelMessage;
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

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if (request.getMethod().equals("POST")) {

			NtroMessage message = Ntro.jsonService().fromString(NtroMessage.class, readBody(baseRequest));
			
			if(message instanceof NtroGetModelMessage) {

				handleGetModelMessage(baseRequest, response, (NtroGetModelMessage) message);

			} else if(message instanceof NtroSetModelMessage) {

				handleSetModelMessage(baseRequest, response, (NtroSetModelMessage) message);

			} else if(message instanceof GetCommitsForPath) {

				handleGetCommitsForPath(baseRequest, response, (GetCommitsForPath) message);
				
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
	private void handleGetCommitsForPath(Request baseRequest, 
			                             HttpServletResponse response,
			                             GetCommitsForPath getCommitsForPathMessage) throws IOException {
		T.call(this);
		
		DocumentPath documentPath = getCommitsForPathMessage.getDocumentPath();
		String authToken = getCommitsForPathMessage.getAuthToken();

		ModelLoader modelLoader = Ntro.modelStore().getModelLoaderFromRequest(Constants.GIT_API_URL, getCommitsForPathMessage);
        modelLoader.execute();
        
        handleModelResponse(baseRequest, response, documentPath, authToken, modelLoader.getModel());
	}

	private void handleModelResponse(Request baseRequest, 
			                         HttpServletResponse response, 
			                         DocumentPath documentPath, 
			                         String authToken, 
			                         NtroModel model) throws IOException {

		T.call(this);

        // FIXME: user observation needs to be global (not specific to a single modelStore as there is one per thread!)
        // NOTE:  we do not need to connect that model to the store
        //        only models in the backend
        RegisteredSockets.registerModelObserver(authToken, documentPath);
        // ??
        // Ntro.backendService().registerThatUserObservesModel(user, documentPath);

        response.setStatus(HttpStatus.OK_200);

        response.getOutputStream().write(Ntro.jsonService().toString(model).getBytes());
        response.flushBuffer();

        baseRequest.setHandled(true);
	}

	@SuppressWarnings("unchecked")
	private void handleGetModelMessage(Request baseRequest, 
			                           HttpServletResponse response,
			                           NtroGetModelMessage getModelNtroMessage) throws IOException {
		T.call(this);

		DocumentPath documentPath = getModelNtroMessage.getDocumentPath();
		NtroUser user = getModelNtroMessage.getUser();

		Class<? extends NtroModel> modelClass = (Class<? extends NtroModel>) Ntro.serializableClass(documentPath.getCollection());
		
        ModelLoader modelLoader = Ntro.modelStore().getLoader(modelClass, getModelNtroMessage.getAuthToken(), documentPath.getDocumentId());
        modelLoader.execute();
        
        NtroModel model = modelLoader.getModel();
        
        handleModelResponse(baseRequest, response, documentPath, user.getAuthToken(), model);
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
			NtroSetModelMessage setModelNtroMessage) throws IOException {

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
