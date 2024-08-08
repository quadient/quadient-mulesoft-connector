package org.mule.extension.quadient.internal.extension;

import org.mule.extension.quadient.internal.config.Configuration;
import org.mule.extension.quadient.internal.error.QuadientModuleErrorType;
import org.mule.extension.quadient.api.proxy.DefaultProxyConfig;
import org.mule.runtime.api.meta.Category;
import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.Configurations;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;
import org.mule.runtime.http.api.client.proxy.ProxyConfig;
import org.mule.sdk.api.annotation.JavaVersionSupport;
import org.mule.sdk.api.annotation.SubTypeMapping;
import org.mule.sdk.api.annotation.error.ErrorTypes;

import static org.mule.sdk.api.meta.JavaVersion.*;


/**
 * This is the main class of an extension, is the entry point from which configurations, connection providers, operations
 * and sources are going to be declared.
 */
@Xml(prefix = "quadient")
@Extension(name = "Quadient Evolve", category = Category.CERTIFIED)
@ErrorTypes(QuadientModuleErrorType.class)
@Configurations(Configuration.class)
@JavaVersionSupport({JAVA_8, JAVA_11, JAVA_17})
@SubTypeMapping(baseType = ProxyConfig.class, subTypes = {DefaultProxyConfig.class})
public class QuadientConnector {

}
