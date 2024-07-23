package org.mule.extension.quadient.internal.errors.exception;

import org.mule.extension.quadient.internal.errors.QuadientModuleErrors;
import org.mule.sdk.api.exception.ModuleException;

public class NotFoundException extends ModuleException {
    public NotFoundException(Exception cause) {
        super(QuadientModuleErrors.NOT_FOUND, cause);
    }
}