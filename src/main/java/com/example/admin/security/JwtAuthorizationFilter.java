package com.example.admin.security;

import com.example.admin.entity.User;
import com.example.admin.repository.RoleRepository;
import com.example.admin.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.admin.contant.SecurityConstant.SECRET;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean debug = this.logger.isDebugEnabled();

        try {
            UsernamePasswordAuthenticationToken authRequest = convert(request);
            if (authRequest == null) {
                chain.doFilter(request, response);
                return;
            }

            String username = authRequest.getName();
            if (debug) {
                this.logger.debug("Basic Authentication Authorization header found for user '" + username + "'");
            }

            if (this.authenticationIsRequired(username)) {
//                Authentication authResult = this.authenticationManager.authenticate(authRequest);
//                if (debug) {
//                    this.logger.debug("Authentication success: " + authResult);
//                }

                SecurityContextHolder.getContext().setAuthentication(authRequest);
//                this.rememberMeServices.loginSuccess(request, response, authResult);
//                this.onSuccessfulAuthentication(request, response, authResult);
            }
        } catch (AuthenticationException var8) {
            SecurityContextHolder.clearContext();
            if (debug) {
                this.logger.debug("Authentication request for failed: " + var8);
            }

//            this.rememberMeServices.loginFail(request, response);
//            this.onUnsuccessfulAuthentication(request, response, var8);
//            if (this.ignoreFailure) {
//                chain.doFilter(request, response);
//            } else {
//                this.authenticationEntryPoint.commence(request, response, var8);
//            }

            return;
        }

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken convert(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null) {
            return null;
        } else {
            header = header.trim();
            if (!StringUtils.startsWithIgnoreCase(header, "Bearer ")) {
                return null;
            } else {
                String username = Jwts.parserBuilder()
                        .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET)))
                        .build()
                        .parseClaimsJws(header.substring(7))
                        .getBody()
                        .getSubject();

                User user = userRepository.findByUsername(username, User.class);
//                user.setRoles(roleRepository.findByUser(user.getId()));

                return new UsernamePasswordAuthenticationToken(username, header.substring(7), user.getAuthorities());
            }
        }
    }

    private boolean authenticationIsRequired(String username) {
        Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();
        if (existingAuth != null && existingAuth.isAuthenticated()) {
            if (existingAuth instanceof UsernamePasswordAuthenticationToken && !existingAuth.getName().equals(username)) {
                return true;
            } else {
                return existingAuth instanceof AnonymousAuthenticationToken;
            }
        } else {
            return true;
        }
    }
}
