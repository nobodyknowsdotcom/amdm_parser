package com.amdm_parser.utils.enum_converter;

import com.amdm_parser.utils.SortCategories;
import org.springframework.core.convert.converter.Converter;

/**
 * Конвертирует параметр запроса sort из любого регистра в SortCategories
 */
public class StringToSortConverter implements Converter<String, SortCategories> {
    @Override
    public SortCategories convert(String source) {
        return SortCategories.valueOf(source.toUpperCase());
    }
}
