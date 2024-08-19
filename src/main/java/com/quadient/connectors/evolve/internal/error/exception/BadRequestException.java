package com.quadient.connectors.evolve.internal.error.exception;

import com.quadient.connectors.evolve.internal.error.QuadientModuleErrorType;
import org.mule.sdk.api.exception.ModuleException;

public class BadRequestException  extends ModuleException {
    public BadRequestException(Exception cause) {
        super(QuadientModuleErrorType.BAD_REQUEST, cause);
    }
}