package ca.ntro.models;

import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.trace.T;

public class NtroTimeOfDay implements NtroModelValue {
	
	private int hour24;
	private int minutes;

	public NtroTimeOfDay() {
		T.call(this);
	}

	public NtroTimeOfDay(int hour24, int minutes) {
		T.call(this);

		this.hour24 = hour24;
		this.minutes = minutes;
	}

	public int getHour24() {
		return hour24;
	}

	public void setHour24(int hour24) {
		this.hour24 = hour24;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public static NtroTimeOfDay fromString(String timeOfDayString) {
		String[] segments = timeOfDayString.split(":");
		
		int hour24 = Integer.parseInt(segments[0]);
		int minutes = Integer.parseInt(segments[1]);
		
		return new NtroTimeOfDay(hour24, minutes);
	}
	
	private String formatNumber(int number) {
		String result = String.valueOf(number);
		if(result.length() == 1) {
			result = "0" + result;
		}

		return result;
	}
	
	@Override
	public String toString() {
		T.call(this);

		return formatNumber(hour24) + ":" + formatNumber(minutes);
	}
	
	
}
