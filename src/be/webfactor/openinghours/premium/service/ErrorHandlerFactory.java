package be.webfactor.openinghours.premium.service;

import android.content.Context;
import be.webfactor.openinghours.premium.service.impl.ErrorHandlerToastImpl;

public class ErrorHandlerFactory {

	public static ErrorHandler forContext(Context context) {
		return new ErrorHandlerToastImpl(context);
	}
	
}
