package be.webfactor.openinghours.service.impl;

import android.content.Context;
import android.widget.Toast;
import be.webfactor.openinghours.service.ErrorHandler;

public class ErrorHandlerToastImpl implements ErrorHandler {

	private Context context;
	
	public ErrorHandlerToastImpl(Context context) {
		this.context = context;
	}
	
	public void error(int message) {
		Toast.makeText(context, context.getResources().getString(message), Toast.LENGTH_LONG).show();
	}

}
