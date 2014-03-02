package be.webfactor.openinghours.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import be.webfactor.openinghours.R;
import be.webfactor.openinghours.adapter.BusinessAdapter;
import be.webfactor.openinghours.domain.BusinessSearchQuery;
import be.webfactor.openinghours.domain.BusinessSearchResult;
import be.webfactor.openinghours.service.BusinessSearchService;
import be.webfactor.openinghours.service.impl.BusinessSearchServiceOpeningsurenComImpl;

public class ResultsActivity extends Activity {

	private ProgressDialog pd;
	private ListView resultList;
	private TextView message;
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
		message = (TextView) findViewById(R.id.message);
	}

	private void populateResultList() {
		BusinessSearchQuery query = (BusinessSearchQuery) getIntent().getSerializableExtra(BusinessSearchQuery.class.getName());
		new FetchResultsTask().execute(query);
	}
	
	private class FetchResultsTask extends AsyncTask<BusinessSearchQuery, Void, BusinessSearchResult> {
		protected BusinessSearchResult doInBackground(BusinessSearchQuery... params) {
			try {
				return service.findBusinesses(params[0]);
			} catch(Throwable t) {
				message.setText(getResources().getString(R.string.unexpected_error));
				return new BusinessSearchResult();
			}
		}
		
		protected void onPostExecute(BusinessSearchResult result) {
			int resultCount = result.getResultCount();
			if(resultCount == 0) {
				message.setText(getResources().getString(R.string.no_results_found));
			} else {
				message.setText(getResources().getString(R.string.x_results_found, resultCount));
			}
			resultList.setAdapter(new BusinessAdapter(getApplicationContext(), result.getFirstResults()));
			pd.dismiss();
		}
	}
	
}
