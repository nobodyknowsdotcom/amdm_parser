package com.amdm_parser.dto;

import com.amdm_parser.utils.TopicCategories;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Data
public class RepositoryMetrics {
    int size;
    Map<TopicCategories, Integer> categorySizes;
}
