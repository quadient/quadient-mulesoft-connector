package org.mule.extension.quadient.internal.error.provider;

import org.mule.extension.quadient.internal.error.QuadientModuleErrorType;
import org.mule.sdk.api.annotation.error.ErrorTypeProvider;
import org.mule.sdk.api.error.ErrorTypeDefinition;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ExecuteErrorsProvider implements ErrorTypeProvider {
    @Override
    public Set<ErrorTypeDefinition> getErrorTypes() {
        return Arrays.stream(QuadientModuleErrorType.values()).collect(Collectors.toSet());
    }
}