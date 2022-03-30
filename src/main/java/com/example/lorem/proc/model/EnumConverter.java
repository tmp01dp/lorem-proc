package com.example.lorem.proc.model;

import org.springframework.core.convert.converter.Converter;

public class EnumConverter implements Converter<String, ParagraphType> {
    @Override
    public ParagraphType convert(String source) {
        return ParagraphType.valueOf(source.toUpperCase());
    }
}