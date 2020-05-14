package com.example.admin.util;

import org.springframework.beans.BeanUtils;

public interface BeanCopyUtils {
    public static <T> T copyBean(Object source, Class<T> clazz) {
        T target = BeanUtils.instantiateClass(clazz);
        BeanUtils.copyProperties(source, target);
        return target;
    }
}
