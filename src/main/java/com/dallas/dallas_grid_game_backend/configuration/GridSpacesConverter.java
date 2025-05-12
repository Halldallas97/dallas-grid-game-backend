package com.dallas.dallas_grid_game_backend.configuration;

import com.dallas.dallas_grid_game_backend.pojo.GridSpaces;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.List;

@Converter
public class GridSpacesConverter implements AttributeConverter<List<GridSpaces>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<GridSpaces> gridSpacesList) {
        try {
            return objectMapper.writeValueAsString(gridSpacesList);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error", e);
        }
    }

    @Override
    public List<GridSpaces> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, objectMapper.getTypeFactory().constructCollectionType(List.class, GridSpaces.class));
        } catch (IOException e) {
            throw new IllegalArgumentException("Error", e);
        }
    }
}