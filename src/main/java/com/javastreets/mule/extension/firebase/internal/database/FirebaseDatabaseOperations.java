package com.javastreets.mule.extension.firebase.internal.database;

import java.util.Map;

import org.mule.runtime.extension.api.annotation.dsl.xml.ParameterDsl;
import org.mule.runtime.extension.api.annotation.error.Throws;
import org.mule.runtime.extension.api.annotation.metadata.OutputResolver;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.mule.runtime.extension.api.exception.ModuleException;
import org.mule.runtime.extension.api.runtime.process.CompletionCallback;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.javastreets.mule.extension.firebase.api.FirebaseDatabaseAttributes;
import com.javastreets.mule.extension.firebase.api.errors.FirebaseDatabaseErrorsProvider;
import com.javastreets.mule.extension.firebase.api.errors.FirebaseErrors;
import com.javastreets.mule.extension.firebase.internal.FirebaseConnection;
import com.javastreets.mule.extension.firebase.internal.FirebaseMetadataResolver;
import com.javastreets.mule.extension.firebase.internal.database.listener.FirebaseDatabaseValueEventListener;

/**
 * This class is a container for operations, every public method in this class
 * will be taken as an extension operation.
 */
@Throws(value = { FirebaseDatabaseErrorsProvider.class })
public class FirebaseDatabaseOperations {

	/**
	 * Set the data at this location to the given value. Passing null to setValue()
	 * will delete the data at the specified location. The native types accepted by
	 * this method for the value correspond to the JSON types:
	 *
	 * <ul>
	 * <li>Boolean
	 * <li>Long
	 * <li>Double
	 * <li>Map&lt;String, Object&gt;
	 * <li>List&lt;Object&gt;
	 * </ul>
	 * 
	 * Setting content to null will remove the key from firebase
	 * 
	 * @see DatabaseReference#setValue(Object,
	 *      com.google.firebase.database.DatabaseReference.CompletionListener)
	 * 
	 * @param refPath
	 *            Path to get database reference for.
	 * @param content
	 *            Content with data type specified as described above.
	 * @param firebaseConnection
	 *            Provided by SDK
	 */
	@DisplayName("Database: Set Data")
	@Summary("Sets (replace) the value of node identified by specified refPath")
	public void databaseSetData(@DisplayName("Reference Path") final String refPath,
			@DisplayName("Content") final Object content, @Connection FirebaseConnection firebaseConnection) {
		DatabaseReference ref = firebaseConnection.getDatabase().getReference(refPath);
		ref.setValue(content, (DatabaseError error, DatabaseReference reference) -> {
			if (error != null) {
				throw new ModuleException(FirebaseErrors.DATA_UPDATE_FAILED, error.toException());
			}

		});
	}

	/**
	 * Updates the specified key to the specified value in the map. Setting value to
	 * null in the map will remove the value.
	 * 
	 * @see DatabaseReference#updateChildren(Map,
	 *      com.google.firebase.database.DatabaseReference.CompletionListener)
	 * @param refPath Base Reference of the node containing keys
	 * @param content {@link Map}<String, Object> containing key-value pairs to be updated.
	 * @param firebaseConnection
	 */
	@DisplayName("Database: Update Data")
	@Summary("Update the value of node identified by specified refPath to include attributes specified as content")
	public void databaseUpdateData(@DisplayName("Reference Path") final String refPath,
			@DisplayName("Content") @ParameterDsl(allowReferences=false) final Map<String, Object> content,
			@Connection FirebaseConnection firebaseConnection) {
		DatabaseReference ref = firebaseConnection.getDatabase().getReference(refPath);
		ref.updateChildren(content, (DatabaseError error, DatabaseReference reference) -> {
			if (error != null) {
				throw new ModuleException(FirebaseErrors.DATA_UPDATE_FAILED, error.toException());
			}
		});
	}
	/**
	 * Adds a new key-value to the node specified by this reference. Auto-generated key is returned. 
	 * @param refPath Base path under which new node should be added.
	 * @param content
	 * @param firebaseConnection
	 * @return auto-generated key for new object
	 */
	@DisplayName("Database: Push Data")
	@Summary("Adds a new key-value to the node specified by this reference. Auto-generated key is returned. ")
	@MediaType(value="application/java")
	public String databasePushData(@DisplayName("Reference Path") final String refPath,
			@DisplayName("Content") final Object content,
			@Connection FirebaseConnection firebaseConnection) {
		DatabaseReference pushRef = firebaseConnection.getDatabase().getReference(refPath).push();
		
		pushRef.setValue(content, (DatabaseError error, DatabaseReference reference) -> {
			if (error != null) {
				throw new ModuleException(FirebaseErrors.DATA_UPDATE_FAILED, error.toException());
			}

		});
		return pushRef.getKey();
	}
	
	/**
	 * Adds a new key-value to the node specified by this reference. Auto-generated key is returned. 
	 * @param refPath Base path under which new node should be added.
	 * @param content
	 * @param firebaseConnection
	 */
	@DisplayName("Database: Query Data")
	@Summary("Queries Firebase Database for the data under specified node.")
	@OutputResolver(output = FirebaseMetadataResolver.class)
	public void databaseQueryData(@DisplayName("Reference Path") final String refPath,
			@Connection FirebaseConnection firebaseConnection, CompletionCallback<Object, FirebaseDatabaseAttributes> callback) {
		DatabaseReference dbRef = firebaseConnection.getDatabase().getReference(refPath);
		FirebaseDatabaseValueEventListener listener = new FirebaseDatabaseValueEventListener(callback);
		dbRef.addListenerForSingleValueEvent(listener);
	}
	
}
