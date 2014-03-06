package be.webfactor.openinghours.service;

import be.webfactor.openinghours.domain.Business;
import be.webfactor.openinghours.domain.BusinessSearchQuery;
import be.webfactor.openinghours.domain.BusinessSearchResult;

public interface BusinessSearchService {

	BusinessSearchResult findBusinesses(BusinessSearchQuery query);
	
	Business getDetail(Business business);
	
}
