package com.javastreets.mule.extension.firebase.api.errors;

import org.mule.runtime.extension.api.error.ErrorTypeDefinition;
import org.mule.runtime.extension.api.error.MuleErrors;

public enum FirebaseErrors implements ErrorTypeDefinition<MuleErrors> {
	DATA_UPDATE_FAILED,
	DATA_READ_FAILED
}
