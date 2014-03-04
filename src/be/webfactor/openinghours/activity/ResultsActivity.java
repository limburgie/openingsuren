package be.webfactor.openinghours.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import be.webfactor.openinghours.R;
import be.webfactor.openinghours.adapter.BusinessAdapter;
import be.webfactor.openinghours.domain.Business;
import be.webfactor.openinghours.domain.BusinessSearchResult;
import be.webfactor.openinghours.service.BusinessSearchServiceFactory;
import be.webfactor.openinghours.service.ErrorHandlerFactory;

public class ResultsActivity extends Activity {

	private ProgressDialog pd;
	private ListView resultList;
	private TextView message;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		buildLayout();
		populateResultList();
	}

	private void populateResultList() {
		BusinessSearchResult result = (BusinessSearchResult) getIntent().getSerializableExtra(BusinessSearchResult.class.getName());
		
		int resultCount = result.getResultCount();
		message.setText(getResources().getString(R.string.x_results_found, resultCount));

		BusinessAdapter adapter = new BusinessAdapter(getApplicationContext(), result.getFirstResults());
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
			} catch (Throwable t) {
				Log.e(getClass().getName(), "Error while retrieving details", t);
				return null;
			}
		}

		protected void onPostExecute(Business business) {
			if (business == null) {
				ErrorHandlerFactory.forContext(getBaseContext()).error(R.string.unexpected_error);
				pd.dismiss();
				return;
			}
			
			Intent i = new Intent(getApplicationContext(), DetailActivity.class);
			i.putExtra(Business.class.getName(), business);
			startActivity(i);
			
			pd.dismiss();
		}
	}

}
