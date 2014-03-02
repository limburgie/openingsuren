package be.webfactor.openinghours.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import be.webfactor.openinghours.R;
import be.webfactor.openinghours.adapter.BusinessAdapter;
import be.webfactor.openinghours.domain.Business;
import be.webfactor.openinghours.domain.BusinessSearchQuery;
import be.webfactor.openinghours.domain.BusinessSearchResult;
import be.webfactor.openinghours.service.BusinessSearchServiceFactory;

public class ResultsActivity extends Activity {

	private ProgressDialog pd;
	private ListView resultList;
	private TextView message;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		buildLayout();
		initializeProgressBar();
		populateResultList();
	}

	private void buildLayout() {
		setContentView(R.layout.activity_results);
		resultList = (ListView) findViewById(R.id.resultList);
		resultList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Business business = (Business) parent.getAdapter().getItem(position);

				Intent i = new Intent(getApplicationContext(), DetailActivity.class);
				i.putExtra(Business.class.getName(), business);
				startActivity(i);
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
		pd.setMessage(getResources().getString(R.string.retrieving_results));
		pd.show();
	}

	private void populateResultList() {
		BusinessSearchQuery query = (BusinessSearchQuery) getIntent().getSerializableExtra(BusinessSearchQuery.class.getName());
		new FetchResultsTask().execute(query);
	}

	private class FetchResultsTask extends AsyncTask<BusinessSearchQuery, Void, BusinessSearchResult> {
		protected BusinessSearchResult doInBackground(BusinessSearchQuery... params) {
			try {
				return BusinessSearchServiceFactory.getInstance().findBusinesses(params[0]);
			} catch (Throwable t) {
				message.setText(getResources().getString(R.string.unexpected_error));
				return new BusinessSearchResult();
			}
		}

		protected void onPostExecute(BusinessSearchResult result) {
			int resultCount = result.getResultCount();
			if (resultCount == 0) {
				message.setText(getResources().getString(R.string.no_results_found));
			} else {
				message.setText(getResources().getString(R.string.x_results_found, resultCount));
			}
			BusinessAdapter adapter = new BusinessAdapter(getApplicationContext(), result.getFirstResults());
			resultList.setAdapter(adapter);
			pd.dismiss();
		}
	}

}
