package com.quadient.connectors.evolve.internal.error.exception;

import com.quadient.connectors.evolve.internal.error.QuadientModuleErrorType;
import org.mule.sdk.api.exception.ModuleException;

@SuppressWarnings({"java:S110"})
public class TooManyRequestsException extends ModuleException {
    public TooManyRequestsException(Exception cause) {
        super(QuadientModuleErrorType.TOO_MANY_REQUESTS, cause);
    }
}