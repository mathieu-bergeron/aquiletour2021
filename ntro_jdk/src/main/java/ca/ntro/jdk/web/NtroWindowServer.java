package ca.ntro.jdk.web;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.NtroWindowWeb;
import ca.ntro.web.dom.HtmlDocument;
import ca.ntro.jdk.dom.HtmlDocumentJdk;

public class NtroWindowServer extends NtroWindowWeb {
	
	private HtmlDocumentJdk document;
	
	public NtroWindowServer(String indexHtmlPath) {
		super();
		T.call(this);
		
        InputStream stream = NtroWindowServer.class.getResourceAsStream(indexHtmlPath);

        MustNot.beNull(stream);

        try {

            Document jsoupDocument = Jsoup.parse(stream, StandardCharsets.UTF_8.name(), indexHtmlPath);
            document = new HtmlDocumentJdk(jsoupDocument);

        } catch (IOException e) {

            System.out.println("FATAL: cannot load " + indexHtmlPath);

        }

        MustNot.beNull(document);
	}

	@Override
	protected HtmlDocument getDocument() {
		T.call(this);

        return document;
	}

}
