package com.quadient.connectors.evolve.internal.operation.generate_v6.batch;

import com.quadient.connectors.evolve.api.generate.batch.BatchCreateWorkingFolderInputFE;
import com.quadient.connectors.evolve.internal.ObjectConverter;
import com.quadient.connectors.evolve.internal.connection.Connection;
import com.quadient.connectors.evolve.internal.error.exception.InvalidInputParameterException;
import com.quadient.connectors.evolve.internal.operation.HttpResponseAttributes;
import com.quadient.connectors.generated.model.v6.batch.CreateWorkingFolderResponse;
import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mule.sdk.api.runtime.operation.Result;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BatchCreateWorkingFolderOperationTest extends TestCase {

    @Mock
    private Connection connection;

    @Captor
    private ArgumentCaptor<String> bodyCaptor;

    @Test
    public void testBatchCreateWorkingFolder() throws IOException {
        BatchCreateWorkingFolderOperation batchCreateWorkingFolderOperation = new BatchCreateWorkingFolderOperation();
        CreateWorkingFolderResponse response = new CreateWorkingFolderResponse();
        response.setWorkingFolderId("workingFolderId");
        Result<InputStream, HttpResponseAttributes> result = Result.<InputStream, HttpResponseAttributes>builder()
                .output(IOUtils.toInputStream(new ObjectConverter().convertToJson(response), StandardCharsets.UTF_8))
                .build();
        when(connection.sendPOSTRequest(anyString(), anyString())).thenReturn(result);

        BatchCreateWorkingFolderInputFE input = new BatchCreateWorkingFolderInputFE();
        input.name = "name";
        input.expiration = "2024-07-10T10:15:30+01:00";
        input.isJobDedicated = true;
        Result<String, HttpResponseAttributes> output = batchCreateWorkingFolderOperation.batchCreateWorkingFolder(connection, input);
        assertEquals("workingFolderId", output.getOutput());

        verify(connection).sendPOSTRequest(anyString(), bodyCaptor.capture());
        File file = new File("src/test/resources/operation/batch/batchCreateWorkingFolderOperationTest.txt");
        assertEquals(FileUtils.readFileToString(file, "UTF-8"), bodyCaptor.getValue());

    }

    @Test(expected = InvalidInputParameterException.class)
    public void testEmptyName() {
        nameLengthTesting("");
    }

    @Test(expected = InvalidInputParameterException.class)
    public void testLongName() {
        nameLengthTesting("012345678901234567891");
    }

    private void nameLengthTesting(String name) {
        BatchCreateWorkingFolderOperation batchCreateWorkingFolderOperation = new BatchCreateWorkingFolderOperation();
        BatchCreateWorkingFolderInputFE input = new BatchCreateWorkingFolderInputFE();
        input.name = name;
        batchCreateWorkingFolderOperation.batchCreateWorkingFolder(connection, input);
    }
}