package com.quadient.connectors.evolve.internal.operation.generate_v6.batch;

import com.quadient.connectors.evolve.api.generate.batch.BatchJobStatusInputFE;
import com.quadient.connectors.evolve.internal.ObjectConverter;
import com.quadient.connectors.evolve.internal.connection.Connection;
import com.quadient.connectors.evolve.internal.operation.HttpResponseAttributes;
import com.quadient.connectors.generated.model.v6.batch.BatchJobDto;
import com.quadient.connectors.generated.model.v6.batch.BatchStatusJobResponse;
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
public class BatchJobStatusOperationTest extends TestCase {

    @Mock
    private Connection connection;

    @Captor
    private ArgumentCaptor<String> bodyCaptor;

    @Test
    public void testBatchBatchJobStatus() throws IOException {
        BatchJobStatusOperation operation = new BatchJobStatusOperation();

        BatchStatusJobResponse response = new BatchStatusJobResponse();
        BatchJobDto batchJobDto = new BatchJobDto();
        batchJobDto.setState(BatchJobDto.StateEnum.FINISHED);
        response.setBatchJob(batchJobDto);
        Result<InputStream, HttpResponseAttributes> result = Result.<InputStream, HttpResponseAttributes>builder()
                .output(IOUtils.toInputStream(new ObjectConverter().convertToJson(response), StandardCharsets.UTF_8))
                .build();
        when(connection.sendPOSTRequest(anyString(), anyString())).thenReturn(result);

        BatchJobStatusInputFE input = new BatchJobStatusInputFE();
        input.batchJobId = "batchJobId";
        Result<String, HttpResponseAttributes> output = operation.batchBatchJobStatus(connection, input);
        assertEquals("Finished", output.getOutput());

        verify(connection).sendPOSTRequest(anyString(), bodyCaptor.capture());
        File file = new File("src/test/resources/operation/batch/batchJobStatusOperationTest.txt");
        assertEquals(FileUtils.readFileToString(file, "UTF-8"), bodyCaptor.getValue());
    }
}
