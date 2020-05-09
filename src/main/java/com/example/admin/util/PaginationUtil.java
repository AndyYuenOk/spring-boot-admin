package com.example.admin.util;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

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
    }
}
