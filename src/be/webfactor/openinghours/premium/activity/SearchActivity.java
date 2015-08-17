package be.webfactor.openinghours.premium.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import be.webfactor.openinghours.premium.R;
import be.webfactor.openinghours.premium.domain.BusinessSearchQuery;
import be.webfactor.openinghours.premium.domain.BusinessSearchResult;
import be.webfactor.openinghours.premium.service.BusinessSearchServiceFactory;
import be.webfactor.openinghours.premium.service.ConnectionException;
import be.webfactor.openinghours.premium.service.ErrorHandler;
import be.webfactor.openinghours.premium.service.ErrorHandlerFactory;

public class SearchActivity extends Activity {

	private ProgressDialog pd;
	private EditText whatInput;
	private EditText whereInput;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		buildLayout();
	}
	
	protected void onDestroy() {
		super.onDestroy();
		if (pd != null) {
			pd.dismiss();
		}
	}

	private void buildLayout() {
		setContentView(R.layout.activity_search);
		
		whatInput = (EditText) findViewById(R.id.what);
		whereInput = (EditText) findViewById(R.id.where);
	}
	
	public void search(View view) {
		initializeProgressBar();

		String what = whatInput.getText().toString();
		String where = whereInput.getText().toString();
		BusinessSearchQuery query = new BusinessSearchQuery(what, where);
		
		new FetchResultsTask().execute(query);
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
	
	private class FetchResultsTask extends AsyncTask<BusinessSearchQuery, Void, BusinessSearchResult> {
		protected BusinessSearchResult doInBackground(BusinessSearchQuery... params) {
			try {
				return BusinessSearchServiceFactory.getInstance().findBusinesses(params[0]);
			} catch (ConnectionException e) {
				return null;
			}
		}
		
		protected void onPostExecute(BusinessSearchResult result) {
			ErrorHandler handler = ErrorHandlerFactory.forContext(getBaseContext());

			if (result == null) {
				handler.error(R.string.check_internet_connection);
				pd.dismiss();
				return;
			}

			if (result.getResultCount() == 0) {
				handler.error(R.string.no_results_found);
				pd.dismiss();
				return;
			}
			
			Intent i = new Intent(getApplicationContext(), ResultsActivity.class);
			i.putExtra(BusinessSearchResult.class.getName(), result);
			startActivity(i);
			pd.dismiss();
		}
	}

}
