package com.javastreets.mule.extension.firebase.internal;

import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.Sources;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.param.Parameter;

import com.javastreets.mule.extension.firebase.internal.database.FirebaseDatabaseOperations;
import com.javastreets.mule.extension.firebase.internal.database.FirebaseDatabaseListener;

/**
 * This class represents an extension configuration, values set in this class are commonly used across multiple
 * operations since they represent something core from the extension.
 */
@Operations(FirebaseDatabaseOperations.class)
@Sources(FirebaseDatabaseListener.class)
@ConnectionProviders(FirebaseConnectionProvider.class)
public class FirebaseConfiguration {


	  @Parameter
	  private String configId;

	  public String getConfigId(){
	    return configId;
	  }
}
