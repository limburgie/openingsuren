package be.webfactor.openinghours.premium.service.impl;

public interface TranslatedLabelInfo {

	String getTooManyResultsLabel();
	
	String getIpBlockedLabel();
	
	String getBaseUrl();
	
	String getNoResultsFoundLabel();
	
	String getPhoneLabel();
	
	String getFaxLabel();
	
	boolean hasProvinceInfo();
	
	String getProvinceLabel();
	
	String getMondayLabel();
	
	String getTuesdayLabel();
	
	String getWednesdayLabel();
	
	String getThursdayLabel();
	
	String getFridayLabel();
	
	String getSaturdayLabel();
	
	String getSundayLabel();
	
	String getHolidayLabel();
	
	String getLastReviewedLabel();
	
	String getLastReviewedDateFormat();
	
	String getLanguage();
	
}
