package org.project.resourceservice.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.project.resourceservice.model.RecordDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class RecordDetailJsonConverter  {

    private ObjectMapper objectMapper;

    @Autowired
    public RecordDetailJsonConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


//    @Override
    public String convertToDatabaseColumn(RecordDetail recordDetail) {
        try {
            return objectMapper.writeValueAsString(recordDetail);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

//    @Override
    public RecordDetail convertToEntityAttribute(String string) {
        try {
            return objectMapper.readValue(string, RecordDetail.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
