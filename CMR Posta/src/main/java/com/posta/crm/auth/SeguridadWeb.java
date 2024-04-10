package com.posta.crm.auth;

import com.posta.crm.auth.filter.JwtAuthenticationFilter;
import com.posta.crm.auth.filter.JwtValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class SeguridadWeb {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authRules -> authRules

                .requestMatchers(HttpMethod.GET, "/users/byEmail/{email}").permitAll()
                .requestMatchers("/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                .requestMatchers(HttpMethod.GET, "/users").hasAnyRole("ADMIN", "ADVISER")
                .requestMatchers(HttpMethod.PUT, "/users").hasAnyRole("ADMIN", "ADVISER")
                .requestMatchers(HttpMethod.POST, "/users/advisory").hasAnyRole("ADMIN", "ADVISER")
                .requestMatchers(HttpMethod.GET, "/users/byAdvisory/{page}").hasAnyRole("ADMIN", "ADVISER")
                .requestMatchers("/plan/**").hasAnyRole("ADMIN", "ADVISER")
                .requestMatchers("/clients/**").hasAnyRole("ADMIN", "ADVISER")
                .requestMatchers("/diagEmp/**").hasAnyRole("ADMIN", "ADVISER")
                .requestMatchers("/financial/**").hasAnyRole("ADMIN", "ADVISER")
                .requestMatchers("/image/**").hasAnyRole("ADMIN", "ADVISER")
                .requestMatchers("/canvas/**").hasAnyRole("ADMIN", "ADVISER")
                .requestMatchers("/process/**").hasAnyRole("ADMIN", "ADVISER")
                .requestMatchers("/processEmpre/**").hasAnyRole("ADMIN", "ADVISER")
                .requestMatchers("/search/**").hasAnyRole("ADMIN", "ADVISER")
                .requestMatchers("/users/**").hasRole("ADMIN")
                .requestMatchers("/mensaje/**").hasAnyRole("ADMIN", "ADVISER")
                .requestMatchers("/calendario/**").hasAnyRole("ADMIN", "ADVISER")
                .requestMatchers("/pdf/**").permitAll()
                .anyRequest().authenticated())

                .addFilter(new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()))
                .addFilter(new JwtValidationFilter(authenticationConfiguration.getAuthenticationManager()))
                .csrf(config -> config.disable())
                .sessionManagement(managment -> managment.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .build();

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
       // config.setAllowedOrigins(Arrays.asList("http://crm-posta.s3-website-sa-east-1.amazonaws.com"));
        config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
