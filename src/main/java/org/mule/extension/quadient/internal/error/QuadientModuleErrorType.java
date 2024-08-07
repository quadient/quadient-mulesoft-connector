package org.mule.extension.quadient.internal.error;

import org.mule.sdk.api.error.ErrorTypeDefinition;
import org.mule.sdk.api.error.MuleErrors;

import java.util.Optional;

public enum QuadientModuleErrorType implements ErrorTypeDefinition<QuadientModuleErrorType> {
    //https://docs.mulesoft.com/mule-runtime/latest/mule-error-concept
    CONNECTION_ERROR(MuleErrors.CONNECTIVITY),
    UNAUTHORIZED(MuleErrors.CLIENT_SECURITY),
    NOT_FOUND(MuleErrors.CONNECTIVITY),
    BAD_REQUEST(MuleErrors.ANY),
    TOO_MANY_REQUESTS(MuleErrors.CONNECTIVITY),
    REQUEST_SERIALIZATION_ERROR(MuleErrors.CONNECTIVITY),
    INVALID_INPUT_PARAMETER(MuleErrors.VALIDATION);

    private QuadientModuleErrorType() {
    }

    private ErrorTypeDefinition<? extends Enum<?>> parent;

    QuadientModuleErrorType(ErrorTypeDefinition<? extends Enum<?>> parent) {
        this.parent = parent;
    }

    @Override
    public Optional<ErrorTypeDefinition<? extends Enum<?>>> getParent() {
        return Optional.ofNullable(parent);
    }
}