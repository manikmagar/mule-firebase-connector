package com.javastreets.mule.extension.firebase.internal.database;

import org.mule.runtime.extension.api.annotation.Configurations;
import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;
import org.mule.runtime.extension.api.annotation.error.ErrorTypes;

import com.javastreets.mule.extension.firebase.api.errors.FirebaseErrors;
import com.javastreets.mule.extension.firebase.internal.FirebaseConfiguration;


/**
 * This is the main class of an extension, is the entry point from which configurations, connection providers, operations
 * and sources are going to be declared.
 */
@Xml(prefix = "firebase")
@Extension(name = "Firebase")
@ErrorTypes(FirebaseErrors.class)
@Configurations(FirebaseConfiguration.class)

public class FirebaseDatabaseExtension {
	
}
