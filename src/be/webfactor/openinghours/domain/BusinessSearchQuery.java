package be.webfactor.openinghours.domain;

import java.io.Serializable;

public class BusinessSearchQuery implements Serializable {

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
