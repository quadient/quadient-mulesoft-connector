package com.quadient.connectors.evolve.internal.error.exception;

import com.quadient.connectors.evolve.internal.error.QuadientModuleErrorType;
import org.mule.sdk.api.exception.ModuleException;

@SuppressWarnings({"java:S110"})
public class RequestSerializationException extends ModuleException {
    public RequestSerializationException(Exception cause) {
        super(QuadientModuleErrorType.REQUEST_SERIALIZATION_ERROR, cause);
    }
}