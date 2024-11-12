package com.quadient.connectors.evolve.internal.operation.generate_v6.ondemand;

import com.quadient.connectors.evolve.api.generate.ondemand.OnDemandCustomDataInputFE;
import com.quadient.connectors.evolve.internal.connection.Connection;
import com.quadient.connectors.evolve.internal.error.exception.InvalidInputParameterException;
import com.quadient.connectors.evolve.internal.operation.ServiceEndpoint;
import com.quadient.connectors.evolve.internal.operation.generatev6.ondemand.OnDemandCustomDataOperation;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mule.runtime.api.metadata.TypedValue;
import org.mule.runtime.api.util.MultiMap;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OnDemandCustomDataOperationTest extends TestCase {

    @Mock
    private Connection connection;

    @Captor
    private ArgumentCaptor<String> endpointCaptor;

    @Captor
    private ArgumentCaptor<TypedValue<Object>> customDataCaptor;

    @Captor
    private ArgumentCaptor<Map<String, String>> headersCaptor;


    @Test
    public void testOnDemandOnDemandCustomData() {
        OnDemandCustomDataOperation onDemandCustomDataOperation = new OnDemandCustomDataOperation();
        when(connection.sendPOSTRequest(anyString(), any(TypedValue.class), anyMap())).thenReturn(null);

        OnDemandCustomDataInputFE input = new OnDemandCustomDataInputFE();
        input.pipelineName = "pipelineName";
        input.fileName = "fileName";
        input.folder = "folder";
        input.useDraftPipeline = true;
        input.useDraftResources = true;
        input.onDemandCustomDataPipelineVariables = new MultiMap<>();
        input.onDemandCustomDataPipelineVariables.put("codeName", "value");
        input.onDemandCustomDataCustomData = new TypedValue<>(new Object(), null);
        onDemandCustomDataOperation.onDemandOnDemandCustomData(connection, input);

        verify(connection).sendPOSTRequest(endpointCaptor.capture(), customDataCaptor.capture(), headersCaptor.capture());
        assertEquals(5, headersCaptor.getValue().size());
        assertEquals(input.onDemandCustomDataCustomData, customDataCaptor.getValue());
        assertEquals(ServiceEndpoint.ON_DEMAND_ON_DEMAND_CUSTOM_DATA + "/" + input.pipelineName, endpointCaptor.getValue());
        assertEquals("value", headersCaptor.getValue().get("c-variable-codeName"));
        assertEquals("fileName", headersCaptor.getValue().get("filename"));
        assertEquals("folder", headersCaptor.getValue().get("folder"));
        assertEquals("true", headersCaptor.getValue().get("useDraftPipeline"));
        assertEquals("true", headersCaptor.getValue().get("useDraftResources"));
    }

    @Test(expected = InvalidInputParameterException.class)
    public void testTooManyVariables() {
        OnDemandCustomDataOperation onDemandCustomDataOperation = new OnDemandCustomDataOperation();

        OnDemandCustomDataInputFE input = new OnDemandCustomDataInputFE();
        input.pipelineName = "pipelineName";
        input.onDemandCustomDataPipelineVariables = new MultiMap<>();
        for (int i = 0; i < 51; i++) {
            input.onDemandCustomDataPipelineVariables.put("codeName" + i, "value" + i);
        }
        onDemandCustomDataOperation.onDemandOnDemandCustomData(connection, input);
    }
}
