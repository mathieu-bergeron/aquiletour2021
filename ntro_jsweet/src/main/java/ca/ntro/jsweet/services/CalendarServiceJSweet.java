package ca.ntro.jsweet.services;

import ca.ntro.models.NtroDate;
import ca.ntro.services.CalendarService;

public class CalendarServiceJSweet extends CalendarService {

	@Override
	public NtroDate fromString(String dateString, String dateFormat) {
		throw new RuntimeException("TODO");
	}

	@Override
	public String format(long epochSeconds, String dateFormat) {
		throw new RuntimeException("TODO");
	}

}
