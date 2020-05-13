package com.example.admin.security;

import com.example.admin.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.NullRememberMeServices;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;

public class JsonAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private JsonObject jsonObject;
    private RememberMeServices rememberMeServices = new NullRememberMeServices();

    public JsonAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            jsonObject = JsonParser.parseReader(request.getReader()).getAsJsonObject();
        } catch (IOException e) {
            throw new RuntimeException("请提交 JSON 数据");
        }

        return super.attemptAuthentication(request, response);
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        return jsonObject.get("username").getAsString();
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        return jsonObject.get("password").getAsString();
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        if (logger.isDebugEnabled()) {
            logger.debug("Authentication success. Updating SecurityContextHolder to contain: "
                    + authResult);
        }

        SecurityContextHolder.getContext().setAuthentication(authResult);

        rememberMeServices.loginSuccess(request, response, authResult);

        // Fire event
        if (this.eventPublisher != null) {
            eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(
                    authResult, this.getClass()));
        }

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        User user = (User) authResult.getPrincipal();
        map.put("id", user.getId());
        map.put("username", user.getUsername());
        map.put("roles", user.getAuthorities());
        response.setHeader("Content-Type", "application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(map));
    }
}
