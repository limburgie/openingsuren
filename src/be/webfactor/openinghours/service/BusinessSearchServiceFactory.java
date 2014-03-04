package be.webfactor.openinghours.service;

import be.webfactor.openinghours.service.impl.BusinessSearchServiceMockImpl;

public class BusinessSearchServiceFactory {

	private static BusinessSearchService SERVICE;
	
	public static BusinessSearchService getInstance() {
		if (SERVICE == null) {
			SERVICE = new BusinessSearchServiceMockImpl();
		}
		return SERVICE;
	}
	
}
