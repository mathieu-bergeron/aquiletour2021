package ca.aquiletour.core.pages.course.handlers;

import ca.aquiletour.core.pages.course.messages.ShowTaskMessage;
import ca.aquiletour.core.pages.course.models.CourseModel;
import ca.ntro.core.mvc.ModelHandler;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class CourseModelHandler extends ModelHandler<CourseModel> {

	@Override
	protected void handle(CourseModel model) {
		T.call(this);
		
		// FIXME: we must wait for model to be loaded before firing this
		//        to fix this we would need a messageQueue in the task graph
		ShowTaskMessage showTaskMessage = Ntro.messages().create(ShowTaskMessage.class);
		Ntro.messages().send(showTaskMessage);
	}

}
