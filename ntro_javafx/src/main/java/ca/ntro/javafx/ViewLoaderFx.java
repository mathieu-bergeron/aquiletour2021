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

package ca.ntro.javafx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import ca.ntro.core.mvc.view.ViewLoader;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class ViewLoaderFx extends ViewLoader {
	
	private URL fxmlUrl;
	private URL cssUrl;
	private ResourceBundle strings;
	private Parent parent;
	private FXMLLoader loader;

	@Override
	protected void initializeTask() {
	}

	@Override
	protected void runTaskAsync() {
		T.call(this);
		notifyTaskFinished();
	}
	
	@Override
	protected void onFailure(Exception e) {
		T.call(this);
	}

	public ViewLoaderFx setFxmlUrl(String fxmlPath) {
		T.call(this);
		
		fxmlUrl = ViewLoaderFx.class.getResource(fxmlPath);
		
		return this;
	}

	public ViewLoaderFx setCssUrl(String cssPath) {
		T.call(this);

		cssUrl = ViewLoaderFx.class.getResource(cssPath);

		return this;
	}

	public ViewLoaderFx setTranslationsName(String translationsName) {
		T.call(this);
		
		strings = ResourceBundle.getBundle(translationsName);

		return this;
	}

	private void loadFxml() {
		T.call(this);
		
		loader = new FXMLLoader(fxmlUrl);

		if(strings != null) {
			loader.setResources(strings);
		}
		
		MustNot.beNull(loader);

		try {

			parent = loader.load();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(cssUrl !=  null) {
			addCss();
		}
	}

	private void addCss() {
		T.call(this);
		
		parent.getStylesheets().add(cssUrl.toExternalForm());
	}

	@Override
	public NtroViewFx createView() {
		T.call(this);

		loadFxml();
		
		NtroViewFx view = loader.getController();
		
		view.setParent(parent);
		
		return view;
	}
}
