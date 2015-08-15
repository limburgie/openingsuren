package be.webfactor.openinghours.premium.domain;

import java.io.Serializable;

public class DailyOpeningTime implements Serializable {

	private final String am;
	private final String pm;
	
	public DailyOpeningTime(String am, String pm) {
		this.am = am;
		this.pm = pm;
	}

	public String getAm() {
		return compact(am);
	}

	public String getPm() {
		return compact(pm);
	}
	
	private String compact(String hours) {
		if (hours == null) {
			return null;
		}
		return hours.replace(" - ", "-").replace(" ˆ ", "-");
	}

}