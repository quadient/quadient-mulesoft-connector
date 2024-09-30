package com.quadient.connectors.evolve.internal.operation.generate_v6.batch;

import com.quadient.connectors.evolve.api.generate.InputVariablesOptionsFE;
import com.quadient.connectors.evolve.api.generate.MultipartAttachmentFE;
import com.quadient.connectors.evolve.api.generate.VariableTypeFE;
import com.quadient.connectors.evolve.api.generate.batch.StartBatchJobInputFE;
import com.quadient.connectors.evolve.internal.connection.Connection;
import com.quadient.connectors.evolve.internal.error.exception.InvalidInputParameterException;
import com.quadient.connectors.evolve.internal.operation.generatev6.batch.StartBatchJobOperation;
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

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StartBatchJobOperationTest extends TestCase {

    @Mock
    private Connection connection;

    @Captor
    private ArgumentCaptor<String> bodyCaptor;

    @Captor
    private ArgumentCaptor<ArrayList<MultipartAttachmentFE>> attachmentsCaptor;

    @Test
    public void testBatchStartBatchJob() throws IOException {
        StartBatchJobOperation startBatchJobOperation = new StartBatchJobOperation();

        when(connection.sendPOSTRequest(anyString(), anyString())).thenReturn(null);

        StartBatchJobInputFE input = new StartBatchJobInputFE();
        input.pipelineName = "pipelineName";
        input.workingFolderId = "workingFolderId";
        input.description = "description";
        input.priority = 1L;
        input.useDraftPipeline = true;
        input.useDraftResources = true;
        input.expiration = "2024-07-10T10:15:30+01:00";
        input.variables = new ArrayList<>();
        InputVariablesOptionsFE inputVariablesOptionsFE1 = createVariable("codeName1", VariableTypeFE.PIPELINE, "value1", "option1", "option2");
        InputVariablesOptionsFE inputVariablesOptionsFE2 = createVariable("codeName2", VariableTypeFE.STEP, "value2", "option1");
        input.variables.add(inputVariablesOptionsFE1);
        input.variables.add(inputVariablesOptionsFE2);
        input.attachments = new ArrayList<>();

        startBatchJobOperation.batchStartBatchJob(connection, input);

        verify(connection).sendPOSTRequest(anyString(), bodyCaptor.capture());
        File file = new File("src/test/resources/operation/batch/startBatchJobOperationTest.txt");
        assertEquals(FileUtils.readFileToString(file, "UTF-8"), bodyCaptor.getValue());
    }

    @Test
    public void testBatchStartBatchJobMultipart() throws IOException {
        StartBatchJobOperation startBatchJobOperation = new StartBatchJobOperation();

        when(connection.sendRequestMultiPart(eq(HttpConstants.Method.POST), anyString(), anyString(), anyList())).thenReturn(null);

        StartBatchJobInputFE input = new StartBatchJobInputFE();
        input.pipelineName = "pipelineName";
        input.workingFolderId = "workingFolderId";
        input.description = "description";
        input.priority = 1L;
        input.useDraftPipeline = true;
        input.useDraftResources = true;
        input.expiration = "2024-07-10T10:15:30+01:00";
        input.variables = new ArrayList<>();
        InputVariablesOptionsFE inputVariablesOptionsFE1 = createVariable("codeName1", VariableTypeFE.PIPELINE, "value1", "option1", "option2");
        InputVariablesOptionsFE inputVariablesOptionsFE2 = createVariable("codeName2", VariableTypeFE.STEP, "value2", "option1");
        input.variables.add(inputVariablesOptionsFE1);
        input.variables.add(inputVariablesOptionsFE2);
        input.attachments = new ArrayList<>();
        MultipartAttachmentFE attachment = new MultipartAttachmentFE();
        attachment.name = "attachmentName";
        attachment.multipartData = new TypedValue<>(new ByteArrayInputStream("attachmentData".getBytes()), DataType.BYTE_ARRAY);
        input.attachments.add(attachment);

        startBatchJobOperation.batchStartBatchJob(connection, input);

        verify(connection).sendRequestMultiPart(eq(HttpConstants.Method.POST), anyString(), bodyCaptor.capture(), attachmentsCaptor.capture());
        File file = new File("src/test/resources/operation/batch/startBatchJobOperationTest.txt");
        assertEquals(FileUtils.readFileToString(file, "UTF-8"), bodyCaptor.getValue());
        assertEquals(1, attachmentsCaptor.getValue().size());
        assertEquals("attachmentName", attachmentsCaptor.getValue().get(0).name);
        assertEquals("attachmentData", IOUtils.toString(attachmentsCaptor.getValue().get(0).multipartData.getValue(), StandardCharsets.UTF_8));
    }

    @Test(expected = InvalidInputParameterException.class)
    public void testPriorityTooLow() {
        priorityTesting(0L);
    }

    @Test(expected = InvalidInputParameterException.class)
    public void testPriorityTooHigh() {
        priorityTesting(101L);
    }

    @Test(expected = InvalidInputParameterException.class)
    public void testVariablesSize() {
        StartBatchJobOperation startBatchJobOperation = new StartBatchJobOperation();
        StartBatchJobInputFE startBatchJobInputFE = new StartBatchJobInputFE();
        startBatchJobInputFE.priority = 50L;
        startBatchJobInputFE.variables = new ArrayList<>();
        for (int i = 0; i < 51; i++) {
            startBatchJobInputFE.variables.add(new InputVariablesOptionsFE());
        }
        startBatchJobOperation.batchStartBatchJob(connection, startBatchJobInputFE);
    }

    private void priorityTesting(Long priority) {
        StartBatchJobOperation startBatchJobOperation = new StartBatchJobOperation();
        StartBatchJobInputFE startBatchJobInputFE = new StartBatchJobInputFE();
        startBatchJobInputFE.priority = priority;
        startBatchJobOperation.batchStartBatchJob(connection, startBatchJobInputFE);
    }

    private InputVariablesOptionsFE createVariable(String codeName, VariableTypeFE type, String value, String... options) {
        InputVariablesOptionsFE variable = new InputVariablesOptionsFE();
        variable.codeName = codeName;
        variable.type = type;
        variable.value = value;
        variable.options = new ArrayList<>();
        variable.options.addAll(Arrays.asList(options));
        return variable;
    }
}