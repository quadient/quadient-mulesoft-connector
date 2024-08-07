package org.mule.extension.quadient.internal.error.exception;

import org.mule.extension.quadient.internal.error.QuadientModuleErrorType;
import org.mule.sdk.api.exception.ModuleException;

public class TooManyRequestsException extends ModuleException {
    public TooManyRequestsException(Exception cause) {
        super(QuadientModuleErrorType.TOO_MANY_REQUESTS, cause);
    }
}