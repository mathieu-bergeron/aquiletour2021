package ca.aquiletour.core.models.logs;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.queue.models.Appointment;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;

public class LogItemQueue extends LogItem {
	
	private NtroDate closeTime = new NtroDate();
	private boolean closedByTeacher = true;
	private List<AppointmentRevision> revisions = new ArrayList<>();
	
	public NtroDate getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(NtroDate closeTime) {
		this.closeTime = closeTime;
	}

	public boolean isClosedByTeacher() {
		return closedByTeacher;
	}

	public void setClosedByTeacher(boolean closedByTeacher) {
		this.closedByTeacher = closedByTeacher;
	}

	public List<AppointmentRevision> getRevisions() {
		return revisions;
	}

	public void setRevisions(List<AppointmentRevision> revisions) {
		this.revisions = revisions;
	}

	public void closeAppointment(NtroDate timestamp, 
								 User user) {
		T.call(this);
		
		setCloseTime(timestamp);
		
		if(user.actsAsTeacher()) {
			
			setClosedByTeacher(true);

		}else {

			setClosedByTeacher(false);
		}
	}

	public void updateAppointementInfo(NtroDate timestamp, 
									   User user,
			                           Appointment appointment) {
		T.call(this);

		AppointmentRevision revision = AppointmentRevision.fromAppointment(appointment);

		revision.setTimestamp(timestamp);
		
		if(user.actsAsTeacher()) {
			
			revision.setModifiedByTeacher(true);

		}else {

			revision.setModifiedByTeacher(false);
		}

		revisions.add(revision);
	}

	@Override
	public void writeCsvLine(String separator, StringBuilder builder) {
		T.call(this);

		writeCsvLineBasicInfo(separator, builder);
		writeCsvLineCoursePath(separator, builder);
		writeCsvLineGroupId(separator, builder);
		writeCsvLineTaskPath(separator, builder);
	}
}
