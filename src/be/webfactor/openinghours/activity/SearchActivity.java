package be.webfactor.openinghours.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import be.webfactor.openinghours.R;
import be.webfactor.openinghours.service.BusinessSearchService;
import be.webfactor.openinghours.service.impl.BusinessSearchServiceOpeningsurenComImpl;

public class SearchActivity extends Activity {

	private EditText whatInput;
	private EditText whereInput;
	
	private BusinessSearchService searchService;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		buildLayout();
		
		searchService = new BusinessSearchServiceOpeningsurenComImpl();
	}

	private void buildLayout() {
		setContentView(R.layout.activity_search);
		
		whatInput = (EditText) findViewById(R.id.what);
		whereInput = (EditText) findViewById(R.id.where);
	}
	
	public void search(View view) {
		String what = whatInput.getText().toString();
		String where = whereInput.getText().toString();
		
		Toast.makeText(getApplicationContext(), what + " - " + where, Toast.LENGTH_LONG).show();
	}

}
