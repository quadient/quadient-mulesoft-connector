package org.mule.extension.quadient.internal.errors.exception;

import org.mule.extension.quadient.internal.errors.QuadientModuleErrors;
import org.mule.sdk.api.exception.ModuleException;

public class InvalidInputParameterException extends ModuleException {
    public InvalidInputParameterException(Exception cause) {
        super(QuadientModuleErrors.INVALID_INPUT_PARAMETER, cause);
    }
}