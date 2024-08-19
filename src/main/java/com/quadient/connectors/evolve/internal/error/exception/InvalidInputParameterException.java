package com.quadient.connectors.evolve.internal.error.exception;

import com.quadient.connectors.evolve.internal.error.QuadientModuleErrorType;
import org.mule.sdk.api.exception.ModuleException;

public class InvalidInputParameterException extends ModuleException {
    public InvalidInputParameterException(Exception cause) {
        super(QuadientModuleErrorType.INVALID_INPUT_PARAMETER, cause);
    }
}