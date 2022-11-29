package com.amdm_parser.utils.enum_converter;

import com.amdm_parser.utils.SortCategories;
import org.springframework.core.convert.converter.Converter;

public class StringToSortConverter implements Converter<String, SortCategories> {
    @Override
    public SortCategories convert(String source) {
        return SortCategories.valueOf(source.toUpperCase());
    }
}
