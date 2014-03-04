package be.webfactor.openinghours.service;

import android.content.Context;
import be.webfactor.openinghours.service.impl.ErrorHandlerToastImpl;

public class ErrorHandlerFactory {

	public static ErrorHandler forContext(Context context) {
		return new ErrorHandlerToastImpl(context);
	}
	
}
