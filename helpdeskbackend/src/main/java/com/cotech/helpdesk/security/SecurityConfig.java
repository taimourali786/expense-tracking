package com.cotech.helpdesk.security;

import com.cotech.helpdesk.UrlPrefix;
import com.cotech.helpdesk.security.filter.FilerChainExcpetionHandler;
import com.cotech.helpdesk.security.filter.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final FilerChainExcpetionHandler excpetionHandler;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //Enable cors when working on frontend to only allow request from localhost via frontend
        // or postman
        http
                .csrf(AbstractHttpConfigurer::disable) // Disabled so each request id authenticated
                .authorizeHttpRequests(authorizationManager ->
                        authorizationManager
                                .requestMatchers(String.format("%s/**", UrlPrefix.SWAGGER)).permitAll()
                                .requestMatchers(String.format("%s/**", UrlPrefix.AUTH)).permitAll()
                                .requestMatchers(String.format("%s/**", UrlPrefix.CATEGORY)).authenticated()
                                .requestMatchers(String.format("%s/**", UrlPrefix.DEPARTMENT)).authenticated()
                                .requestMatchers(String.format("%s/**", UrlPrefix.STATUS)).authenticated()
                                .requestMatchers(String.format("%s/**", UrlPrefix.PRIORITY)).authenticated()
                                .anyRequest().authenticated())
                .exceptionHandling(ex ->
                        ex.authenticationEntryPoint((request, response, authException) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                                        "Unauthorized")))
                .sessionManagement(sessionConfigurer ->
                        sessionConfigurer
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(excpetionHandler, JwtAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    protected AuthenticationProvider authenticationProvider() {
        // Responsible fot getting user information from database
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
