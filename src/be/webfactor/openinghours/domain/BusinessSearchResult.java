package be.webfactor.openinghours.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BusinessSearchResult implements Serializable {

	private int resultCount;
	private List<Business> pageResults = new ArrayList<Business>();
	private String nextResultPageUrl;

	public boolean hasMoreResults() {
		return nextResultPageUrl != null;
	}
	
	public int getResultCount() {
		return resultCount;
	}

	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}

	public List<Business> getPageResults() {
		return pageResults;
	}

	public void setPageResults(List<Business> pageResults) {
		this.pageResults = pageResults;
	}

	public String getNextResultPageUrl() {
		return nextResultPageUrl;
	}

	public void setNextResultPageUrl(String nextResultPageUrl) {
		this.nextResultPageUrl = nextResultPageUrl;
	}

}
