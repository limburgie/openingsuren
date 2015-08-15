package be.webfactor.openinghours.premium.service.impl;

import android.content.Context;
import android.widget.Toast;
import be.webfactor.openinghours.premium.service.ErrorHandler;

public class ErrorHandlerToastImpl implements ErrorHandler {

	private Context context;
	
	public ErrorHandlerToastImpl(Context context) {
		this.context = context;
	}
	
	public void error(int message, Object... params) {
		Toast.makeText(context, context.getResources().getString(message, params), Toast.LENGTH_LONG).show();
	}

}
