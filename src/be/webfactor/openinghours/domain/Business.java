package be.webfactor.openinghours.domain;

import java.util.Date;

public class Business {

	private String name;
	private String category;
	private String street;
	private String city;
	private String province;
	private String phone;
	private String fax;
	private String extraInfo;
	private Date lastModified;

	private DailyOpeningTime monday;
	private DailyOpeningTime tuesday;
	private DailyOpeningTime wednesday;
	private DailyOpeningTime thursday;
	private DailyOpeningTime friday;
	private DailyOpeningTime saturday;
	private DailyOpeningTime sunday;
	private DailyOpeningTime holiday;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getExtraInfo() {
		return extraInfo;
	}

	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public DailyOpeningTime getMonday() {
		return monday;
	}

	public void setMonday(DailyOpeningTime monday) {
		this.monday = monday;
	}

	public DailyOpeningTime getTuesday() {
		return tuesday;
	}

	public void setTuesday(DailyOpeningTime tuesday) {
		this.tuesday = tuesday;
	}

	public DailyOpeningTime getWednesday() {
		return wednesday;
	}

	public void setWednesday(DailyOpeningTime wednesday) {
		this.wednesday = wednesday;
	}

	public DailyOpeningTime getThursday() {
		return thursday;
	}

	public void setThursday(DailyOpeningTime thursday) {
		this.thursday = thursday;
	}

	public DailyOpeningTime getFriday() {
		return friday;
	}

	public void setFriday(DailyOpeningTime friday) {
		this.friday = friday;
	}

	public DailyOpeningTime getSaturday() {
		return saturday;
	}

	public void setSaturday(DailyOpeningTime saturday) {
		this.saturday = saturday;
	}

	public DailyOpeningTime getSunday() {
		return sunday;
	}

	public void setSunday(DailyOpeningTime sunday) {
		this.sunday = sunday;
	}

	public DailyOpeningTime getHoliday() {
		return holiday;
	}

	public void setHoliday(DailyOpeningTime holiday) {
		this.holiday = holiday;
	}

}
