package com.smeem.api.config;

import com.smeem.api.auth.jwt.CustomJwtAuthenticationEntryPoint;
import com.smeem.api.auth.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomJwtAuthenticationEntryPoint customJwtAuthenticationEntryPoint;

    @Bean
    @Profile({"dev", "local"})
    public SecurityFilterChain filterChainDev(HttpSecurity http) throws Exception {
        permitSwaggerUri(http);
        setHttp(http);
        return http.build();
    }

    @Bean
    @Profile("prod")
    public SecurityFilterChain filterChainProd(HttpSecurity http) throws Exception {
        setHttp(http);
        return http.build();
    }

    private void setHttp(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(customJwtAuthenticationEntryPoint))
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests
                                .requestMatchers(new AntPathRequestMatcher("/api/v2/auth", "POST")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/v2/test")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/v2/versions/client/app")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/v2/goals/{type}")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/v2/goals")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/v2/members/nickname/check")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/favicon.ico")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/error")).permitAll()
                                .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    private void permitSwaggerUri(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/docs/**")).permitAll());
    }
}