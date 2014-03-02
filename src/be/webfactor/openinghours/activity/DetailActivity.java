package be.webfactor.openinghours.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import be.webfactor.openinghours.R;
import be.webfactor.openinghours.domain.Business;
import be.webfactor.openinghours.service.BusinessSearchServiceFactory;

public class DetailActivity extends Activity {

	private ProgressDialog pd;
	private TextView name;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		buildLayout();
		retrieveBusinessDetail();
	}

	private void buildLayout() {
		setContentView(R.layout.activity_detail);
		name = (TextView) findViewById(R.id.name);
		initializeProgressBar();
	}
	
	private void retrieveBusinessDetail() {
		Business business = (Business) getIntent().getSerializableExtra(Business.class.getName());
		new FetchDetailTask().execute(business);
	}
	
	@SuppressLint("InlinedApi")
	private void initializeProgressBar() {
		if(Build.VERSION.SDK_INT >= 11) {
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
			return BusinessSearchServiceFactory.getInstance().getDetail(params[0]);
		}
		
		protected void onPostExecute(Business result) {
			name.setText(result.getName());
			pd.dismiss();
		}
	}
	
}
