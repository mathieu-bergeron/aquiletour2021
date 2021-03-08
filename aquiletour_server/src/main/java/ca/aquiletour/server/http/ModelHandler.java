package ca.aquiletour.server.http;

import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.queues.QueuesModel;
import ca.aquiletour.core.pages.users.UsersModel;
import ca.ntro.core.Ntro;
import ca.ntro.core.json.JsonParser;
import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.services.stores.LocalStore;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
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
        String[] uriParts = uriParts(request.getRequestURI());
        String collectionName = uriParts[1];
        String fileName = uriParts[2];

        System.out.println("Collection: " + collectionName + ", File: " + fileName);

        Class<? extends NtroModel> modelClazz = Ntro.introspector().getSerializableClass(collectionName);

        if (modelClazz == null) {
            Log.fatalError("[ModelHandler] Could not find NtroModel subclass for collection name '" + collectionName + "'");
            MustNot.beNull(modelClazz);
        }

        if (request.getMethod().equals("GET")) {
            handleModelFetch(baseRequest, response, collectionName, fileName, modelClazz);
        } else if (request.getMethod().equals("POST")) {
            handleModelWrite(baseRequest, response, request);
        } else {
            Log.error("[ModelHandler] Invalid HTTP method '" + request.getMethod() + "'!");
            response.setStatus(HttpStatus.METHOD_NOT_ALLOWED_405);

            baseRequest.setHandled(true);
        }
    }

    private void handleModelFetch(Request baseRequest, HttpServletResponse response, String collectionName, String fileName, Class<? extends NtroModel> modelClazz) throws IOException {
        ModelLoader modelLoader = LocalStore.getLoader(modelClazz, null, collectionName, fileName);
        modelLoader.execute();

        response.getWriter().println(JsonParser.toString(modelLoader.getModel().toJsonObject()));
        response.flushBuffer();

        baseRequest.setHandled(true);
    }

    // TODO Faire que ça sauvegarde vraiment le modèle ! Seulement pour tester
    // TODO instancier + save ???
    private void handleModelWrite(Request baseRequest, HttpServletResponse response, HttpServletRequest request) throws IOException {
        BufferedReader bodyText = request.getReader();

        StringBuilder jsonText = new StringBuilder();
        String ln;
        while ((ln = bodyText.readLine()) != null) {
            jsonText.append(ln);
        }

        System.out.println(jsonText);
        response.setStatus(HttpStatus.IM_A_TEAPOT_418);
        baseRequest.setHandled(true);
    }


    @Override
    public String dumpSelf() {
        return null;
    }
}
