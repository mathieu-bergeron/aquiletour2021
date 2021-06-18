package ca.ntro.jdk.web;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.NtroWindowWeb;
import ca.ntro.web.dom.HtmlDocument;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.jdk.dom.HtmlDocumentJdk;

public class NtroWindowServer extends NtroWindowWeb implements Cloneable {
	
	private HtmlDocumentJdk document;

	public NtroWindowServer(HtmlDocumentJdk document) {
		super();
		T.call(this);
		
		this.document = document;
	}
	
	public NtroWindowServer(String indexHtmlPath) {
		super();
		T.call(this);
		
        loadDocument(indexHtmlPath);
	}

	private void loadDocument(String indexHtmlPath) {
		T.call(this);

		InputStream stream = NtroWindowServer.class.getResourceAsStream(indexHtmlPath);

        MustNot.beNull(stream);

        try {

            Document jsoupDocument = Jsoup.parse(stream, StandardCharsets.UTF_8.name(), indexHtmlPath);
            document = new HtmlDocumentJdk(jsoupDocument);

        } catch (IOException e) {

        	Log.fatalError("FATAL: cannot load " + indexHtmlPath, e);

        }

        MustNot.beNull(document);
	}

	@Override
	protected HtmlDocument getDocument() {
		T.call(this);

        return document;
	}

	@Override
	public NtroWindowServer clone() {
		return new NtroWindowServer(document.clone());
	}

	public void setUpLoadingScreen() {
		T.call(this);
		
		HtmlElement body = document.select("body").get(0);
		body.css("cursor", "wait");
		
		body.find("a").forEach(e -> {
			e.addClass("link-disabled");
		});
	}
}
