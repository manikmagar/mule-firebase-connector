package com.javastreets.mule.extension.firebase;

import java.util.Collections;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mule.functional.junit4.MuleArtifactFunctionalTestCase;
import org.mule.runtime.api.event.Event;

public class FirebaseOperationsTestCase extends MuleArtifactFunctionalTestCase {

	/**
	 * Specifies the mule config xml with the flows that are going to be executed in
	 * the tests, this file lives in the test resources.
	 */
	@Override
	protected String getConfigFile() {
		return "test-mule-config.xml";
	}

	@Test
	public void executeSetDataOperation() throws Exception {
		
		String uuid = UUID.randomUUID().toString();
		
		Event event = flowRunner("test-mule-config-set-data")
								.withPayload(uuid)
								.withVariable("refPath", "test-user/set-data-test")
								.run();
		
		String payload = (String)(flowRunner("test-mule-config-query-data")
				.withVariable("queryPath", "test-user/set-data-test")
				.run()
				.getMessage()
				.getPayload()
				.getValue());
	
		Assertions.assertThat(payload).isEqualTo(uuid);
		
	}
	
	@Test
	public void executePushDataOperation() throws Exception {
		
		String uuid = UUID.randomUUID().toString();
		
		
		String pushId = (String)(flowRunner("test-mule-config-push-data")
				.withPayload(uuid)
				.withVariable("refPath", "test-user/push-data-test")
				.run()
				.getMessage()
				.getPayload()
				.getValue());
		
		Assertions.assertThat(pushId).as("New Push Id").isNotNull();
		
		String payload = (String)(flowRunner("test-mule-config-query-data")
				.withVariable("queryPath", "test-user/push-data-test/" + pushId)
				.run()
				.getMessage()
				.getPayload()
				.getValue());
	
		Assertions.assertThat(payload).isEqualTo(uuid);
		
	}
	
	@Test
	public void executeUpdateOperation() throws Exception {
		
		String uuid = UUID.randomUUID().toString();
		
		
		String pushId = (String)(flowRunner("test-mule-config-push-data")
				.withPayload(uuid)
				.withVariable("refPath", "test-user/push-data-test")
				.run()
				.getMessage()
				.getPayload()
				.getValue());
		
		String uuid2 = UUID.randomUUID().toString();
		
		flowRunner("test-mule-config-update-data")
				.withPayload(Collections.singletonMap(pushId, uuid2))
				.withVariable("updateRef", "test-user/push-data-test")
				.run();
		
		Object payload = (flowRunner("test-mule-config-query-data")
				.withVariable("queryPath", "test-user/push-data-test/" + pushId)
				.run()
				.getMessage()
				.getPayload()
				.getValue());
	
		Assertions.assertThat(payload).isEqualTo(uuid2);
		
	}

}
