package be.webfactor.openinghours.premium.domain;

public class BusinessSearchQuery {

	private String what;
	private String where;
	
	public BusinessSearchQuery(String what, String where) {
		this.what = what;
		this.where = where;
	}
	
	public String getWhat() {
		return what == null ? "" : what;
	}
	
	public String getWhere() {
		return where == null ? "" : where;
	}
	
}
