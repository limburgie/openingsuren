package be.webfactor.openinghours.service;

import be.webfactor.openinghours.domain.Business;
import be.webfactor.openinghours.domain.BusinessSearchQuery;
import be.webfactor.openinghours.domain.BusinessSearchResult;

public interface BusinessSearchService {

	BusinessSearchResult findBusinesses(BusinessSearchQuery query);
	
	BusinessSearchResult getMoreResults(BusinessSearchResult result);
	
	Business getDetail(Business business);
	
}
