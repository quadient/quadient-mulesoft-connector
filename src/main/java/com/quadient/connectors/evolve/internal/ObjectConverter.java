package com.quadient.connectors.evolve.internal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.quadient.connectors.evolve.internal.error.exception.RequestSerializationException;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class ObjectConverter {
    ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JsonNullableModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);
    
    public String convertToJson(Object object) {
        ObjectWriter ow = mapper.writer();
        try {
            return ow.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RequestSerializationException(e);
        }
    }
    
    public <T> T readValue(InputStream src, Class<T> valueType) {
        try {
            return mapper.readValue(src, valueType);
        } catch (IOException e) {
            throw new RequestSerializationException(e);
        }
    }

    public <T> T readValue(String src, Class<T> valueType) {
        try {
            return mapper.readValue(src, valueType);
        } catch (IOException e) {
            throw new RequestSerializationException(e);
        }
    }
}
