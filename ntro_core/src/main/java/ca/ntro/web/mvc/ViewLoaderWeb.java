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

import ca.ntro.core.mvc.NtroView;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.services.ResourceLoaderTask;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.core.system.assertions.MustNot;

public abstract class ViewLoaderWeb extends ViewLoader {

	private String html;
	private String htmlUrl;
	private Class<? extends NtroViewWeb> viewClass;

	public ViewLoaderWeb() {
		super();
		T.call(this);
	}
	
	/*
	@Override
	protected void onSomeSubTaskFinished(String taskId, NtroTask subTask) {
		T.call(this);

		if(taskId.contains("Html")) {
			html = ((ResourceLoaderTask) subTask).getResourceAsString();
		}
	}*/

	@Override
	protected void runTaskAsync() {
		T.call(this);

		// FIXME: explicit casting as otherwise we get type errors in JSweet
		//        can we fix this??
		html = ((ResourceLoaderTask) getSubTask(ResourceLoaderTask.class, "Html")).getResourceAsString();

		MustNot.beNull(html);
		
		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {

	}

	public ViewLoaderWeb setHtmlUrl(String htmlPath) {
		T.call(this);
		
		this.htmlUrl = htmlPath;

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

	public String getHtmlUrl() {
		T.call(this);

		return htmlUrl;
	}

	public ViewLoader setTargetClass(Class<? extends NtroViewWeb> viewClass) {
		T.call(this);

		this.viewClass = viewClass;

		return this;
	}
	
	public Class<? extends NtroViewWeb> getTargetClass(){
		T.call(this);
		
		return viewClass;
	}

	@Override
	protected NtroView createViewImpl() {
		T.call(this);

		MustNot.beNull(html);

		NtroViewWeb view = Ntro.factory().newInstance(viewClass);

		HtmlElement rootElement = parseHtml(html);

		view.setRootElement(rootElement);

		return view;
	}

	protected abstract HtmlElement parseHtml(String html);

}
