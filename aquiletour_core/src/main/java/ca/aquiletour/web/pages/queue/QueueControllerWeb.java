package ca.aquiletour.web.pages.queue;

import java.util.Map;

import ca.aquiletour.core.pages.queue.QueueController;
import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.Ntro;
import ca.ntro.core.mvc.view.ViewLoader;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.MessageFactory;
import ca.ntro.web.Path;
import ca.ntro.web.RequestHandlerTask;

public abstract class QueueControllerWeb extends QueueController implements RequestHandlerTask {

	//private AddCourseMessage addCourseMessage = MessageFactory.getOutgoingMessage(AddCourseMessage.class);

	public QueueControllerWeb(RootController parentController) {
		super(parentController);
	}

	@Override
	protected ViewLoader createViewLoader(String lang) {
		T.call(this);

		return Ntro.viewLoaderWeb()
		           .setHtmlUrl("/views/queue/structure.html")
		           .setCssUrl("/views/queue/style.css")
		           .setTranslationsUrl("/i18/"+lang+"/strings.json")
		           .setTargetClass(QueueViewWeb.class);
	}

//	@Override
//	protected  ViewLoader createAppointmentViewLoader(String lang) {
//		T.call(this);
//
//		return Ntro.viewLoaderWeb()
//		           .setHtmlUrl("/views/course_summary/structure.html")
//		           .setCssUrl("/views/course_summary/style.css")
//		           .setTranslationsUrl("/i18/"+lang+"/strings.json")
//		           .setTargetClass(AppointmentViewWeb.class);
//	}

	@Override
	public void initialRequest(Path path, 
			                   Map<String, String[]> parameters, 
			                   String authToken) {
		T.call(this);
		
//		if(parameters.containsKey("date")) {
//			
//
//			String date = parameters.get("date")[0];
//			
//			addCourseMessage.setCourse(new CourseSummary(title, summary, date));
//			
//			addNextTask(addCourseMessage);
//		}
		
	}

	@Override
	public void newRequest(Path oldPath, 
			               Path path, 
			               Map<String, String[]> oldParameters,
			               Map<String, String[]> parameters, 
			               String authToken) {
		T.call(this);
		
	}

}
