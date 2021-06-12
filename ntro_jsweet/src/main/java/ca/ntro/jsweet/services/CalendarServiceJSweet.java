package ca.ntro.jsweet.services;

import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;
import ca.ntro.models.NtroTimeOfDay;
import ca.ntro.services.CalendarService;
import def.dom.intl.DateTimeFormatOptions;
import def.js.Date;

public class CalendarServiceJSweet extends CalendarService {

	@Override
	public NtroDate fromString(String dateString, String dateFormat) {
		return new NtroDate();
	}

	@Override
	public String format(long epochSeconds, String dateFormat) {
		T.call(this);

		Date date = new Date();
		date.setTime(epochSeconds * 1000);
		
		DateTimeFormatOptions options = new DateTimeFormatOptions() {};

		if(dateFormat.contains("y")) {
			options.year = "numeric";
		}
		
		if(dateFormat.contains("E")) {
			options.weekday = "short";
		}

		if(dateFormat.contains("dd")) {

			options.day = "2-digit";

		}else if(dateFormat.contains("d")) {

			options.day = "numeric";
		}

		if(dateFormat.contains("M")) {
			options.month = "short";
		}

		if(dateFormat.contains("H")) {
			options.hour12 = false;
			options.hour = "2-digit";
		}

		if(dateFormat.contains("m")) {
			options.minute = "2-digit";
		}

		if(dateFormat.contains("s")) {
			options.second = "2-digit";
		}
		
		return date.toLocaleString("fr-CA", options);
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
