package com.javastreets.mule.extension.firebase.api;

import java.io.Serializable;

public class FirebaseAttributes implements Serializable {

	protected String FIREBASE_MODULE = "";
	
	public String moduleName() {
		return FIREBASE_MODULE;
	}
	

}
