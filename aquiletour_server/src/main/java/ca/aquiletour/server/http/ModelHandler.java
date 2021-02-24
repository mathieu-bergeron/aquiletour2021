package ca.aquiletour.server.http;

import ca.ntro.core.system.trace.T;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

        this.modelsUrlPrefix = modelsUrlPrefix;
        this.publicFilesPrefix = publicFilesPrefix;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.getWriter().println("{}");
        response.flushBuffer();
        baseRequest.setHandled(true);
    }

    @Override
    public String dumpSelf() {
        return null;
    }
}
