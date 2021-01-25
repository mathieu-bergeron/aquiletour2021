package ca.aquiletour.server;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.web.NtroWindowWeb;
import ca.ntro.core.web.dom.HtmlDocument;
import ca.ntro.jdk.dom.HtmlDocumentJava;

public class NtroWindowServer extends NtroWindowWeb {

	@Override
	protected HtmlDocument getDocument() {
		T.call(this);
		
		String htmlPath = "/private/index.html";

        InputStream stream = NtroWindowServer.class.getResourceAsStream(htmlPath);

        MustNot.beNull(stream);

        HtmlDocument htmlDocument = null;

        try {

            Document jsoupDocument = Jsoup.parse(stream, StandardCharsets.UTF_8.name(), htmlPath);
            htmlDocument = new HtmlDocumentJava(jsoupDocument);

        } catch (IOException e) {

            System.out.println("FATAL: cannot load " + htmlPath);

        }

        MustNot.beNull(htmlDocument);

        return htmlDocument;
	}
	

}
