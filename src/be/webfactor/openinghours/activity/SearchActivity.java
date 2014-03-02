package be.webfactor.openinghours.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import be.webfactor.openinghours.R;
import be.webfactor.openinghours.domain.BusinessSearchQuery;

public class SearchActivity extends Activity {

	private EditText whatInput;
	private EditText whereInput;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		buildLayout();
	}

	private void buildLayout() {
		setContentView(R.layout.activity_search);
		
		whatInput = (EditText) findViewById(R.id.what);
		whereInput = (EditText) findViewById(R.id.where);
	}
	
	public void search(View view) {
		String what = whatInput.getText().toString();
		String where = whereInput.getText().toString();
		
		Intent i = new Intent(getApplicationContext(), ResultsActivity.class);
		i.putExtra(BusinessSearchQuery.class.getName(), new BusinessSearchQuery(what, where));
		
		final ProgressDialog progress = new ProgressDialog(this);
		progress.setTitle("Loading");
		progress.setMessage("Wait while loading...");
		progress.setIndeterminate(true);
		progress.show();
		
		startActivity(i);
		
		progress.hide();
	}

}
