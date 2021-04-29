package ca.ntro.services;

import ca.ntro.models.NtroDate;
import ca.ntro.models.NtroTimeOfDay;

public abstract class CalendarService {

	public abstract NtroDate fromString(String dateString, String dateFormat);
	public abstract String format(long epochSeconds, String dateFormat);
	public abstract void setTimeOfDay(NtroDate ntroDate, NtroTimeOfDay time);
	public abstract NtroDate now();

}
