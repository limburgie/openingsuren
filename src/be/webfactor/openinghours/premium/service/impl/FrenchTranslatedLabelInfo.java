package be.webfactor.openinghours.premium.service.impl;

public class FrenchTranslatedLabelInfo implements TranslatedLabelInfo {

	private static final String LAST_REVIEWED_LABEL = "Dernier contr�le des heures d'ouverture le";

	public String getTooManyResultsLabel() {
		return "Seuls les 300 premiers r�sultats de la recherche sont affich�s.";
	}

	public String getIpBlockedLabel() {
		return "Vous n'est pas autoris� a acc�der au contenu de cette page";
	}

	public String getBaseUrl() {
		return "http://www.heures-douverture.com/";
	}

	public String getNoResultsFoundLabel() {
		return "Malhereusement les heures d'ouvertures demand�s ne sont pas disponibles.";
	}

	public String getPhoneLabel() {
		return "T�l�phone";
	}

	public String getFaxLabel() {
		return "T�l�fax";
	}

	public boolean hasProvinceInfo() {
		return false;
	}

	public String getProvinceLabel() {
		return null;
	}

	public String getMondayLabel() {
		return "Lundi";
	}

	public String getTuesdayLabel() {
		return "Mardi";
	}

	public String getWednesdayLabel() {
		return "Mercredi";
	}

	public String getThursdayLabel() {
		return "Jeudi";
	}

	public String getFridayLabel() {
		return "Vendredi";
	}

	public String getSaturdayLabel() {
		return "Samedi";
	}

	public String getSundayLabel() {
		return "Dimanche";
	}

	public String getHolidayLabel() {
		return "Jour F�ri�";
	}

	public String getLastReviewedLabel() {
		return LAST_REVIEWED_LABEL;
	}

	public String getLastReviewedDateFormat() {
		return "'" + LAST_REVIEWED_LABEL.replace("'", "''") + "'d MMMMM yyyy";
	}

	public String getLanguage() {
		return "fr";
	}

}
