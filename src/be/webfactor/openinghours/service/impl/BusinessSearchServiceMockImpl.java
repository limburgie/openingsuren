package be.webfactor.openinghours.service.impl;

import java.util.Arrays;

import be.webfactor.openinghours.domain.Business;
import be.webfactor.openinghours.domain.BusinessSearchQuery;
import be.webfactor.openinghours.domain.BusinessSearchResult;
import be.webfactor.openinghours.domain.DailyOpeningTime;
import be.webfactor.openinghours.service.BusinessSearchService;

public class BusinessSearchServiceMockImpl implements BusinessSearchService {

	public BusinessSearchResult findBusinesses(BusinessSearchQuery query) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(query.getWhat().equals("zero")) {
			return new BusinessSearchResult();
		}
		
		if(query.getWhat().equals("error")) {
			throw new IllegalArgumentException();
		}
		
		BusinessSearchResult result = new BusinessSearchResult();
		result.setResultCount(234);
		result.setFirstTenResults(
			Arrays.asList(
				createBusiness("Business 1", "Auto-onderdelen", "3620 Lanaken", true), 
				createBusiness("Business 2", "Vleeswaren", "3600 Genk", false),
				createBusiness("Business 3", "Multimedia", "3500 Hasselt", true),
				createBusiness("Business 4", "Auto-onderdelen", "3620 Lanaken", true), 
				createBusiness("Business 5", "Vleeswaren", "3600 Genk", false),
				createBusiness("Business 6", "Multimedia", "3500 Hasselt", true),
				createBusiness("Business 7", "Auto-onderdelen", "3620 Lanaken", true), 
				createBusiness("Business 8", "Vleeswaren", "3600 Genk", false),
				createBusiness("Business 9", "Multimedia", "3500 Hasselt", true),
				createBusiness("Business 10", "Auto-onderdelen", "3620 Lanaken", true), 
				createBusiness("Business 11", "Vleeswaren", "3600 Genk", false),
				createBusiness("Business 12", "Multimedia", "3500 Hasselt", true)
			)
		);
		return result;
	}

	private Business createBusiness(String name, String category, String city, boolean open) {
		Business business = new Business();
		business.setName(name);
		business.setCategory(category);
		business.setCity(city);
		business.setOpen(open);
		business.setStreet(city+"straat 123");
		business.setMonday(new DailyOpeningTime("6:00 - 11:00", "13:00 - 18:00"));
		business.setTuesday(new DailyOpeningTime("7:00 - 11:00", "14:00 - 18:00"));
		business.setWednesday(new DailyOpeningTime("8:00 - 11:00", "15:00 - 18:00"));
		business.setThursday(new DailyOpeningTime("7:00 - 11:00", "14:00 - 18:00"));
		business.setFriday(new DailyOpeningTime("6:00 - 11:00", "13:00 - 18:00"));
		business.setSaturday(new DailyOpeningTime("7:00 - 11:00", "14:00 - 18:00"));
		business.setSunday(new DailyOpeningTime("8:00 - 11:00", "15:00 - 18:00"));
		business.setHoliday(new DailyOpeningTime("9:00 - 11:00", null));
		business.setPhone("123 456 789");
		business.setFax("987 654 321");
		return business;
	}

	public Business getDetail(Business business) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(business.getName().equals("Business 3")) {
			throw new IllegalArgumentException();
		}
		return business;
	}

}
