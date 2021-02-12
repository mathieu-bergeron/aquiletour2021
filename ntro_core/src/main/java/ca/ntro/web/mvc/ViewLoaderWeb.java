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

package ca.ntro.web.mvc;

import ca.ntro.core.Ntro;
import ca.ntro.core.introspection.Factory;
import ca.ntro.core.mvc.NtroView;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.services.ResourceLoaderTask;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.core.system.assertions.MustNot;

public abstract class ViewLoaderWeb extends ViewLoader {

	private String html;
	private Class<? extends NtroViewWeb> viewClass;

	public ViewLoaderWeb() {
		super();
	}

	@Override
	protected void initializeTask() {

	}

	@Override
	protected void runTask() {
		T.call(this);

		// FIXME: explicit casting as otherwise we get type errors in JSweet
		//        can we fix this??
		html = ((ResourceLoaderTask) getSubTask(ResourceLoaderTask.class, "Html")).getResourceAsString();

		MustNot.beNull(html);
	}

	@Override
	protected void onFailure(Exception e) {

	}

	public ViewLoaderWeb setHtmlUrl(String htmlPath) {
		T.call(this);

		System.out.println("htmlPath: " + htmlPath);

		ResourceLoaderTask htmlLoader = Ntro.resourceLoader().loadResourceTask(htmlPath);
		htmlLoader.setTaskId("Html");

		addSubTask(htmlLoader);

		return this;
	}

	public ViewLoaderWeb setCssUrl(String string) {
		// TODO Auto-generated method stub

		return this;
	}

	public ViewLoaderWeb setTranslationsUrl(String string) {
		// TODO Auto-generated method stub

		return this;
	}

	public String getHtml() {
		T.call(this);

		return html;
	}

	public ViewLoader setTargetClass(Class<? extends NtroViewWeb> viewClass) {
		T.call(this);

		this.viewClass = viewClass;

		return this;
	}

	@Override
	protected NtroView createViewImpl() {
		T.call(this);

		MustNot.beNull(html);

		NtroViewWeb view = Factory.newInstance(viewClass);


		HtmlElement rootElement = parseHtml(html);
		System.out.println(rootElement);

		view.setRootElement(rootElement);

		return view;
	}

	protected abstract HtmlElement parseHtml(String html);

}
