package com.quadient.connectors.evolve.internal.error.exception;

import com.quadient.connectors.evolve.internal.error.QuadientModuleErrorType;
import org.mule.sdk.api.exception.ModuleException;

@SuppressWarnings({"java:S110"})
public class NotFoundException extends ModuleException {
    public NotFoundException(Exception cause) {
        super(QuadientModuleErrorType.NOT_FOUND, cause);
    }
}