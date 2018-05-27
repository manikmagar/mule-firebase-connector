package com.javastreets.mule.extension.firebase.internal;

import org.mule.metadata.api.builder.BaseTypeBuilder;
import org.mule.metadata.api.model.AnyType;
import org.mule.metadata.api.model.MetadataFormat;
import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.metadata.resolving.OutputStaticTypeResolver;

public class FirebaseMetadataResolver extends OutputStaticTypeResolver {
	private static final AnyType ANY_TYPE = BaseTypeBuilder.create(MetadataFormat.JAVA).anyType().build();

	@Override
	public String getCategoryName() {
		return "Firebase";
	}

	@Override
	public MetadataType getStaticMetadata() {
		return ANY_TYPE;
	}
}
