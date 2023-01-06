package com.amdm_parser.utils.enum_converter;

import com.amdm_parser.utils.TopicCategories;
import org.springframework.core.convert.converter.Converter;

/**
 * Конвертирует параметр запроса category из любого регистра в TopicCategories
 */
public class StringToCategoryConverter implements Converter<String, TopicCategories> {
    @Override
    public TopicCategories convert(String source) {
        return TopicCategories.valueOf(source.toUpperCase());
    }
}
