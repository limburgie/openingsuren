package be.webfactor.openinghours.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BusinessSearchResult implements Serializable {

	private int resultCount;
	private List<Business> firstResults = new ArrayList<Business>();

	public int getResultCount() {
		return resultCount;
	}

	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}

	public List<Business> getFirstResults() {
		return firstResults;
	}

	public void setFirstTenResults(List<Business> firstResults) {
		this.firstResults = firstResults;
	}

}
