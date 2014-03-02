package be.webfactor.openinghours.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;
import be.webfactor.openinghours.R;
import be.webfactor.openinghours.adapter.BusinessAdapter;
import be.webfactor.openinghours.domain.BusinessSearchQuery;
import be.webfactor.openinghours.domain.BusinessSearchResult;
import be.webfactor.openinghours.service.BusinessSearchService;
import be.webfactor.openinghours.service.impl.BusinessSearchServiceOpeningsurenComImpl;

public class ResultsActivity extends Activity {

	private ProgressDialog pd;
	private ListView resultList;
	private BusinessSearchService service;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initializeProgressBar();

		service = new BusinessSearchServiceOpeningsurenComImpl();
//		service = new BusinessSearchServiceMockImpl();
		buildLayout();
		populateResultList();
	}

	@SuppressLint("InlinedApi")
	private void initializeProgressBar() {
		if(Build.VERSION.SDK_INT >= 11) {
			pd = new ProgressDialog(this, ProgressDialog.THEME_HOLO_DARK);
		} else {
			pd = new ProgressDialog(this);
		}
		pd.setTitle(getResources().getString(R.string.loading));
		pd.setMessage(getResources().getString(R.string.retrieving_results));
		pd.show();
	}

	private void buildLayout() {
		setContentView(R.layout.activity_results);
		resultList = (ListView) findViewById(R.id.resultList);
	}

	private void populateResultList() {
		BusinessSearchQuery query = (BusinessSearchQuery) getIntent().getSerializableExtra(BusinessSearchQuery.class.getName());
		new FetchResultsTask().execute(query);
	}
	
	private class FetchResultsTask extends AsyncTask<BusinessSearchQuery, Void, BusinessSearchResult> {
		protected BusinessSearchResult doInBackground(BusinessSearchQuery... params) {
			return service.findBusinesses(params[0]);
		}
		
		protected void onPostExecute(BusinessSearchResult result) {
			resultList.setAdapter(new BusinessAdapter(getApplicationContext(), result.getFirstResults()));
			pd.dismiss();
		}
	}
	
}
