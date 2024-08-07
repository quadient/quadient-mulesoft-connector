package org.mule.extension.quadient.internal.error.exception;

import org.mule.extension.quadient.internal.error.QuadientModuleErrorType;
import org.mule.sdk.api.exception.ModuleException;

public class BadRequestException  extends ModuleException {
    public BadRequestException(Exception cause) {
        super(QuadientModuleErrorType.BAD_REQUEST, cause);
    }
}