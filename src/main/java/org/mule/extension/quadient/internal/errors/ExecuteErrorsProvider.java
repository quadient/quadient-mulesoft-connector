package org.mule.extension.quadient.internal.errors;

import org.mule.sdk.api.annotation.error.ErrorTypeProvider;
import org.mule.sdk.api.error.ErrorTypeDefinition;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ExecuteErrorsProvider implements ErrorTypeProvider {
    @Override
    public Set<ErrorTypeDefinition> getErrorTypes() {
        return Arrays.stream(QuadientModuleErrors.values()).collect(Collectors.toSet());
    }
}