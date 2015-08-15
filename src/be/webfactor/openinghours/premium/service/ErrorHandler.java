package be.webfactor.openinghours.premium.service;

public interface ErrorHandler {

	void error(int message, Object... params);

}
