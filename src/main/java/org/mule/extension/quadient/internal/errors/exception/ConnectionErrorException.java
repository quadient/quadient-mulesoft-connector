package org.mule.extension.quadient.internal.errors.exception;

import org.mule.extension.quadient.internal.errors.QuadientModuleErrors;
import org.mule.sdk.api.exception.ModuleException;

public class ConnectionErrorException extends ModuleException {
    public ConnectionErrorException(Exception cause) {
        super(QuadientModuleErrors.CONNECTION_ERROR, cause);
    }
}