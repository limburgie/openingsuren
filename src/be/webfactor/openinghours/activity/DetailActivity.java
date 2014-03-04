package be.webfactor.openinghours.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import be.webfactor.openinghours.R;
import be.webfactor.openinghours.domain.Business;
import be.webfactor.openinghours.service.BusinessSearchServiceFactory;

public class DetailActivity extends Activity {

	private ProgressDialog pd;
	private TextView message;
	private TextView name;
	private TextView category;
	private TextView street;
	private TextView city;
	private TextView phone;
	private TextView fax;
	private TextView mondayAm;
	private TextView mondayPm;
	private TextView tuesdayAm;
	private TextView tuesdayPm;
	private TextView wednesdayAm;
	private TextView wednesdayPm;
	private TextView thursdayAm;
	private TextView thursdayPm;
	private TextView fridayAm;
	private TextView fridayPm;
	private TextView saturdayAm;
	private TextView saturdayPm;
	private TextView sundayAm;
	private TextView sundayPm;
	private TextView holidayAm;
	private TextView holidayPm;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		buildLayout();
		retrieveBusinessDetail();
	}

	private void buildLayout() {
		setContentView(R.layout.activity_detail);

		message = (TextView) findViewById(R.id.message);
		name = (TextView) findViewById(R.id.name);
		category = (TextView) findViewById(R.id.category);
		street = (TextView) findViewById(R.id.street);
		city = (TextView) findViewById(R.id.city);
		phone = (TextView) findViewById(R.id.phone);
		fax = (TextView) findViewById(R.id.fax);
		mondayAm = (TextView) findViewById(R.id.monday_am);
		mondayPm = (TextView) findViewById(R.id.monday_pm);
		tuesdayAm = (TextView) findViewById(R.id.tuesday_am);
		tuesdayPm = (TextView) findViewById(R.id.tuesday_pm);
		wednesdayAm = (TextView) findViewById(R.id.wednesday_am);
		wednesdayPm = (TextView) findViewById(R.id.wednesday_pm);
		thursdayAm = (TextView) findViewById(R.id.thursday_am);
		thursdayPm = (TextView) findViewById(R.id.thursday_pm);
		fridayAm = (TextView) findViewById(R.id.friday_am);
		fridayPm = (TextView) findViewById(R.id.friday_pm);
		saturdayAm = (TextView) findViewById(R.id.saturday_am);
		saturdayPm = (TextView) findViewById(R.id.saturday_pm);
		sundayAm = (TextView) findViewById(R.id.sunday_am);
		sundayPm = (TextView) findViewById(R.id.sunday_pm);
		holidayAm = (TextView) findViewById(R.id.holiday_am);
		holidayPm = (TextView) findViewById(R.id.holiday_pm);

		initializeProgressBar();
	}

	private void retrieveBusinessDetail() {
		Business business = (Business) getIntent().getSerializableExtra(Business.class.getName());
		new FetchDetailTask().execute(business);
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

		protected void onPostExecute(Business result) {
			pd.dismiss();
			if (result == null) {
				message.setVisibility(View.VISIBLE);
				message.setText(getResources().getString(R.string.unexpected_error));
				return;
			}
			name.setText(result.getName());
			category.setText(result.getCategory());
			street.setText(result.getStreet());
			city.setText(result.getCity());
			if (result.getPhone() == null) {
				phone.setVisibility(View.GONE);
			} else {
				phone.setText(result.getPhone());
			}
			if (result.getFax() == null) {
				fax.setVisibility(View.GONE);
			} else {
				fax.setText(result.getFax());
			}
			mondayAm.setText(result.getMonday().getAm());
			mondayPm.setText(result.getMonday().getPm());
			tuesdayAm.setText(result.getTuesday().getAm());
			tuesdayPm.setText(result.getTuesday().getPm());
			wednesdayAm.setText(result.getWednesday().getAm());
			wednesdayPm.setText(result.getWednesday().getPm());
			thursdayAm.setText(result.getThursday().getAm());
			thursdayPm.setText(result.getThursday().getPm());
			fridayAm.setText(result.getFriday().getAm());
			fridayPm.setText(result.getFriday().getPm());
			saturdayAm.setText(result.getSaturday().getAm());
			saturdayPm.setText(result.getSaturday().getPm());
			sundayAm.setText(result.getSunday().getAm());
			sundayPm.setText(result.getSunday().getPm());
			holidayAm.setText(result.getHoliday().getAm());
			holidayPm.setText(result.getHoliday().getPm());
		}
	}

}
