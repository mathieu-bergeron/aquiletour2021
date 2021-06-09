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

package ca.aquiletour.server.vertx;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;

import ca.ntro.core.system.trace.T;
import ca.ntro.jdk.FileLoader;
import ca.ntro.jdk.FileLoaderDev;
import ca.ntro.services.Ntro;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.buffer.impl.BufferImpl;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

public class ResourceHandlerVertx {

	
	private static int BUFFER_SIZE = 1024;

	public static ResourceHandlerVertx createResourceHandler(String resourcesUrlPrefix, String publicFilesPrefix) {
		T.call(ResourceHandlerVertx.class);
		
		return new ResourceHandlerVertx(resourcesUrlPrefix, publicFilesPrefix, new FileLoaderDev());
	}
	
	private String resourcesUrlPrefix;
	private String publicFilesPrefix;
	private FileLoader fileLoader;
	
	public ResourceHandlerVertx(String resourcesUrlPrefix, 
			String publicFilesPrefix, 
			FileLoader fileLoader) {

		T.call(this);

		this.resourcesUrlPrefix = resourcesUrlPrefix;
		this.publicFilesPrefix = publicFilesPrefix;
		this.fileLoader = fileLoader;
	}
	
	public void handle(RoutingContext routingContext) {
		
		T.call(this);
		
		HttpServerRequest request = routingContext.request();
		HttpServerResponse response = routingContext.response();

		String filePath = request.uri().toString();
		
		if(filePath.startsWith(resourcesUrlPrefix)) {
			
			filePath = removeResourcesPrefix(filePath);

			filePath = addPublicFilesPrefix(filePath);
			
			serveStaticFile(response, filePath);
			
		}else{

			serveError404(response);
		}
	}

	private void serveError404(HttpServerResponse response) {
		T.call(this);

		response.setStatusCode(HttpServletResponse.SC_NOT_FOUND);
		response.end();
	}

	private String removeResourcesPrefix(String filePath) {
		T.call(this);

		filePath = filePath.replaceFirst(resourcesUrlPrefix, "");
		return filePath;
	}

	private String addPublicFilesPrefix(String filePath) {
		T.call(this);

		filePath = publicFilesPrefix + "/" +  filePath;
		return filePath;
	}

	private void serveStaticFile(HttpServerResponse response, String filePath) {

		T.call(this);
		
		setContentType(response, filePath);

		response.setStatusCode(HttpServletResponse.SC_OK);
		
		InputStream fileStream = fileLoader.loadFile(filePath);
		
		if(isBinary(filePath)) {

			writeBinaryResponse(response, fileStream);
			
		}else {

			writeUtf8Response(response, fileStream);

		}
	}

	private boolean isBinary(String filePath) {
		T.call(this);
		
		boolean isBinary = false;

		if(filePath.endsWith("png")) {

			isBinary = true;

		}else if(filePath.endsWith("gif")) {

			isBinary = true;

		}else if(filePath.endsWith("jpg")) {

			isBinary = true;

		}
		
		return isBinary;
	}

	private void setContentType(HttpServerResponse response, String filePath) {
		T.call(this);

		if(filePath.endsWith("html")) {

			response.putHeader("content-type", "text/html; charset=utf-8");

		}else if(filePath.endsWith("js")) {

			response.putHeader("content-type", "application/javascript; charset=utf-8");

		}else if(filePath.endsWith("map")) {

			response.putHeader("content-type", "application/json; charset=utf-8");

		}else if(filePath.endsWith("css")) {

			response.putHeader("content-type", "text/css; charset=utf-8");

		}else if(filePath.endsWith("png")) {

			response.putHeader("content-type", "image/png");

		}else if(filePath.endsWith("gif")) {

			response.putHeader("content-type", "image/gif");

		}else if(filePath.endsWith("jpg")) {

			response.putHeader("content-type", "image/jpeg");

		}else {
			response.putHeader("content-type", "text/plain; charset=utf-8");
		}
	}


	private void writeBinaryResponse(HttpServerResponse response, InputStream fileStream) {
		T.call(this);
		
        byte[] buffer = new byte[BUFFER_SIZE];
        
        Buffer bufferVertx = new BufferImpl();

        int readSize = 0;
        
        while(true){

            try {

				readSize = fileStream.read(buffer);

			} catch (IOException e) { 
				e.printStackTrace();
				break;
			}
            if(readSize > 0) {
            	bufferVertx.appendBytes(buffer);
            }else {
            	break;
            }
        } 

        response.end(bufferVertx);
	}

	private void writeUtf8Response(HttpServerResponse response, InputStream fileStream) {
		T.call(this);
		
		Scanner scanner = new Scanner(fileStream);
		StringBuilder builder = new StringBuilder();
		while(scanner.hasNextLine()) {
			builder.append(scanner.nextLine());
			builder.append(Ntro.lineSeparator());
		}
		scanner.close();

		response.end(builder.toString(), "UTF-8");
	}

}

