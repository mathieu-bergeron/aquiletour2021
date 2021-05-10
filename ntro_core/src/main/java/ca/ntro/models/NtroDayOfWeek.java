package ca.ntro.models;

import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;

public class NtroDayOfWeek implements NtroModelValue {
	
	// JSWeet: enum does not have class name, hence not a good NtroModelValue
	public static final int MONDAY     = 0;
	public static final int TUESDAY    = 1;
	public static final int WEDNESDAY  = 2;
	public static final int THURSDAY   = 3;
	public static final int FRIDAY     = 4;
	public static final int SATURDAY   = 5;
	public static final int SUNDAY     = 6;
	
	private int dayOfWeek = 0;
	
	public NtroDayOfWeek() {
		T.call(this);
	}

	public NtroDayOfWeek(int dayOfWeek) {
		T.call(this);

		this.dayOfWeek = dayOfWeek;
	}

	public int getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	
	public static NtroDayOfWeek fromString(String dayName) {
		NtroDayOfWeek semesterDay = new NtroDayOfWeek();
		
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
			
			Log.warning("[NtroDayOfWeek] could not determine dayOfWeek for " + dayName);
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
	public String toString() {
		T.call(this);
		
		String name = "";
		
		switch(dayOfWeek) {
			case MONDAY:
				name = "lundi";
				break;

			case TUESDAY:
				name = "mardi";
				break;

			case WEDNESDAY:
				name = "mecredi";
				break;

			case THURSDAY:
				name = "jeudi";
				break;

			case FRIDAY:
				name = "vendredi";
				break;

			case SATURDAY:
				name = "samedi";
				break;

			case SUNDAY:
				name = "dimanche";
				break;
				
			default:
				Log.warning("Unknown dayOfWeek: " + dayOfWeek);
		}
		
		return name;
	}
	
	@Override
	public int hashCode() {
		return dayOfWeek;
	}

	@Override
	public boolean equals(Object other) {
		if(other == null) return false;
		if(other == this) return true;
		if(other instanceof NtroDayOfWeek) {
			NtroDayOfWeek otherDay = (NtroDayOfWeek) other;
			return otherDay.dayOfWeek == dayOfWeek;
		}
		return false;
	}
}
