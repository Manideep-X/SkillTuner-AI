package com.manideep.skilltunerai.mapper;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

// Converts a Java object into a JSON String before saving.
// Converts JSON String from DB back into a Java object when loading.
@Converter
public class ListToJsonConverter implements AttributeConverter<List<String>, String> {

    // ObjectMapper is a class provided by Jackson library
    // It's used to convert Java object to JSON
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> listOfString) {
        
        try {
            return objectMapper.writeValueAsString(listOfString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Can't convert List of String to String for Hibernate!");
        }
        
    }
    
    @Override
    public List<String> convertToEntityAttribute(String jsonAsString) {
        
        try {
            return objectMapper.readValue(jsonAsString, new TypeReference<List<String>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Can't convert String to List of String from Hibernate!");
        }

    }

}
