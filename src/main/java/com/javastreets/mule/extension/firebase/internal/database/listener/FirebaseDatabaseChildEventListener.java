package com.javastreets.mule.extension.firebase.internal.database.listener;

import java.util.HashMap;
import java.util.Map;

import org.mule.runtime.extension.api.exception.ModuleException;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.mule.runtime.extension.api.runtime.source.SourceCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.javastreets.mule.extension.firebase.api.FirebaseDatabaseAttributes;
import com.javastreets.mule.extension.firebase.api.errors.FirebaseErrors;

public class FirebaseDatabaseChildEventListener implements ChildEventListener {
	
	private static final String KEY = "key";

	private static final String VALUE = "value";

	private final Logger LOG = LoggerFactory.getLogger(FirebaseDatabaseChildEventListener.class);
	
	private SourceCallback<Map<String, Object>, FirebaseDatabaseAttributes> sourceCallback;
	
	public FirebaseDatabaseChildEventListener(SourceCallback<Map<String, Object>, FirebaseDatabaseAttributes> sourceCallback) {
		this.sourceCallback = sourceCallback;
	}
	
	@Override
	public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
		
		Map<String, Object> payload = new HashMap<>();
		payload.put(KEY, dataSnapshot.getKey());
		payload.put(VALUE, dataSnapshot.getValue());
		payload.put("previousKey", prevChildKey);

		FirebaseDatabaseAttributes attributes = new FirebaseDatabaseAttributes(
				dataSnapshot.getRef().getPath().toString(), dataSnapshot.hasChildren(), dataSnapshot.exists(), FirebaseDatabaseAttributes.EVENT_TYPE.CHILD_ADDED);

		Result<Map<String, Object>, FirebaseDatabaseAttributes> result = Result.<Map<String, Object>, FirebaseDatabaseAttributes>builder()
																				.output(payload)
																				.attributes(attributes)
																				.build();
		sourceCallback.handle(result);
	}

	@Override
	public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
		Map<String, Object> payload = new HashMap<>();
		payload.put(KEY, snapshot.getKey());
		payload.put(VALUE, snapshot.getValue());
		payload.put("previousChildName", previousChildName);

		FirebaseDatabaseAttributes attributes = new FirebaseDatabaseAttributes(
				snapshot.getRef().getPath().toString(), snapshot.hasChildren(), snapshot.exists(),FirebaseDatabaseAttributes.EVENT_TYPE.CHILD_CHANGED);

		Result<Map<String, Object>, FirebaseDatabaseAttributes> result = Result.<Map<String, Object>, FirebaseDatabaseAttributes>builder()
																				.output(payload)
																				.attributes(attributes)
																				.build();
		sourceCallback.handle(result);

	}

	@Override
	public void onChildRemoved(DataSnapshot snapshot) {
		Map<String, Object> payload = new HashMap<>();
		payload.put(KEY, snapshot.getKey());
		payload.put(VALUE, snapshot.getValue());

		FirebaseDatabaseAttributes attributes = new FirebaseDatabaseAttributes(
				snapshot.getRef().getPath().toString(), snapshot.hasChildren(), snapshot.exists(),FirebaseDatabaseAttributes.EVENT_TYPE.CHILD_REMOVED);

		Result<Map<String, Object>, FirebaseDatabaseAttributes> result = Result.<Map<String, Object>, FirebaseDatabaseAttributes>builder()
																				.output(payload)
																				.attributes(attributes)
																				.build();
		sourceCallback.handle(result);
	}

	@Override
	public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
		Map<String, Object> payload = new HashMap<>();
		payload.put(KEY, snapshot.getKey());
		payload.put(VALUE, snapshot.getValue());
		payload.put("previousChildName", previousChildName);

		FirebaseDatabaseAttributes attributes = new FirebaseDatabaseAttributes(
				snapshot.getRef().getPath().toString(), snapshot.hasChildren(), snapshot.exists(),FirebaseDatabaseAttributes.EVENT_TYPE.CHILD_MOVED);

		Result<Map<String, Object>, FirebaseDatabaseAttributes> result = Result.<Map<String, Object>, FirebaseDatabaseAttributes>builder()
																				.output(payload)
																				.attributes(attributes)
																				.build();
		sourceCallback.handle(result);
	}

	@Override
	public void onCancelled(DatabaseError error) {
		throw new ModuleException(FirebaseErrors.DATA_READ_FAILED, error.toException());
	}

}
