package org.mule.extension.quadient.internal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.mule.extension.quadient.internal.error.exception.RequestSerializationException;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class ObjectConverter {
    ObjectMapper mapper = new ObjectMapper().registerModule(new JsonNullableModule());
    
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
