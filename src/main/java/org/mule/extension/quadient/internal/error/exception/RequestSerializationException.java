package org.mule.extension.quadient.internal.error.exception;

import org.mule.extension.quadient.internal.error.QuadientModuleErrorType;
import org.mule.sdk.api.exception.ModuleException;

public class RequestSerializationException extends ModuleException {
    public RequestSerializationException(Exception cause) {
        super(QuadientModuleErrorType.REQUEST_SERIALIZATION_ERROR, cause);
    }
}