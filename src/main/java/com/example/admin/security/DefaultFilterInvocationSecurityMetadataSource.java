package com.example.admin.security;

import com.example.admin.service.MenuService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;

public class DefaultFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private FilterInvocationSecurityMetadataSource securityMetadataSource;
    private MenuService menuService;

    public DefaultFilterInvocationSecurityMetadataSource(FilterInvocationSecurityMetadataSource securityMetadataSource, MenuService menuService) {
        this.securityMetadataSource = securityMetadataSource;
        this.menuService = menuService;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        HttpServletRequest request = ((FilterInvocation) object).getRequest();
        ArrayList<ConfigAttribute> attributes = new ArrayList<>();

        menuService.getMenus().forEach(menu -> {
            if (new AntPathRequestMatcher(menu.getPath()).matches(request)) {
                menu.getRoles().forEach(role -> {
                    attributes.add(new SecurityConfig(role.getName()));
                });
            }
        });

        if (attributes.isEmpty()) {
            return null;
        } else {
           return attributes;
        }
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
