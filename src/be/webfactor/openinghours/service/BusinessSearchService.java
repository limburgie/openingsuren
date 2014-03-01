package be.webfactor.openinghours.service;

import be.webfactor.openinghours.domain.BusinessSearchResult;

public interface BusinessSearchService {

	BusinessSearchResult findBusinesses(String name, String city);
	
}
