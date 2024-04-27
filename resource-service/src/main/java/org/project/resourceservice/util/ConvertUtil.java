package org.project.resourceservice.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.project.resourceservice.exception.ResourceException;
import org.project.resourceservice.model.RecordDetail;

import java.util.List;

public class ConvertUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T jsonToObject(String json, TypeReference<T> typeReference) throws ResourceException {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            throw new ResourceException("", "Exception while converting json to " + typeReference.getType().getTypeName() + ". \n" + e.getMessage());
        }
    }

    public static <T> List<T> convertToList(String object, TypeReference<List<T>> typeReference) {
        return objectMapper.convertValue(object, typeReference);
    }

    public static String objectToJson(Object object) throws ResourceException {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new ResourceException("", "Exception while converting object to json.\n" + e.getMessage());
        }
    }
}
