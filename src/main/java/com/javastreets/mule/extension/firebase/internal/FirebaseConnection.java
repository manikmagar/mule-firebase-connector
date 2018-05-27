package com.javastreets.mule.extension.firebase.internal;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;

/**
 * This class represents an extension connection just as example (there is no
 * real connection with anything here c:).
 */
public final class FirebaseConnection {

	private final String id;

	private final String serviceKeyPath;

	private FirebaseApp firebaseApp;
	private FirebaseDatabase firebaseDatabase;

	private final String appName;

	public FirebaseConnection(String databaseUrl, String serviceKeyPath, String appName) throws IOException {
		this.id = databaseUrl;
		this.serviceKeyPath = serviceKeyPath;
		this.appName = appName;
		initialize();
	}

	public String getId() {
		return id;
	}

	private void initialize() throws IOException {
		
		Optional<FirebaseApp> fApp = (FirebaseApp.getApps().stream().filter(app -> app.getName().equals(this.appName)).findFirst());
		if(fApp.isPresent()) {
			firebaseApp = fApp.get();
		} else {
			FileInputStream serviceAccount = new FileInputStream(this.serviceKeyPath);
			
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount)).setDatabaseUrl(this.getId()).build();

			firebaseApp = FirebaseApp.initializeApp(options, this.appName);
		}
		
		firebaseDatabase = FirebaseDatabase.getInstance(firebaseApp);
	}

	public FirebaseDatabase getDatabase() {
		return firebaseDatabase;
	}

	public void invalidate() {
		firebaseApp = null;
	}
}
