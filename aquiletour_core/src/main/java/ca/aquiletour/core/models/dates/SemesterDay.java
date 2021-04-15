package ca.aquiletour.core.models.dates;

import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;

public class SemesterDay implements NtroModelValue {
	
	// JSWeet: enum does not have a class name
	public static final int MONDAY     = 0;
	public static final int TUESDAY    = 1;
	public static final int WEDNESDAY  = 2;
	public static final int THURSDAY   = 3;
	public static final int FRIDAY     = 4;
	public static final int SATURDAY   = 5;
	public static final int SUNDAY     = 6;
	
	private int dayOfWeek = 0;
	
	public SemesterDay() {
		
	}

	public SemesterDay(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public int getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	
	public static SemesterDay fromString(String dayName) {
		SemesterDay semesterDay = new SemesterDay();
		
		if(dayName.equalsIgnoreCase("lundi") || dayName.equalsIgnoreCase("lu")) {

			semesterDay.setDayOfWeek(MONDAY);

		} else if(dayName.equalsIgnoreCase("mardi") || dayName.equalsIgnoreCase("ma")) {

			semesterDay.setDayOfWeek(TUESDAY);

		} else if(dayName.equalsIgnoreCase("mercredi") || dayName.equalsIgnoreCase("me")) {

			semesterDay.setDayOfWeek(WEDNESDAY);

		} else if(dayName.equalsIgnoreCase("jeudi") || dayName.equalsIgnoreCase("je")) {

			semesterDay.setDayOfWeek(THURSDAY);

		} else if(dayName.equalsIgnoreCase("vendredi") || dayName.equalsIgnoreCase("ve")) {

			semesterDay.setDayOfWeek(FRIDAY);

		} else if(dayName.equalsIgnoreCase("samedi") || dayName.equalsIgnoreCase("sa")) {

			semesterDay.setDayOfWeek(SATURDAY);

		} else if(dayName.equalsIgnoreCase("dimanche") || dayName.equalsIgnoreCase("di")) {

			semesterDay.setDayOfWeek(SUNDAY);

		}else {
			
			Log.warning("[SemesterDay] could not determine dayOfWeek for " + dayName);
		}
		
		
		return semesterDay;
	}

	public String shortName() {
		T.call(this);
		
		String shortName = "";
		
		switch(dayOfWeek) {
			case MONDAY:
				shortName = "LU";
				break;

			case TUESDAY:
				shortName = "MA";
				break;

			case WEDNESDAY:
				shortName = "ME";
				break;

			case THURSDAY:
				shortName = "JE";
				break;

			case FRIDAY:
				shortName = "VE";
				break;

			case SATURDAY:
				shortName = "SA";
				break;

			case SUNDAY:
				shortName = "DI";
				break;
				
			default:
				Log.warning("Unknown dayOfWeek: " + dayOfWeek);
		}
		
		return shortName;
	}
	
	@Override
	public int hashCode() {
		return dayOfWeek;
	}

	@Override
	public boolean equals(Object other) {
		if(other == null) return false;
		if(other == this) return true;
		if(other instanceof SemesterDay) {
			SemesterDay otherDay = (SemesterDay) other;
			return otherDay.dayOfWeek == dayOfWeek;
		}
		return false;
	}
	
	
}
