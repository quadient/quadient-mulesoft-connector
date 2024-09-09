package com.quadient.connectors.evolve.internal.error.exception;

import com.quadient.connectors.evolve.internal.error.QuadientModuleErrorType;
import org.mule.sdk.api.exception.ModuleException;

@SuppressWarnings({"java:S110"})
public class InvalidInputParameterException extends ModuleException {
    public InvalidInputParameterException(Exception cause) {
        super(QuadientModuleErrorType.INVALID_INPUT_PARAMETER, cause);
    }
}