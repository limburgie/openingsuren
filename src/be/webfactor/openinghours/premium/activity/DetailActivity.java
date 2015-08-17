package be.webfactor.openinghours.premium.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import be.webfactor.openinghours.premium.R;
import be.webfactor.openinghours.premium.domain.Business;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class DetailActivity extends Activity {

	private Business business;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		business = (Business) getIntent().getSerializableExtra(Business.class.getName());
		buildLayout();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.detail, menu);
		if (business.getPhone() == null) {
			menu.removeItem(R.id.action_call);
			menu.removeItem(R.id.action_add_to_contacts);
		}
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_call:
				callPhoneNumber();
				return true;
			case R.id.action_add_to_contacts:
				addToContacts();
				return true;
			case R.id.action_navigate:
				openGoogleMaps();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void callPhoneNumber() {
		Intent intent = new Intent(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:" + business.getPhone()));
		startActivity(intent);
	}

	private void addToContacts() {
		Intent i = new Intent(ContactsContract.Intents.Insert.ACTION);
		i.setData(ContactsContract.Contacts.CONTENT_URI);
		i.putExtra(ContactsContract.Intents.Insert.NAME, business.getName());
		i.putExtra(ContactsContract.Intents.Insert.PHONE, business.getPhone());
		i.putExtra(ContactsContract.Intents.Insert.POSTAL, business.getAddress());
		startActivity(i);
	}

	private void openGoogleMaps() {
		try {
			String uri = String.format("geo:0,0?q=%s", URLEncoder.encode(business.getAddress(), "UTF-8"));
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
			startActivity(intent);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	private void buildLayout() {
		setContentView(R.layout.activity_detail);

		TextView name = (TextView) findViewById(R.id.name);
		TextView category = (TextView) findViewById(R.id.category);
		TextView street = (TextView) findViewById(R.id.street);
		TextView city = (TextView) findViewById(R.id.city);
		TextView mondayAm = (TextView) findViewById(R.id.monday_am);
		TextView mondayPm = (TextView) findViewById(R.id.monday_pm);
		TextView tuesdayAm = (TextView) findViewById(R.id.tuesday_am);
		TextView tuesdayPm = (TextView) findViewById(R.id.tuesday_pm);
		TextView wednesdayAm = (TextView) findViewById(R.id.wednesday_am);
		TextView wednesdayPm = (TextView) findViewById(R.id.wednesday_pm);
		TextView thursdayAm = (TextView) findViewById(R.id.thursday_am);
		TextView thursdayPm = (TextView) findViewById(R.id.thursday_pm);
		TextView fridayAm = (TextView) findViewById(R.id.friday_am);
		TextView fridayPm = (TextView) findViewById(R.id.friday_pm);
		TextView saturdayAm = (TextView) findViewById(R.id.saturday_am);
		TextView saturdayPm = (TextView) findViewById(R.id.saturday_pm);
		TextView sundayAm = (TextView) findViewById(R.id.sunday_am);
		TextView sundayPm = (TextView) findViewById(R.id.sunday_pm);
		TextView holidayAm = (TextView) findViewById(R.id.holiday_am);
		TextView holidayPm = (TextView) findViewById(R.id.holiday_pm);
		TextView lastUpdated = (TextView) findViewById(R.id.last_updated);

		name.setText(business.getName());
		category.setText(business.getCategory());
		street.setText(business.getStreet());
		city.setText(business.getCity());
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
		lastUpdated.setText(getLastReviewed());
	}

	private String getLastReviewed() {
		String lastUpdateAgo = business.getLastReviewedLabel();
		String lastUpdate = getResources().getString(R.string.last_update);
		return String.format("%s: %s", lastUpdate, lastUpdateAgo);
	}

}
