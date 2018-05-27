package com.javastreets.mule.extension.firebase.api;

public class FirebaseDatabaseAttributes extends FirebaseAttributes {
	
	private static final String FIREBASE_DATABASE = "DATABASE";
	private String referencePath;
	private boolean hasChildren;
	private boolean hasValue;
	private EVENT_TYPE eventType;

	public FirebaseDatabaseAttributes(String referencePath, boolean hasChildren, boolean hasValue, EVENT_TYPE eventType) {
		this.referencePath = referencePath;
		this.hasChildren = hasChildren;
		this.hasValue = hasValue;
		this.eventType = eventType;
		super.FIREBASE_MODULE = FirebaseDatabaseAttributes.FIREBASE_DATABASE;
	}
	public String getReferencePath() {
		return referencePath;
	}
	public boolean hasChildren() {
		return hasChildren;
	}
	public boolean hasValue() {
		return hasValue;
	}
	public EVENT_TYPE getEventType() {
		return eventType;
	}
	
	public enum EVENT_TYPE {
		VALUE,
		CHILD_ADDED,
		CHILD_CHANGED,
		CHILD_MOVED,
		CHILD_REMOVED
	}
}
