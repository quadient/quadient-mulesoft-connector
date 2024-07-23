package org.mule.extension.quadient.internal.errors.exception;

import org.mule.extension.quadient.internal.errors.QuadientModuleErrors;
import org.mule.sdk.api.exception.ModuleException;

public class TooManyRequestsException extends ModuleException {
    public TooManyRequestsException(Exception cause) {
        super(QuadientModuleErrors.TOO_MANY_REQUESTS, cause);
    }
}