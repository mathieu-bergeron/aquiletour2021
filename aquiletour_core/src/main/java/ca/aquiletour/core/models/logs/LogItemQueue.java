package ca.aquiletour.core.models.logs;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.queue.models.Appointment;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;
import ca.ntro.services.Ntro;

public class LogItemQueue extends LogItem {
	
	private String closeTime = "";
	private boolean closedByTeacher = true;
	private List<AppointmentRevision> revisions = new ArrayList<>();
	
	public String getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}

	public void updateCloseTime(NtroDate timestamp) {
		this.closeTime = formatTimestamp(timestamp);
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
		
		updateCloseTime(timestamp);
		
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

		revision.updateTimestamp(timestamp);
		
		if(user.actsAsTeacher()) {
			
			revision.setModifiedByTeacher(true);

		}else {

			revision.setModifiedByTeacher(false);
		}

		revisions.add(revision);
	}

	@Override
	public void writeCsvLine(String separator, StringBuilder builder, int longuestTaskPath) {
		T.call(this);
		
		writeCsvLineBasicInfo(separator, builder);
		writeCsvLineCoursePath(separator, builder);
		writeCsvLineGroupId(separator, builder);
		writeCsvLineTaskPath(separator, builder, longuestTaskPath);
		
		builder.append(separator);
		builder.append(openTime());

		builder.append(separator);
		writeLastRevisionCsv(separator, builder);
		
		if(getRevisions().size() > 1) {
			builder.append(separator);
			writeAllRevisionsJson(separator, builder);
		}
	}

	private void writeAllRevisionsJson(String separator, StringBuilder builder) {
		T.call(this);
		
		builder.append(separator);
		builder.append("'");
		builder.append(Ntro.jsonService().toString(getRevisions()));
		builder.append("'");
	}

	private void writeLastRevisionCsv(String separator, StringBuilder builder) {
		T.call(this);
		
		if(!getRevisions().isEmpty()) {
			
			AppointmentRevision lastRevision = getRevisions().get(getRevisions().size() - 1);

			builder.append(separator);
			if(!lastRevision.getComment().isEmpty()) {
				builder.append("'");
				builder.append(lastRevision.getComment());
				builder.append("'");
			}

			builder.append(separator);
			if(!lastRevision.getTags().isEmpty()) {
				builder.append("'");
				builder.append(tagList(lastRevision.getTags()));
				builder.append("'");
			}
		}
	}
	
	private String tagList(List<String> tags) {
		T.call(this);

		StringBuilder builder = new StringBuilder();
		
		if(tags.size() > 0) {
			builder.append(tags.get(0));
		}
		
		for(int i = 1; i < tags.size(); i++) {
			builder.append(",");
			builder.append(tags.get(i));
		}

		return builder.toString();
	}

	private String openTime() {
		T.call(this);

		String openTime = null;
		
		if(!getRevisions().isEmpty()) {
			AppointmentRevision firstRevision = getRevisions().get(0);
			openTime = firstRevision.getTimestamp();
		}
		
		return openTime;
	}
}