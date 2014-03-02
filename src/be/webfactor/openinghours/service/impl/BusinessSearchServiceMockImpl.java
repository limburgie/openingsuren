package be.webfactor.openinghours.service.impl;

import java.util.Arrays;

import be.webfactor.openinghours.domain.Business;
import be.webfactor.openinghours.domain.BusinessSearchQuery;
import be.webfactor.openinghours.domain.BusinessSearchResult;
import be.webfactor.openinghours.service.BusinessSearchService;

public class BusinessSearchServiceMockImpl implements BusinessSearchService {

	public BusinessSearchResult findBusinesses(BusinessSearchQuery query) {
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
		return business;
	}

	public Business getDetail(Business business) {
		return business;
	}

}
