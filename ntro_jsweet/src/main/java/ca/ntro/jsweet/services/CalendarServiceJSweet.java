package ca.ntro.jsweet.services;

import ca.ntro.models.NtroDate;
import ca.ntro.models.NtroTimeOfDay;
import ca.ntro.services.CalendarService;
import def.js.Date;

public class CalendarServiceJSweet extends CalendarService {

	@Override
	public NtroDate fromString(String dateString, String dateFormat) {
		return new NtroDate();
	}

	@Override
	public String format(long epochSeconds, String dateFormat) {
		Date date = new Date();
		date.setTime(epochSeconds * 1000);
		
		return date.toLocaleTimeString().toString();
	}

	@Override
	public void setTimeOfDay(NtroDate ntroDate, NtroTimeOfDay time) {
		// TODO
	}

	@Override
	public NtroDate now() {
		return new NtroDate();
	}

}
