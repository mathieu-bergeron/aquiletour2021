package ca.ntro.jdk.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;
import ca.ntro.models.NtroTimeOfDay;
import ca.ntro.services.CalendarService;

public class CalendarServiceJdk extends CalendarService {

	@Override
	public NtroDate fromString(String dateString, String dateFormat) {
		T.call(this);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat, Locale.CANADA_FRENCH);
		LocalDateTime dateTime = null;
		try {
			
			dateTime = LocalDateTime.parse(dateString, formatter);
			
		}catch(DateTimeParseException e) {
			
			try {
				
				LocalDate date = LocalDate.parse(dateString, formatter);
				dateTime = LocalDateTime.of(date, LocalTime.of(0, 0));
				
			}catch(Exception e2) {
				
				Log.warning("Could not parse dateString: " + dateString + " with dateFormat: " + dateFormat);
				dateTime = LocalDateTime.now();
			}
		}
		
		return new NtroDate(dateTime.toEpochSecond(localZoneOffset()));
	}

	private ZoneOffset localZoneOffset() {
		T.call(this);

		return OffsetDateTime.now().getOffset();
	}

	@Override
	public String format(long epochSeconds, String dateFormat) {
		T.call(this);
		
		LocalDateTime dateTime = LocalDateTime.ofEpochSecond(epochSeconds, 0, localZoneOffset());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat, Locale.CANADA_FRENCH);

		return dateTime.format(formatter);
	}

	@Override
	public void setTimeOfDay(NtroDate ntroDate, NtroTimeOfDay time) {
		T.call(this);

		LocalDateTime dateTime = LocalDateTime.ofEpochSecond(ntroDate.getEpochSeconds(), 0, localZoneOffset());
		LocalDate date = dateTime.toLocalDate();
		LocalDateTime startOfDay = date.atStartOfDay();
		
		long epochDateSeconds = startOfDay.toEpochSecond(localZoneOffset());
		long epochDateTimeSeconds = epochDateSeconds + time.getHour24() * 60 * 60 + time.getMinutes() * 60;
		
		ntroDate.setEpochSeconds(epochDateTimeSeconds);
	}

	@Override
	public NtroDate now() {
		T.call(this);

		LocalDateTime dateTime = LocalDateTime.now(localZoneOffset());
		
		return new NtroDate(dateTime.toEpochSecond(localZoneOffset()));
	}

}
