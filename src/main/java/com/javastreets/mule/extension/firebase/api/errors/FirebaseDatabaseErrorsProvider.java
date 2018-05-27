package com.javastreets.mule.extension.firebase.api.errors;

import java.util.HashSet;
import java.util.Set;

import org.mule.runtime.extension.api.annotation.error.ErrorTypeProvider;
import org.mule.runtime.extension.api.error.ErrorTypeDefinition;

public class FirebaseDatabaseErrorsProvider implements ErrorTypeProvider {

	@Override
	public Set<ErrorTypeDefinition> getErrorTypes() {
		HashSet<ErrorTypeDefinition> errors = new HashSet<>();
        errors.add(FirebaseErrors.DATA_UPDATE_FAILED);
        errors.add(FirebaseErrors.DATA_READ_FAILED);
        return errors;
	}

}
