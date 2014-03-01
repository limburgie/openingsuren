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
import be.webfactor.openinghours.domain.BusinessSearchResult;
import be.webfactor.openinghours.domain.DailyOpeningTime;
import be.webfactor.openinghours.service.BusinessSearchService;
import be.webfactor.openinghours.service.ConnectionException;

public class BusinessSearchServiceOpeningsurenComImpl implements BusinessSearchService {

	private static final String BASE_URL = "http://www.openingsuren.com/";
	private static final String NO_RESULTS_FOUND = "Gevraagde openingsuren niet gevonden.";
	private static final String SEARCH_FORMAT = BASE_URL + "lijst.php?zoekveld=%s&veldgem=%s";
	
	public BusinessSearchResult findBusinesses(String name, String city) {
		String safeName = name == null ? "" : name;
		String safeCity = city == null ? "" : city;
		
		Document resultsPage = connect(String.format(SEARCH_FORMAT, safeName, safeCity));
		BusinessSearchResult result = new BusinessSearchResult();
		if(resultsPage.text().contains(NO_RESULTS_FOUND)) {
			return result;
		}
		result.setResultCount(getResultCount(resultsPage));
		
		List<Business> firstTenResults = result.getFirstTenResults();
		Elements elements = resultsPage.select("table table font[size=2] a");
		for (Element businessLink : elements) {
			Document detailPage = connect(BASE_URL + businessLink.attr("href"));
			Element resultTable = detailPage.select("table[bgcolor=#ffffcc]").first();
			firstTenResults.add(createBusiness(resultTable));
		}
		
		return result;
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
		for(int i = 0; i < elements.size(); i++) {
			Element element = elements.get(i);
			if(day.equals(element.text())) {
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
		return context.select("tr:nth-child(6) td").text().replaceAll("Telefoon", "").trim();
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
