package com.javastreets.mule.extension.firebase.internal.database;

import java.util.Map;

import org.mule.runtime.api.connection.ConnectionProvider;
import org.mule.runtime.api.exception.MuleException;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.metadata.MetadataScope;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.runtime.source.Source;
import org.mule.runtime.extension.api.runtime.source.SourceCallback;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.javastreets.mule.extension.firebase.api.FirebaseDatabaseAttributes;
import com.javastreets.mule.extension.firebase.internal.FirebaseConfiguration;
import com.javastreets.mule.extension.firebase.internal.FirebaseConnection;
import com.javastreets.mule.extension.firebase.internal.FirebaseMetadataResolver;
import com.javastreets.mule.extension.firebase.internal.database.listener.FirebaseDatabaseChildEventListener;

@DisplayName("Database: Child Listener")
@Alias("database-child-listener")
@MediaType(value = MediaType.ANY)
@MetadataScope(outputResolver=FirebaseMetadataResolver.class)
public class FirebaseDatabaseListener extends Source<Map<String, Object>, FirebaseDatabaseAttributes> {

	@Config
	private FirebaseConfiguration config;

	@Connection
	private ConnectionProvider<FirebaseConnection> firebaseProvider;

	@Parameter
	@DisplayName("Reference Path")
	private String refPath;
	
	private ChildEventListener eventListener;
	private DatabaseReference databaseReference;

	@Override
	public void onStart(SourceCallback<Map<String, Object>, FirebaseDatabaseAttributes> sourceCallback)
			throws MuleException {
		
		databaseReference = firebaseProvider.connect().getDatabase().getReference(refPath);
		eventListener = new FirebaseDatabaseChildEventListener(sourceCallback);
		
		databaseReference.addChildEventListener(eventListener);
	}
	
	@Override
	public void onStop() {
		databaseReference.removeEventListener(eventListener);
	}

}
