package be.webfactor.openinghours.domain;

public class DailyOpeningTime {

	private final String am;
	private final String pm;
	
	public DailyOpeningTime(String am, String pm) {
		this.am = am;
		this.pm = pm;
	}

	public String getAm() {
		return am;
	}

	public String getPm() {
		return pm;
	}

}