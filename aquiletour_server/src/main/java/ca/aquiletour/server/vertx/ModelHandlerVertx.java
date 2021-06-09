package ca.aquiletour.server.vertx;

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
import io.netty.handler.codec.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class ModelHandlerVertx {

    public static void handle(RoutingContext routingContext) {
    	T.call(ModelHandlerVertx.class);
    	
    	HttpServerRequest request = routingContext.request();
    	HttpServerResponse response = routingContext.response();

        if (request.method().equals(HttpMethod.POST)) {

			NtroMessage message = Ntro.jsonService().fromString(NtroMessage.class, routingContext.getBodyAsString());
			
			if(message instanceof NtroGetModelMessage) {

				handleGetModelMessage(request, response, (NtroGetModelMessage) message);

			} else if(message instanceof NtroSetModelMessage) {

				handleSetModelMessage(request, response, (NtroSetModelMessage) message);

			} else if(message instanceof GetCommitsForPath) {

				handleGetCommitsForPath(request, response, (GetCommitsForPath) message);
				
			}else {

				Log.error("[ModelHandler] Unsupported message '" + Ntro.introspector().ntroClassFromObject(message).simpleName() + "'");
				response.setStatusCode(Response.SC_BAD_REQUEST);
				response.end();

			}

        }else {

            Log.error("[ModelHandler] Invalid HTTP method '" + request.method() + "'!");
            response.setStatusCode(HttpStatus.METHOD_NOT_ALLOWED_405);
            response.end();
        }

        // XXX: prepare for next request
        Ntro.reset();
    }


	@SuppressWarnings("unchecked")
	private static void handleGetCommitsForPath(HttpServerRequest baseRequest, 
			                             HttpServerResponse response,
			                             GetCommitsForPath getCommitsForPathMessage) {
		T.call(ModelHandlerVertx.class);
		
		DocumentPath documentPath = getCommitsForPathMessage.getDocumentPath();
		String authToken = getCommitsForPathMessage.getAuthToken();

		ModelLoader modelLoader = Ntro.modelStore().getLoaderFromRequest(Constants.GIT_API_URL, getCommitsForPathMessage);
        modelLoader.execute();
        
        handleModelResponse(baseRequest, response, documentPath, authToken, modelLoader.getModel());
	}

	private static void handleModelResponse(HttpServerRequest baseRequest, 
			                         HttpServerResponse response, 
			                         DocumentPath documentPath, 
			                         String authToken, 
			                         NtroModel model) {

		T.call(ModelHandlerVertx.class);

        // FIXME: user observation needs to be global (not specific to a single modelStore as there is one per thread!)
        // NOTE:  we do not need to connect that model to the store
        //        only models in the backend
        RegisteredSockets.registerModelObserver(authToken, documentPath);
        // ??
        // Ntro.backendService().registerThatUserObservesModel(user, documentPath);

        response.setStatusCode(HttpStatus.OK_200);

        response.end(Ntro.jsonService().toString(model));
        
	}

	@SuppressWarnings("unchecked")
	private static void handleGetModelMessage(HttpServerRequest baseRequest, 
			                           HttpServerResponse response,
			                           NtroGetModelMessage getModelNtroMessage) {

		T.call(ModelHandlerVertx.class);

		DocumentPath documentPath = getModelNtroMessage.getDocumentPath();
		NtroUser user = getModelNtroMessage.getUser();

		Class<? extends NtroModel> modelClass = (Class<? extends NtroModel>) Ntro.serializableClass(documentPath.getCollection());
		
        ModelLoader modelLoader = Ntro.modelStore().getLoader(modelClass, getModelNtroMessage.getAuthToken(), documentPath.getDocumentId());
        modelLoader.execute();
        
        NtroModel model = modelLoader.getModel();
        
        handleModelResponse(baseRequest, response, documentPath, user.getAuthToken(), model);
	}

	private static void handleSetModelMessage(HttpServerRequest baseRequest, 
			                           HttpServerResponse response,
			                           NtroSetModelMessage setModelNtroMessage) {

		T.call(ModelHandlerVertx.class);

		DocumentPath documentPath = setModelNtroMessage.getDocumentPath();
		NtroModel model = setModelNtroMessage.getModel();

        Ntro.modelStore().saveDocument(documentPath, Ntro.jsonService().toString(model));

        response.setStatusCode(Response.SC_OK);
	}
}
