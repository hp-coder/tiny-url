package com.luban.tinyurl.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Optional;

/**
 * @author hp 2023/4/10
 */
@Converter
public class TinyUrlTypeConverter implements AttributeConverter<TinyUrlType, String> {

    @Override
    public String convertToDatabaseColumn(TinyUrlType attribute) {
       return Optional.ofNullable(attribute).map(TinyUrlType::getCode).orElse(null);
    }

    @Override
    public TinyUrlType convertToEntityAttribute(String dbData) {
        return Optional.ofNullable(dbData).flatMap(TinyUrlType::of).orElse(null);
    }
}
