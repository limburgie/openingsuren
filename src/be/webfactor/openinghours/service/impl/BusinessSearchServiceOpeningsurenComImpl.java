package be.webfactor.openinghours.service.impl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import be.webfactor.openinghours.domain.Business;
import be.webfactor.openinghours.domain.BusinessSearchQuery;
import be.webfactor.openinghours.domain.BusinessSearchResult;
import be.webfactor.openinghours.domain.DailyOpeningTime;
import be.webfactor.openinghours.service.BusinessSearchService;
import be.webfactor.openinghours.service.ConnectionException;

public class BusinessSearchServiceOpeningsurenComImpl implements BusinessSearchService {

	private static final String TOO_MANY_RESULTS = "Enkel de eerste 300 handelszaken worden getoond. Gelieve uw selectiecriteria eventueel te verfijnen.";
	private static final String IP_BLOCKED = "Deze pagina is niet meer beschikbaar voor uw IP-adres";
	private static final String BASE_URL = "http://www.openingsuren.com/";
	private static final String NO_RESULTS_FOUND = "Gevraagde openingsuren niet gevonden.";
	private static final String SEARCH_FORMAT = BASE_URL + "lijst.php?zoekveld=%s&veldgem=%s";

	public BusinessSearchResult findBusinesses(BusinessSearchQuery query) {
		String safeName = query.getWhat();
		String safeCity = query.getWhere();

		Document resultsPage = connect(String.format(SEARCH_FORMAT, safeName, safeCity));
		BusinessSearchResult result = new BusinessSearchResult();
		if (resultsPage.text().contains(NO_RESULTS_FOUND)) {
			return result;
		}
		int startIndex = 2;
		if (resultsPage.text().contains(TOO_MANY_RESULTS)) {
			startIndex++;
		}
		result.setResultCount(getResultCount(resultsPage));

		List<Business> firstTenResults = result.getFirstResults();
		Elements rows = resultsPage.select("table[bordercolordark=#000266]").first().select("tr");
		for (int i = startIndex; i < rows.size() - 5; i++) {
			Business business = new Business();
			Elements columns = rows.get(i).select("td");
			business.setOpen(columns.get(0).attr("bgcolor").equals("#33FF33"));
			business.setName(columns.get(1).text());
			business.setUrl(columns.get(1).select("a").attr("href"));
			business.setCategory(columns.get(2).text());
			business.setCity(columns.get(3).text().concat(" ").concat(columns.get(4).text()));
			
			firstTenResults.add(business);
		}

		return result;
	}

	public Business getDetail(Business business) {
		Document detailPage = connect(BASE_URL + business.getUrl());
		if (detailPage.text().contains(IP_BLOCKED)) {
			throw new IPBlockedException();
		}
		Element resultTable = detailPage.select("table[bgcolor=#ffffcc]").first();
		return createBusiness(resultTable);
	}

	private Document connect(String url) {
		try {
			return Jsoup.connect(url).get();
		} catch (IOException e) {
			throw new ConnectionException(e);
		}
	}

	private Business createBusiness(Element context) {
		Business business = new Business();

		business.setName(getName(context));
		business.setCategory(getCategory(context));
		business.setStreet(getStreet(context));
		business.setCity(getCity(context));
		business.setProvince(getProvince(context));
		business.setPhone(getPhone(context));
		business.setFax(getFax(context));
		business.setMonday(getOpeningTime(context, "Maandag"));
		business.setTuesday(getOpeningTime(context, "Dinsdag"));
		business.setWednesday(getOpeningTime(context, "Woensdag"));
		business.setThursday(getOpeningTime(context, "Donderdag"));
		business.setFriday(getOpeningTime(context, "Vrijdag"));
		business.setSaturday(getOpeningTime(context, "Zaterdag"));
		business.setSunday(getOpeningTime(context, "Zondag"));
		business.setHoliday(getOpeningTime(context, "Feestdag"));
		business.setExtraInfo(getExtraInfo(context));
		business.setLastModified(getLastModified(context));

		return business;
	}

	private Date getLastModified(Element context) {
		String lastModifiedText = context.select("font[size=1]").first().text();
		DateFormat dateFormat = new SimpleDateFormat("'Openingsuren laatst gecontroleerd op 'd MMMMM yyyy", new Locale("nl"));
		try {
			return dateFormat.parse(lastModifiedText);
		} catch (ParseException e) {
			return null;
		}
	}

	private String getExtraInfo(Element context) {
		return context.select("td[bgcolor=orangered]").text();
	}

	private DailyOpeningTime getOpeningTime(Element context, String day) {
		String am = null, pm = null;
		Elements elements = context.select("td:nth-child(1)[align=CENTER]");
		for (int i = 0; i < elements.size(); i++) {
			Element element = elements.get(i);
			if (day.equals(element.text())) {
				Element amElement = element.nextElementSibling();
				am = amElement.text();
				Element pmElement = amElement.nextElementSibling();
				if (pmElement != null) {
					pm = pmElement.text();
				}
			}
		}
		return new DailyOpeningTime(am, pm);
	}

	private String getFax(Element context) {
		String result = context.select("tr:nth-child(7) td").text();
		if (result.contains("Fax")) {
			return result.replaceAll("Fax", "").trim();
		}
		return null;
	}

	private String getPhone(Element context) {
		String phone = context.select("tr:nth-child(6) td").text();
		if (phone.contains("Telefoon")) {
			return phone.replaceAll("Telefoon", "").trim();
		}
		return null;
	}

	private String getProvince(Element context) {
		return context.select("tr:nth-child(5) td").text().replaceAll("Provincie", "").trim();
	}

	private String getCity(Element context) {
		return context.select("tr:nth-child(4) td").text();
	}

	private String getStreet(Element context) {
		return context.select("tr:nth-child(3) td").text();
	}

	private String getCategory(Element context) {
		return context.select("tr:nth-child(2) a").text();
	}

	private String getName(Element context) {
		return context.select("h1").first().text();
	}

	private int getResultCount(Element context) {
		Element resultCountElement = context.select("td[colspan=2] b").first();
		String count = resultCountElement.text().replaceFirst(".*?(\\d+).*", "$1");
		return Integer.parseInt(count);
	}
	
}
