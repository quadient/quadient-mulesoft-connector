package org.mule.extension.quadient.internal.errors.exception;

import org.mule.extension.quadient.internal.errors.QuadientModuleErrors;
import org.mule.sdk.api.exception.ModuleException;

public class UnauthorizedException extends ModuleException {
    public UnauthorizedException(Exception cause) {
        super(QuadientModuleErrors.UNAUTHORIZED, cause);
    }
}