package com.quadient.connectors.evolve.internal.operation.generate_v6.ondemand;

import com.quadient.connectors.evolve.api.generate.InputVariablesOptionsFE;
import com.quadient.connectors.evolve.api.generate.MultipartAttachmentFE;
import com.quadient.connectors.evolve.api.generate.VariableTypeFE;
import com.quadient.connectors.evolve.api.generate.ondemand.OnDemandOnDemandInputFE;
import com.quadient.connectors.evolve.internal.connection.Connection;
import com.quadient.connectors.evolve.internal.error.exception.InvalidInputParameterException;
import com.quadient.connectors.evolve.internal.operation.generatev6.ondemand.OnDemandOnDemandOperation;
import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mule.runtime.api.metadata.DataType;
import org.mule.runtime.api.metadata.TypedValue;
import org.mule.runtime.http.api.HttpConstants;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OnDemandOnDemandOperationTest extends TestCase {

    @Mock
    private Connection connection;

    @Captor
    private ArgumentCaptor<String> bodyCaptor;

    @Captor
    private ArgumentCaptor<ArrayList<MultipartAttachmentFE>> attachmentsCaptor;

    @Test
    public void testOnDemandOnDemand() throws IOException {
        OnDemandOnDemandOperation onDemandOnDemandOperation = new OnDemandOnDemandOperation();

        when(connection.sendPOSTRequest(anyString(), anyString())).thenReturn(null);

        OnDemandOnDemandInputFE input = new OnDemandOnDemandInputFE();
        input.pipelineName = "pipelineName";
        input.useDraftPipeline = true;
        input.useDraftResources = true;

        input.onDemandOnDemandVariables = new ArrayList<>();
        InputVariablesOptionsFE inputVariablesOptionsFE1 = createVariable("codeName1", VariableTypeFE.PIPELINE, "value1", "option1", "option2");
        InputVariablesOptionsFE inputVariablesOptionsFE2 = createVariable("codeName2", VariableTypeFE.STEP, "value2", "option1");
        input.onDemandOnDemandVariables.add(inputVariablesOptionsFE1);
        input.onDemandOnDemandVariables.add(inputVariablesOptionsFE2);

        input.onDemandOnDemandAttachments = new ArrayList<>();

        onDemandOnDemandOperation.onDemandOnDemand(connection, input);

        verify(connection).sendPOSTRequest(anyString(), bodyCaptor.capture());
        File file = new File("src/test/resources/operation/ondemand/onDemandOnDemandTest.txt");
        assertEquals(FileUtils.readFileToString(file, "UTF-8"), bodyCaptor.getValue());
    }

    @Test
    public void testOnDemandOnDemandMultiPart() throws IOException {
        OnDemandOnDemandOperation onDemandOnDemandOperation = new OnDemandOnDemandOperation();
        when(connection.sendRequestMultiPart(eq(HttpConstants.Method.POST), anyString(), anyString(), anyList())).thenReturn(null);

        OnDemandOnDemandInputFE input = new OnDemandOnDemandInputFE();
        input.pipelineName = "pipelineName";
        input.useDraftPipeline = true;
        input.useDraftResources = true;

        input.onDemandOnDemandVariables = new ArrayList<>();
        InputVariablesOptionsFE inputVariablesOptionsFE1 = createVariable("codeName1", VariableTypeFE.PIPELINE, "value1", "option1", "option2");
        InputVariablesOptionsFE inputVariablesOptionsFE2 = createVariable("codeName2", VariableTypeFE.STEP, "value2", "option1");
        input.onDemandOnDemandVariables.add(inputVariablesOptionsFE1);
        input.onDemandOnDemandVariables.add(inputVariablesOptionsFE2);

        input.onDemandOnDemandAttachments = new ArrayList<>();
        MultipartAttachmentFE attachment = new MultipartAttachmentFE();
        attachment.name = "attachmentName";
        attachment.multipartData = new TypedValue<>(new ByteArrayInputStream("attachmentData".getBytes()), DataType.BYTE_ARRAY);
        input.onDemandOnDemandAttachments.add(attachment);

        onDemandOnDemandOperation.onDemandOnDemand(connection, input);

        verify(connection).sendRequestMultiPart(eq(HttpConstants.Method.POST), anyString(), bodyCaptor.capture(), attachmentsCaptor.capture());
        File file = new File("src/test/resources/operation/ondemand/onDemandOnDemandTest.txt");
        assertEquals(FileUtils.readFileToString(file, "UTF-8"), bodyCaptor.getValue());
        assertEquals(1, attachmentsCaptor.getValue().size());
        assertEquals("attachmentName", attachmentsCaptor.getValue().get(0).name);
        assertEquals("attachmentData", IOUtils.toString(attachmentsCaptor.getValue().get(0).multipartData.getValue(), StandardCharsets.UTF_8));
    }

    @Test(expected = InvalidInputParameterException.class)
    public void testTooManyVariables() {
        OnDemandOnDemandOperation onDemandOnDemandOperation = new OnDemandOnDemandOperation();

        OnDemandOnDemandInputFE input = new OnDemandOnDemandInputFE();
        input.pipelineName = "pipelineName";
        input.onDemandOnDemandVariables = new ArrayList<>();
        for (int i = 0; i < 51; i++) {
            InputVariablesOptionsFE inputVariablesOptionsFE = createVariable("codeName" + i, VariableTypeFE.PIPELINE, "value" + i, "option" + i);
            input.onDemandOnDemandVariables.add(inputVariablesOptionsFE);
        }
        input.onDemandOnDemandAttachments = new ArrayList<>();

        onDemandOnDemandOperation.onDemandOnDemand(connection, input);
    }

    private InputVariablesOptionsFE createVariable(String codeName, VariableTypeFE type, String value, String... options) {
        InputVariablesOptionsFE variable = new InputVariablesOptionsFE();
        variable.codeName = codeName;
        variable.inputVariablesType = type;
        variable.value = value;
        variable.inputVariablesOptions = new ArrayList<>();
        variable.inputVariablesOptions.addAll(Arrays.asList(options));
        return variable;
    }
}