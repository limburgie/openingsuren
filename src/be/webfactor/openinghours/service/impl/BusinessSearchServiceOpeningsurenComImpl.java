package be.webfactor.openinghours.service.impl;

import java.io.IOException;
import java.net.URL;
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

	private static final String AD_PREFIX = "! ";
	private static final String SEARCH_QUERY = "lijst.php?zoekveld=%s&veldgem=%s";

	private TranslatedLabelInfo labels;

	public BusinessSearchServiceOpeningsurenComImpl() {
		if (Locale.getDefault().getLanguage().equals("fr")) {
			labels = new FrenchTranslatedLabelInfo();
		} else {
			labels = new DutchTranslatedLabelInfo();
		}
	}

	public BusinessSearchResult findBusinesses(BusinessSearchQuery query) {
		String safeName = query.getWhat();
		String safeCity = query.getWhere();

		Document resultsPage = connect(String.format(labels.getBaseUrl() + SEARCH_QUERY, safeName, safeCity));
		return parseResult(resultsPage);
	}

	public BusinessSearchResult getMoreResults(BusinessSearchResult result) {
		Document resultsPage = connect(labels.getBaseUrl() + result.getNextResultPageUrl());
		return parseResult(resultsPage);
	}

	private BusinessSearchResult parseResult(Document resultsPage) {
		BusinessSearchResult result = new BusinessSearchResult();
		if (resultsPage.text().contains(labels.getNoResultsFoundLabel())) {
			return result;
		}
		int startIndex = 2;
		if (resultsPage.text().contains(labels.getTooManyResultsLabel())) {
			startIndex++;
		}
		result.setResultCount(getResultCount(resultsPage));

		List<Business> firstTenResults = result.getPageResults();
		Elements rows = resultsPage.select("table[bordercolordark=#000266]").first().select("tr");
		for (int i = startIndex; i < rows.size() - 5; i++) {
			Business business = new Business();
			Elements columns = rows.get(i).select("td");
			business.setOpen(!columns.get(0).attr("bgcolor").equals("red"));
			String name = columns.get(1).text();
			if (name.startsWith(AD_PREFIX)) {
				name = name.replace(AD_PREFIX, "");
				business.setAdvertised(true);
			}
			business.setName(name);
			business.setUrl(columns.get(1).select("a").attr("href"));
			business.setCategory(columns.get(2).text());
			business.setCity(columns.get(3).text().concat(" ").concat(columns.get(4).text()));

			firstTenResults.add(business);
		}
		Elements moreResultsLink = resultsPage.select("td[colspan=2][align=right] a");
		if (moreResultsLink.isEmpty()) {
			result.setNextResultPageUrl(null);
		} else {
			result.setNextResultPageUrl(moreResultsLink.first().attr("href"));
		}

		return result;
	}

	public Business getDetail(Business business) {
		Document detailPage = connect(labels.getBaseUrl() + business.getUrl());
		if (detailPage.text().contains(labels.getIpBlockedLabel())) {
			throw new IPBlockedException();
		}
		Element resultTable = detailPage.select("table[bgcolor=#ffffcc]").first();
		return createBusiness(business, resultTable);
	}

	private Document connect(String url) {
		try {
			return Jsoup.parse(new URL(url).openStream(), "ISO-8859-1", url);
		} catch (IOException e) {
			throw new ConnectionException(e);
		}
	}

	private Business createBusiness(Business business, Element context) {
		Business result = business;

		Element row = getBaseRow(context);
		// street
		row = row.nextElementSibling().nextElementSibling();
		result.setStreet(row.text());
		row = row.nextElementSibling();
		// province
		if (labels.hasProvinceInfo()) {
			row = row.nextElementSibling();
			result.setProvince(row.text().replace(labels.getProvinceLabel(), ""));
		}
		// phone
		row = row.nextElementSibling();
		if (row.text().contains(labels.getPhoneLabel())) {
			result.setPhone(row.text().replace(labels.getPhoneLabel(), ""));
		}
		// fax
		row = row.nextElementSibling();
		if (row.text().contains(labels.getFaxLabel())) {
			result.setFax(row.text().replace(labels.getFaxLabel(), ""));
		}

		result.setMonday(getOpeningTime(context, labels.getMondayLabel()));
		result.setTuesday(getOpeningTime(context, labels.getTuesdayLabel()));
		result.setWednesday(getOpeningTime(context, labels.getWednesdayLabel()));
		result.setThursday(getOpeningTime(context, labels.getThursdayLabel()));
		result.setFriday(getOpeningTime(context, labels.getFridayLabel()));
		result.setSaturday(getOpeningTime(context, labels.getSaturdayLabel()));
		result.setSunday(getOpeningTime(context, labels.getSundayLabel()));
		result.setHoliday(getOpeningTime(context, labels.getHolidayLabel()));
		result.setExtraInfo(getExtraInfo(context));
		result.setLastModified(getLastModified(context));

		return result;
	}

	private Element getBaseRow(Element table) {
		for (Element row : table.select("tr")) {
			if (row.select("h1").size() > 0 || row.select("font[size=4]").size() > 0) {
				return row;
			}
		}
		return null;
	}

	private Date getLastModified(Element context) {
		Elements lastModifiedDateElement = context.select("font[size][color=#000266]");
		if (!lastModifiedDateElement.isEmpty()) {
			for (Element element : lastModifiedDateElement) {
				if (element.text().contains(labels.getLastReviewedLabel())) {
					DateFormat dateFormat = new SimpleDateFormat(labels.getLastReviewedDateFormat(), new Locale(labels.getLanguage()));
					try {
						return dateFormat.parse(element.text());
					} catch (ParseException e) {
						return null;
					}

				}
			}
		}
		return null;
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

	private int getResultCount(Element context) {
		Element resultCountElement = context.select("td[colspan=2] b").first();
		String count = resultCountElement.text().replaceFirst(".*?(\\d+).*", "$1");
		return Integer.parseInt(count);
	}

}
