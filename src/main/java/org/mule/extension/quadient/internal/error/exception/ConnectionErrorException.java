package org.mule.extension.quadient.internal.error.exception;

import org.mule.extension.quadient.internal.error.QuadientModuleErrorType;
import org.mule.sdk.api.exception.ModuleException;

public class ConnectionErrorException extends ModuleException {
    public ConnectionErrorException(Exception cause) {
        super(QuadientModuleErrorType.CONNECTION_ERROR, cause);
    }
}