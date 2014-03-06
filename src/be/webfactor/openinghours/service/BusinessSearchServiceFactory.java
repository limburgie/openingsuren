package be.webfactor.openinghours.service;

import be.webfactor.openinghours.service.impl.BusinessSearchServiceOpeningsurenComImpl;

public class BusinessSearchServiceFactory {

	private static BusinessSearchService SERVICE;
	
	public static BusinessSearchService getInstance() {
		if (SERVICE == null) {
			SERVICE = new BusinessSearchServiceOpeningsurenComImpl();
		}
		return SERVICE;
	}
	
}
