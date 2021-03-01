// Copyright (C) (2020) (Mathieu Bergeron) (mathieu.bergeron@cmontmorency.qc.ca)
//
// This file is part of tutoriels4f5
//
// tutoriels4f5 is free software: you can redistribute it and/or modify
// it under the terms of the GNU Affero General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// tutoriels4f5 is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Affero General Public License for more details.
//
// You should have received a copy of the GNU Affero General Public License
// along with aquiletour.  If not, see <https://www.gnu.org/licenses/>

package ca.ntro.server.http;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;

import ca.ntro.core.system.trace.T;
import ca.ntro.jdk.FileLoader;
import ca.ntro.jdk.FileLoaderDev;

public class DynamicHandler extends AbstractHandler {

	private static int BUFFER_SIZE = 1024;

	public static ContextHandler createDynamicHandler(String urlPrefix, String privateFilesPrefix) {
		T.call(DynamicHandler.class);

        // Http handler
        ContextHandler dynamicContext = new ContextHandler();
        dynamicContext.setContextPath(urlPrefix);
        
        // XXX: dev-only, disable all caching
        dynamicContext.setInitParameter("cacheControl", "no-store,no-cache,must-revalidate,max-age=0,public");
        dynamicContext.setInitParameter("maxCacheSize", "0");
        
        dynamicContext.setHandler(new DynamicHandler(urlPrefix, privateFilesPrefix, new FileLoaderDev()));
        
        // TODO: dev-only: load resources from ./src/main/ressources NOT ./build/resources/...
        //staticFilesContext.setResourceBase("./src/main/resources");
        
        return dynamicContext;
	}
	
	private String resourcesUrlPrefix;
	private String privateFilesPrefix;
	private FileLoader fileLoader;

	public DynamicHandler(String resourcesUrlPrefix, 
			String publicFilesPrefix, 
			FileLoader fileLoader) {

		T.call(this);

		this.resourcesUrlPrefix = resourcesUrlPrefix;
		this.privateFilesPrefix = publicFilesPrefix;
		this.fileLoader = fileLoader;
	}
	
	@Override
	public void handle(String target, 
			           Request baseRequest, 
			           HttpServletRequest request, 
			           HttpServletResponse response)
			    
			    throws IOException, ServletException {
		
		T.call(this);
		

		OutputStream out = response.getOutputStream();
		serveView(baseRequest, response, out);
	}

	private void serveView(Request baseRequest, HttpServletResponse response, OutputStream out)
			throws FileNotFoundException, IOException {

		T.call(this);
		
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");

		System.err.println("");
		System.err.println("");
		System.err.println("");
		System.err.println("");
		
		serveStaticFile(baseRequest, response, out, "/private/index.html");
		
		// nothing for now
		response.setContentType("text/html; charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		baseRequest.setHandled(true);
	}

	private void serveStaticFile(Request baseRequest, HttpServletResponse response, OutputStream out, String filePath)
			throws FileNotFoundException, IOException {

		T.call(this);
		
		setContentType(response, filePath);

		response.setStatus(HttpServletResponse.SC_OK);
		
		InputStream fileStream = fileLoader.loadFile(filePath);
		
		copyFileToOutStream(out, fileStream);
		
		out.close();
		
		baseRequest.setHandled(true);
	}

	private void setContentType(HttpServletResponse response, String filePath) {
		T.call(this);

		if(filePath.endsWith("html")) {
			response.setContentType("text/html; charset=utf-8");
		}else if(filePath.endsWith("js")) {
			response.setContentType("application/javascript; charset=utf-8");
		}else if(filePath.endsWith("map")) {
			response.setContentType("application/json; charset=utf-8");
		}else if(filePath.endsWith("css")) {
			response.setContentType("text/css; charset=utf-8");
		}else if(filePath.endsWith("png")) {
			response.setContentType("image/png");
		}else if(filePath.endsWith("gif")) {
			response.setContentType("image/gif");
		}else if(filePath.endsWith("jpg")) {
			response.setContentType("image/jpeg");
		}else {
			response.setContentType("text/plain; charset=utf-8");
		}
	}


	private void copyFileToOutStream(OutputStream out, InputStream fileStream) throws IOException {
		T.call(this);
		
        byte[] buffer = new byte[BUFFER_SIZE];

        int readSize = 0;

        while(true){

            readSize = fileStream.read(buffer);
            if(readSize > 0) {
				out.write(buffer, 0, readSize);
            }else {
            	break;
            }
        } 
	}


}