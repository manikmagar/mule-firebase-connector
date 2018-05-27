package com.javastreets.mule.extension.firebase.internal;

import java.io.IOException;

import org.mule.runtime.api.connection.CachedConnectionProvider;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.connection.ConnectionProvider;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.connection.PoolingConnectionProvider;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class (as it's name implies) provides connection instances and the funcionality to disconnect and validate those
 * connections.
 * <p>
 * All connection related parameters (values required in order to create a connection) must be
 * declared in the connection providers.
 * <p>
 * This particular example is a {@link PoolingConnectionProvider} which declares that connections resolved by this provider
 * will be pooled and reused. There are other implementations like {@link CachedConnectionProvider} which lazily creates and
 * caches connections or simply {@link ConnectionProvider} if you want a new connection each time something requires one.
 */
public class FirebaseConnectionProvider implements PoolingConnectionProvider<FirebaseConnection> {

  private final Logger LOGGER = LoggerFactory.getLogger(FirebaseConnectionProvider.class);

 /**
  * A parameter that is always required to be configured.
  */
  @Parameter
  @DisplayName("Service Account Key File")
  private String serviceAccoutKeyPath;
  
  @Parameter
  @DisplayName("Firebase Database URL")
  private String databaseUrl; 
  
  @Parameter
  @DisplayName("Firebase Application Name")
  @Optional(defaultValue="DEFAULT")
  @Summary("Each Configuration must use an unique application name. Default application name is 'DEFAULT'")
  private String appName;
 
  @Override
  public FirebaseConnection connect() throws ConnectionException {
    try {
		return new FirebaseConnection(this.databaseUrl, this.serviceAccoutKeyPath, this.appName);
	} catch (IOException e) {
		throw new ConnectionException(e);
	}
  }

  @Override
  public void disconnect(FirebaseConnection connection) {
    try {
      connection.invalidate();
    } catch (Exception e) {
      LOGGER.error("Error while disconnecting [" + connection.getId() + "]: " + e.getMessage(), e);
    }
  }

  @Override
  public ConnectionValidationResult validate(FirebaseConnection connection) {
    return ConnectionValidationResult.success();
  }
}
