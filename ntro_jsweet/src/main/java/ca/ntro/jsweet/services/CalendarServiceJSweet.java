package ca.ntro.jsweet.services;

import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;
import ca.ntro.models.NtroTimeOfDay;
import ca.ntro.services.CalendarService;
import def.dom.intl.DateTimeFormatOptions;
import def.js.Date;
import def.js.Math;

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

		if(dateFormat.contains("MMM")) {

			options.month = "short";

		} else if(dateFormat.contains("MM")) {

			options.month = "2-digit";

		}else if(dateFormat.contains("M")) {

			options.month = "numeric";
		}

		if(dateFormat.contains("H")) {
			options.hour12 = false;
			options.hour = "2-digit";
		}

		if(dateFormat.contains("mm")) {

			options.minute = "2-digit";

		}else if(dateFormat.contains("m")) {

			options.minute = "numeric";
		}

		if(dateFormat.contains("ss")) {
			options.second = "2-digit";

		}else if(dateFormat.contains("s")) {
			options.second = "numeric";
		}
		
		String result = date.toLocaleString("fr-CA", options);
		
		result = result.replace(" h ", ":");

		result = result.replace(" min ", ":");

		result = result.replace(" s", "");
		
		return result;
	}

	@Override
	public void setTimeOfDay(NtroDate ntroDate, NtroTimeOfDay time) {
		// TODO
	}

	@Override
	public NtroDate now() {
		double epochSeconds = new Date().getTime() / 1000;
		long epoch = Long.parseLong(Double.toString(Math.floor(epochSeconds)));

		return new NtroDate(epoch);
	}

}
