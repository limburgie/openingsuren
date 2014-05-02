package be.webfactor.openinghours.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;

import org.ocpsoft.prettytime.PrettyTime;

public class Business implements Serializable {

	private boolean advertised;
	private String name;
	private String category;
	private String street;
	private String city;
	private String province;
	private String phone;
	private String extraInfo;
	private Date lastModified;
	private boolean open;
	private String url;

	private DailyOpeningTime monday;
	private DailyOpeningTime tuesday;
	private DailyOpeningTime wednesday;
	private DailyOpeningTime thursday;
	private DailyOpeningTime friday;
	private DailyOpeningTime saturday;
	private DailyOpeningTime sunday;
	private DailyOpeningTime holiday;

	public String getLastReviewedLabel(String format) {
		if (lastModified == null) {
			return null;
		}
		String agoLabel = new PrettyTime(getLocale()).format(lastModified);
		return String.format(format, agoLabel);
	}

	private Locale getLocale() {
		if(Locale.getDefault().getLanguage().equals("fr")) {
			return Locale.getDefault();
		}
		return new Locale("nl");
	}
	
	public boolean isAdvertised() {
		return advertised;
	}

	public void setAdvertised(boolean advertised) {
		this.advertised = advertised;
	}

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

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
