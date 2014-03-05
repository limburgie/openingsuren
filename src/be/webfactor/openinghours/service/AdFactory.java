package be.webfactor.openinghours.service;

import android.app.Activity;
import be.webfactor.openinghours.R;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class AdFactory {

	private Activity activity;
	
	public AdFactory(Activity activity) {
		this.activity = activity;
	}
	
	public void setup() {
		AdView adView = (AdView) activity.findViewById(R.id.ad_view);
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);	
	}
	
}
