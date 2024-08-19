package com.quadient.connectors.evolve.internal.error.exception;

import com.quadient.connectors.evolve.internal.error.QuadientModuleErrorType;
import org.mule.sdk.api.exception.ModuleException;

public class UnauthorizedException extends ModuleException {
    public UnauthorizedException(Exception cause) {
        super(QuadientModuleErrorType.UNAUTHORIZED, cause);
    }
}