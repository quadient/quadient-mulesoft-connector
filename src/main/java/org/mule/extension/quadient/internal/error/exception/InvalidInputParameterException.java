package org.mule.extension.quadient.internal.error.exception;

import org.mule.extension.quadient.internal.error.QuadientModuleErrorType;
import org.mule.sdk.api.exception.ModuleException;

public class InvalidInputParameterException extends ModuleException {
    public InvalidInputParameterException(Exception cause) {
        super(QuadientModuleErrorType.INVALID_INPUT_PARAMETER, cause);
    }
}