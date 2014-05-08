package be.webfactor.openinghours.service;

public interface ErrorHandler {

	void error(int message, Object... params);

}
