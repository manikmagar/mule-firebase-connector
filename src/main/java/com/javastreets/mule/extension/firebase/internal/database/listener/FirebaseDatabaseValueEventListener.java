package com.javastreets.mule.extension.firebase.internal.database.listener;

import org.mule.runtime.extension.api.exception.ModuleException;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.mule.runtime.extension.api.runtime.process.CompletionCallback;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.javastreets.mule.extension.firebase.api.FirebaseDatabaseAttributes;
import com.javastreets.mule.extension.firebase.api.errors.FirebaseErrors;

/**
 * This implements {@link ValueEventListener} with {@link CompletionCallback} to allow non-blocking operation.
 * @author manik
 *
 */
public class FirebaseDatabaseValueEventListener implements ValueEventListener {

	private CompletionCallback<Object, FirebaseDatabaseAttributes> callback;
	
	public FirebaseDatabaseValueEventListener(CompletionCallback<Object, FirebaseDatabaseAttributes> callback) {
		super();
		this.callback = callback;
	}

	@Override
	public void onDataChange(DataSnapshot snapshot) {
		final Object value = snapshot.getValue();
		FirebaseDatabaseAttributes attributes = new FirebaseDatabaseAttributes(
				snapshot.getRef().getPath().toString(), snapshot.hasChildren(), snapshot.exists(), FirebaseDatabaseAttributes.EVENT_TYPE.VALUE);

		Result<Object, FirebaseDatabaseAttributes> result = Result.<Object, FirebaseDatabaseAttributes>builder()
																				.output(value)
																				.attributes(attributes)
																				.build();
		callback.success(result);
	}
	

	@Override
	public void onCancelled(DatabaseError error) {
		callback.error(new ModuleException(FirebaseErrors.DATA_READ_FAILED, error.toException()));
	}

}
