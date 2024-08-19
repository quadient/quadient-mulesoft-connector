package com.quadient.connectors.evolve.internal.error.exception;

import com.quadient.connectors.evolve.internal.error.QuadientModuleErrorType;
import org.mule.sdk.api.exception.ModuleException;

public class ConnectionErrorException extends ModuleException {
    public ConnectionErrorException(Exception cause) {
        super(QuadientModuleErrorType.CONNECTION_ERROR, cause);
    }
}