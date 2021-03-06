package be.webfactor.openinghours.service.impl;

public class DutchTranslatedLabelInfo implements TranslatedLabelInfo {

	private static final String LAST_REVIEWED_LABEL = "Openingsuren laatst gecontroleerd op";

	public String getTooManyResultsLabel() {
		return "Enkel de eerste 300 handelszaken worden getoond. Gelieve uw selectiecriteria eventueel te verfijnen.";
	}

	public String getIpBlockedLabel() {
		return "Deze pagina is niet meer beschikbaar voor uw IP-adres";
	}

	public String getBaseUrl() {
		return "http://www.openingsuren.com/";
	}

	public String getNoResultsFoundLabel() {
		return "Gevraagde openingsuren niet gevonden.";
	}

	public String getPhoneLabel() {
		return "Telefoon";
	}

	public String getFaxLabel() {
		return "Fax";
	}

	public boolean hasProvinceInfo() {
		return true;
	}

	public String getProvinceLabel() {
		return "Provincie";
	}

	public String getMondayLabel() {
		return "Maandag";
	}

	public String getTuesdayLabel() {
		return "Dinsdag";
	}

	public String getWednesdayLabel() {
		return "Woensdag";
	}

	public String getThursdayLabel() {
		return "Donderdag";
	}

	public String getFridayLabel() {
		return "Vrijdag";
	}

	public String getSaturdayLabel() {
		return "Zaterdag";
	}

	public String getSundayLabel() {
		return "Zondag";
	}

	public String getHolidayLabel() {
		return "Feestdag";
	}
	
	public String getLastReviewedLabel() {
		return LAST_REVIEWED_LABEL;
	}

	public String getLastReviewedDateFormat() {
		return "'" + LAST_REVIEWED_LABEL + "'d MMMMM yyyy";
	}

	public String getLanguage() {
		return "nl";
	}

}
