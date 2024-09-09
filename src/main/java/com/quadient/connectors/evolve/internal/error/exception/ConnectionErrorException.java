package com.quadient.connectors.evolve.internal.error.exception;

import com.quadient.connectors.evolve.internal.error.QuadientModuleErrorType;
import org.mule.sdk.api.exception.ModuleException;

@SuppressWarnings({"java:S110"})
public class ConnectionErrorException extends ModuleException {
    public ConnectionErrorException(Exception cause) {
        super(QuadientModuleErrorType.CONNECTION_ERROR, cause);
    }
}