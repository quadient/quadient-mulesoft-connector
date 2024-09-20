package com.quadient.connectors.evolve.internal;

import com.quadient.connectors.evolve.internal.error.exception.RequestSerializationException;
import com.quadient.connectors.generated.model.v6.batch.QueryBatchJobStatus;
import junit.framework.TestCase;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.InputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThrows;

public class ObjectConverterTest extends TestCase {

    @Test
    public void testConvertToJson() {
        QueryBatchJobStatus batchJobStatus = new QueryBatchJobStatus().batchJobId("batchJobId");
        String json = new ObjectConverter().convertToJson(batchJobStatus);
        assertThat(json, is(equalTo("{\"batchJobId\":\"batchJobId\"}")));
    }

    @Test
    public void testReadValueInputStream() {
        InputStream inputStream = IOUtils.toInputStream("{\"batchJobId\":\"batchJobId\"}", "UTF-8");
        QueryBatchJobStatus batchJobStatus = new ObjectConverter().readValue(inputStream, QueryBatchJobStatus.class);
        assertThat(batchJobStatus.getBatchJobId(), is(equalTo("batchJobId")));
    }

    @Test
    public void testReadValueInputStreamException() {
        InputStream inputStream = IOUtils.toInputStream("{", "UTF-8");
        assertThrows(RequestSerializationException.class, () -> {
            new ObjectConverter().readValue(inputStream, QueryBatchJobStatus.class);
        });
    }

    @Test
    public void testReadValue() {
        QueryBatchJobStatus batchJobStatus = new ObjectConverter().readValue("{\"batchJobId\":\"batchJobId\"}", QueryBatchJobStatus.class);
        assertThat(batchJobStatus.getBatchJobId(), is(equalTo("batchJobId")));
    }

    @Test
    public void testReadValueException() {
        assertThrows(RequestSerializationException.class, () -> {
            new ObjectConverter().readValue("{", QueryBatchJobStatus.class);
        });
    }
}