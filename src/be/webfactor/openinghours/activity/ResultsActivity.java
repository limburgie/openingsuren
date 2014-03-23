package be.webfactor.openinghours.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import be.webfactor.openinghours.R;
import be.webfactor.openinghours.adapter.BusinessAdapter;
import be.webfactor.openinghours.domain.Business;
import be.webfactor.openinghours.domain.BusinessSearchResult;
import be.webfactor.openinghours.service.AdFactory;
import be.webfactor.openinghours.service.BusinessSearchServiceFactory;
import be.webfactor.openinghours.service.ErrorHandlerFactory;
import be.webfactor.openinghours.service.impl.IPBlockedException;

public class ResultsActivity extends Activity {

	private static final int MAX_RESULTS = 300;
	
	private BusinessSearchResult searchResult;
	
	private ProgressDialog pd;
	private ListView resultList;
	private TextView message;
	
	private int visibleThreshold = 5;
    private int previousTotal = 0;
    private boolean loading = true;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		buildLayout();
		new AdFactory(this).setup();
		populateResultList();
	}
	
	protected void onDestroy() {
		super.onDestroy();
		if (pd != null) {
			pd.dismiss();
		}
	}

	private void populateResultList() {
		searchResult = (BusinessSearchResult) getIntent().getSerializableExtra(BusinessSearchResult.class.getName());
		
		int resultCount = searchResult.getResultCount();
		if (resultCount == MAX_RESULTS) {
			message.setText(getResources().getString(R.string.more_than_300_results_found));
		} else {
			message.setText(getResources().getString(R.string.x_results_found, resultCount));
		}

		BusinessAdapter adapter = new BusinessAdapter(getApplicationContext(), searchResult.getPageResults());
		resultList.setAdapter(adapter);
	}

	private void buildLayout() {
		setContentView(R.layout.activity_results);
		resultList = (ListView) findViewById(R.id.resultList);
		resultList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				initializeProgressBar();
				Business business = (Business) parent.getAdapter().getItem(position);
				new FetchDetailTask().execute(business);
			}
		});
		resultList.setOnScrollListener(new OnScrollListener() {
			
			public void onScrollStateChanged(AbsListView view, int scrollState) {}
			
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if (loading) {
		            if (totalItemCount > previousTotal) {
		                loading = false;
		                previousTotal = totalItemCount;
		            }
		        }
		        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold) && searchResult.hasMoreResults()) {
		            new FetchMoreResultsTask().execute(searchResult);
		            loading = true;
		        }
			}
		});
		message = (TextView) findViewById(R.id.message);
	}
	
	@SuppressLint("InlinedApi")
	private void initializeProgressBar() {
		if (Build.VERSION.SDK_INT >= 11) {
			pd = new ProgressDialog(this, ProgressDialog.THEME_HOLO_DARK);
		} else {
			pd = new ProgressDialog(this);
		}
		pd.setTitle(getResources().getString(R.string.loading));
		pd.setMessage(getResources().getString(R.string.retrieving_details));
		pd.show();
	}
	
	private class FetchDetailTask extends AsyncTask<Business, Void, Business> {
		protected Business doInBackground(Business... params) {
			try {
				return BusinessSearchServiceFactory.getInstance().getDetail(params[0]);
			} catch (IPBlockedException e) {
				return null;
			}
		}

		protected void onPostExecute(Business business) {
			if (business == null) {
				ErrorHandlerFactory.forContext(getBaseContext()).error(R.string.max_retrievals_reached);
				pd.dismiss();
				return;
			}
			
			Intent i = new Intent(getApplicationContext(), DetailActivity.class);
			i.putExtra(Business.class.getName(), business);
			startActivity(i);
			pd.dismiss();
		}
	}
	
	private class FetchMoreResultsTask extends AsyncTask<BusinessSearchResult, Void, BusinessSearchResult> {

		protected BusinessSearchResult doInBackground(BusinessSearchResult... params) {
			return BusinessSearchServiceFactory.getInstance().getMoreResults(params[0]);
		}
		
		protected void onPostExecute(BusinessSearchResult result) {
			searchResult = result;
			BusinessAdapter adapter = (BusinessAdapter) resultList.getAdapter();
			adapter.addBusinesses(result.getPageResults());
		}
		
	}

}
