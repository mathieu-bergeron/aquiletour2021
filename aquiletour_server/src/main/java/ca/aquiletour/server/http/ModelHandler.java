package ca.aquiletour.server.http;

import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.queues.QueuesModel;
import ca.aquiletour.core.pages.users.UsersModel;
import ca.ntro.core.Ntro;
import ca.ntro.core.NtroUser;
import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.services.stores.DocumentPath;
import ca.ntro.core.services.stores.LocalStore;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;
import ca.ntro.messages.ntro_messages.GetModelNtroMessage;
import ca.ntro.messages.ntro_messages.SetModelNtroMessage;

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
    public static ContextHandler createModelHandler(String modelsUrlPrefix, String publicFilesPrefix) {
        T.call(ModelHandler.class);

        // Http handler
        ContextHandler dynamicContext = new ContextHandler();
        dynamicContext.setContextPath(modelsUrlPrefix);

        // XXX: dev-only, disable all caching
        dynamicContext.setInitParameter("cacheControl", "no-store,no-cache,must-revalidate,max-age=0,public");
        dynamicContext.setInitParameter("maxCacheSize", "0");

        dynamicContext.setHandler(new ModelHandler(modelsUrlPrefix, publicFilesPrefix));

        return dynamicContext;
    }


    private String modelsUrlPrefix;
    private String publicFilesPrefix;

    public ModelHandler(String modelsUrlPrefix,
                        String publicFilesPrefix) {

        T.call(this);

        Ntro.introspector().registerSerializableClass(UsersModel.class);
        Ntro.introspector().registerSerializableClass(DashboardModel.class);
        Ntro.introspector().registerSerializableClass(QueuesModel.class);

        this.modelsUrlPrefix = modelsUrlPrefix;
        this.publicFilesPrefix = publicFilesPrefix;
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
    }

	private void handleGetModelMessage(Request baseRequest, HttpServletResponse response,
			GetModelNtroMessage getModelNtroMessage) throws IOException {

		DocumentPath documentPath = getModelNtroMessage.getDocumentPath();
		NtroUser user = getModelNtroMessage.getUser();

		Class<? extends NtroModel> modelClazz = (Class<? extends NtroModel>) Ntro.jsonService().serializableClass(documentPath.getCollection());
		
        ModelLoader modelLoader = LocalStore.getLoader(modelClazz, user.getAuthToken(), documentPath.getDocumentId());
        modelLoader.execute();
        
        NtroModel model = modelLoader.getModel();
        
        LocalStore.registerThatUserObservesModel(user, documentPath, model);

        response.getWriter().print(Ntro.jsonService().toString(modelLoader.getModel()));
        response.flushBuffer();

        baseRequest.setHandled(true);
	}

	private String readBody(Request baseRequest) throws IOException {
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
		NtroUser user = setModelNtroMessage.getUser();
		NtroModel model = setModelNtroMessage.getModel();

        LocalStore.saveJsonString(documentPath, Ntro.jsonService().toString(model));

        response.setStatus(HttpStatus.OK_200);
        baseRequest.setHandled(true);
	}

    @Override
    public String dumpSelf() {
        return null;
    }
}
