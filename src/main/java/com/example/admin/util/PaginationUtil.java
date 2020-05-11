package com.example.admin.util;

import com.example.admin.annotation.ColumnKey;
import com.example.admin.vo.UserVO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PaginationUtil {
    public static Map<String, Object> toPagination(Page<?> page) {
        HashMap<String, Object> pagination = new HashMap<>();
        pagination.put("total", page.getTotalElements());
        pagination.put("size", page.getSize());
        HashMap<String, Object> content = new HashMap<>();
        content.put("data", page.getContent());
        content.put("pagination", pagination);
        return content;
    }

    public static Map<String, Object> toPagination(Page<?> page, Class<?> clazz) {
        if (!page.getContent().isEmpty() && !page.getContent().get(0).getClass().getName().equals(clazz.getName())) {
            Page<?> objectPage = page.map(entity -> {
                try {
                    Object target = clazz.getDeclaredConstructor().newInstance();
                    BeanUtils.copyProperties(entity, target);
                    return target;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            return toPagination(objectPage);
        } else {
            return toPagination(page);
        }
    }

    public static Map<String, Object> toPaginationWithColumns(Page<?> page, Class<?> clazz) {
        Map<String, Object> content = toPagination(page, clazz);

        ArrayList<HashMap<String, String>> columns = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            HashMap<String, String> column = new HashMap<>();

            ApiModelProperty property = field.getAnnotation(ApiModelProperty.class);
            if (property == null) {
                continue;
            } else {
                column.put("name", property.value());
            }

            ColumnKey columnKey = field.getAnnotation(ColumnKey.class);
            if (columnKey == null) {
                JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
                if (jsonProperty == null) {
                    JsonSetter jsonSetter = field.getAnnotation(JsonSetter.class);
                    if (jsonSetter == null) {
                        column.put("key", field.getName());
                    } else {
                        column.put("key", jsonSetter.value());
                    }
                } else {
                    column.put("key", jsonProperty.value());
                }
            } else {
                column.put("key", columnKey.value());
            }

            columns.add(column);
        }

        content.put("columns", columns);

        return content;
    }
}
