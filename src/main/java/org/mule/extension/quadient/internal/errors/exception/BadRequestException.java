package org.mule.extension.quadient.internal.errors.exception;

import org.mule.extension.quadient.internal.errors.QuadientModuleErrors;
import org.mule.sdk.api.exception.ModuleException;

public class BadRequestException  extends ModuleException {
    public BadRequestException(Exception cause) {
        super(QuadientModuleErrors.BAD_REQUEST, cause);
    }
}