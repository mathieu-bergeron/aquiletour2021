package ca.ntro.services;

import ca.ntro.models.NtroDate;

public abstract class CalendarService {

	public abstract NtroDate fromString(String dateString, String dateFormat);
	public abstract String format(long epochSeconds, String dateFormat);

}
