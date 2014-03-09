package be.webfactor.openinghours.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import be.webfactor.openinghours.R;
import be.webfactor.openinghours.domain.Business;
import be.webfactor.openinghours.service.AdFactory;

public class DetailActivity extends Activity {

	private Business business;
	
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
		
		business = (Business) getIntent().getSerializableExtra(Business.class.getName());
		buildLayout();
		new AdFactory(this).setup();
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
		
		String lastReviewedLabel = business.getLastReviewedLabel();
		if (lastReviewedLabel == null) {
			message.setVisibility(View.GONE);
		} else {
			message.setText(lastReviewedLabel);
		}
		name.setText(business.getName());
		category.setText(business.getCategory());
		street.setText(business.getStreet());
		city.setText(business.getCity());
		if (business.getPhone() == null) {
			phone.setVisibility(View.GONE);
		} else {
			phone.setText(getResources().getString(R.string.tel_prefix, business.getPhone()));
		}
		if (business.getFax() == null) {
			fax.setVisibility(View.GONE);
		} else {
			fax.setText(getResources().getString(R.string.fax_prefix, business.getFax()));
		}
		mondayAm.setText(business.getMonday().getAm());
		mondayPm.setText(business.getMonday().getPm());
		tuesdayAm.setText(business.getTuesday().getAm());
		tuesdayPm.setText(business.getTuesday().getPm());
		wednesdayAm.setText(business.getWednesday().getAm());
		wednesdayPm.setText(business.getWednesday().getPm());
		thursdayAm.setText(business.getThursday().getAm());
		thursdayPm.setText(business.getThursday().getPm());
		fridayAm.setText(business.getFriday().getAm());
		fridayPm.setText(business.getFriday().getPm());
		saturdayAm.setText(business.getSaturday().getAm());
		saturdayPm.setText(business.getSaturday().getPm());
		sundayAm.setText(business.getSunday().getAm());
		sundayPm.setText(business.getSunday().getPm());
		holidayAm.setText(business.getHoliday().getAm());
		holidayPm.setText(business.getHoliday().getPm());
	}
	
	public void callPhoneNumber(View view) {
		Intent intent = new Intent(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:"+business.getPhone()));
		startActivity(intent);
	}

}
