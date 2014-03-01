package be.webfactor.openinghours.domain;

import java.util.ArrayList;
import java.util.List;

public class BusinessSearchResult {

	private int resultCount;
	private List<Business> firstTenResults = new ArrayList<Business>();

	public int getResultCount() {
		return resultCount;
	}

	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}

	public List<Business> getFirstTenResults() {
		return firstTenResults;
	}

	public void setFirstTenResults(List<Business> firstTenResults) {
		this.firstTenResults = firstTenResults;
	}

}
