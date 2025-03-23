package com.smeem.persistence.postgresql;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class GenericEnumListConverter<E extends Enum<E>> implements AttributeConverter<List<E>, String> {

    private final Class<E> enumClass;

    public GenericEnumListConverter(Class<E> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public String convertToDatabaseColumn(List<E> enumValues) {
        if (enumValues == null || enumValues.isEmpty()) {
            return "";
        }
        return enumValues.stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));
    }

    @Override
    public List<E> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return List.of();
        }
        return Arrays.stream(dbData.split(","))
                .map(value -> Enum.valueOf(enumClass, value))
                .toList();
    }
}