package com.example.admin.config;

import com.example.admin.repository.RoleRepository;
import com.example.admin.repository.UserRepository;
import com.example.admin.security.DefaultFilterInvocationSecurityMetadataSource;
import com.example.admin.security.JsonAuthenticationFilter;
import com.example.admin.security.JwtUtil;
import com.example.admin.security.RoleVoter;
import com.example.admin.service.MenuService;
import com.example.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MenuService menuService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
            .csrf().disable()
            .exceptionHandling(exceptionHandling -> {
                exceptionHandling.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
            })
            .authorizeRequests(authorizeRequests -> {
                authorizeRequests
                    .anyRequest()
                    .authenticated()
                    .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                        @Override
                        public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                            o.setSecurityMetadataSource(new DefaultFilterInvocationSecurityMetadataSource(o.getSecurityMetadataSource(), menuService));
                            return o;
                        }
                    })
                    .accessDecisionManager(new AffirmativeBased(Collections.singletonList(new RoleVoter())));
            })
            .formLogin(formLogin -> {
                formLogin.failureHandler(new SimpleUrlAuthenticationFailureHandler("/erorr"));
            })
            .logout(logout -> {
                logout.logoutSuccessUrl("/");
            })
            .addFilterAt(new JsonAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
//                .and()
//                .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtUtil))
//                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository, roleRepository));
        //@formatter:on
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}