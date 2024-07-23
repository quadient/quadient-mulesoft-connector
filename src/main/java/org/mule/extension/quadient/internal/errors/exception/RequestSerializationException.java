package org.mule.extension.quadient.internal.errors.exception;

import org.mule.extension.quadient.internal.errors.QuadientModuleErrors;
import org.mule.sdk.api.exception.ModuleException;

public class RequestSerializationException extends ModuleException {
    public RequestSerializationException(Exception cause) {
        super(QuadientModuleErrors.REQUEST_SERIALIZATION_ERROR, cause);
    }
}