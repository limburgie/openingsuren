package be.webfactor.openinghours.premium.service;

import be.webfactor.openinghours.premium.domain.Business;
import be.webfactor.openinghours.premium.domain.BusinessSearchQuery;
import be.webfactor.openinghours.premium.domain.BusinessSearchResult;

public interface BusinessSearchService {

	BusinessSearchResult findBusinesses(BusinessSearchQuery query);
	
	BusinessSearchResult getMoreResults(BusinessSearchResult result);
	
	Business getDetail(Business business);
	
}
