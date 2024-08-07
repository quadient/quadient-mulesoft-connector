package org.mule.extension.quadient.internal.error.exception;

import org.mule.extension.quadient.internal.error.QuadientModuleErrorType;
import org.mule.sdk.api.exception.ModuleException;

public class NotFoundException extends ModuleException {
    public NotFoundException(Exception cause) {
        super(QuadientModuleErrorType.NOT_FOUND, cause);
    }
}