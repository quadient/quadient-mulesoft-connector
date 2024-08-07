package org.mule.extension.quadient.internal.error.exception;

import org.mule.extension.quadient.internal.error.QuadientModuleErrorType;
import org.mule.sdk.api.exception.ModuleException;

public class UnauthorizedException extends ModuleException {
    public UnauthorizedException(Exception cause) {
        super(QuadientModuleErrorType.UNAUTHORIZED, cause);
    }
}