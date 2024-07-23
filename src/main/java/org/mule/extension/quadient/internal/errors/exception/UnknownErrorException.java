package org.mule.extension.quadient.internal.errors.exception;

import org.mule.extension.quadient.internal.errors.QuadientModuleErrors;
import org.mule.sdk.api.exception.ModuleException;

public class UnknownErrorException extends ModuleException {
    public UnknownErrorException(Exception cause) {
        super(QuadientModuleErrors.UNKNOWN_ERROR, cause);
    }
}