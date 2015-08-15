package be.webfactor.openinghours.premium.service.impl;

public class FrenchTranslatedLabelInfo implements TranslatedLabelInfo {

	private static final String LAST_REVIEWED_LABEL = "Dernier contrôle des heures d'ouverture le";

	public String getTooManyResultsLabel() {
		return "Seuls les 300 premiers résultats de la recherche sont affichés.";
	}

	public String getIpBlockedLabel() {
		return "Vous n'est pas autorisé a accéder au contenu de cette page";
	}

	public String getBaseUrl() {
		return "http://www.heures-douverture.com/";
	}

	public String getNoResultsFoundLabel() {
		return "Malhereusement les heures d'ouvertures demandés ne sont pas disponibles.";
	}

	public String getPhoneLabel() {
		return "Téléphone";
	}

	public String getFaxLabel() {
		return "Téléfax";
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
		return "Jour Férié";
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
