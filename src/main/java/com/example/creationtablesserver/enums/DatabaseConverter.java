package com.example.creationtablesserver.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter
public class DatabaseConverter implements AttributeConverter<Database, String> {
    @Override
    public String convertToDatabaseColumn(Database attribute) {
        if (attribute == null)
            return null;
        return attribute.name();
    }

    @Override
    public Database convertToEntityAttribute(String dbData) {
        if (dbData == null)
            return null;

        return Arrays.stream(Database.values())
                .filter(db -> db.name().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
