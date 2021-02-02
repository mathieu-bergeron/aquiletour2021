package ca.aquiletour.web.pages.dashboard;

import java.util.Map;

import ca.aquiletour.core.pages.dashboard.DashboardController;
import ca.aquiletour.core.pages.dashboard.messages.AddCourseMessage;
import ca.aquiletour.core.pages.dashboard.values.CourseSummary;
import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.Ntro;
import ca.ntro.core.Path;
import ca.ntro.core.mvc.view.ViewLoader;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.MessageFactory;
import ca.ntro.web.RequestHandlerTask;

public abstract class DashboardControllerWeb extends DashboardController implements RequestHandlerTask {
}
