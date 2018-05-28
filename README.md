# Mule4 Firebase Connector

See https://javastreets.com/blog/mule4-firebase-connector-demo.html for connector overview.

To run the test and build - 

`mvn clean package -Dtest.service.path={path-to-service-key-json}/test-firebase-sdk-key.json -Dtest.database.url=https://{your-project-name}.firebaseio.com`

## Using Plugin in Mule 4 Project

Add below dependency in project -

```xml
		<dependency>
			<groupId>com.javastreets</groupId>
			<artifactId>mule-firebase-connector</artifactId>
			<version>0.0.2</version>
			<classifier>mule-plugin</classifier>
		</dependency>
```