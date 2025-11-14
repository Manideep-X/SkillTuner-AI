package com.manideep.skilltunerai.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.manideep.skilltunerai.filter.JwtRequestFilter;
import com.manideep.skilltunerai.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;
    private final String frontendUrl;
    
    public SecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter, @Value("${app.frontend-url}") String frontendUrl) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
        this.frontendUrl = frontendUrl;
    }

    // Security filter chain configuration
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable()) // Same as .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(configurer -> configurer
                    .requestMatchers("/health", "/info", "/status", "/checkup", "/auth/**").permitAll()
                    .anyRequest().authenticated())
            // Instead of DaoAuthenticationProvider(deprecated), UserDetailsService can be pluged in security filter clain.
            // Spring will automatically look for PasswordEncoder & autowire it when encountered a UserDeatilsService.
            .userDetailsService(customUserDetailsService)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    // Spring will use this password encoder under the hood
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // CORS configuration
    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration cors = new CorsConfiguration();

        // Allowed domains, who can send sequests
        cors.setAllowedOriginPatterns(List.of(frontendUrl));

        // Allowed HTTP methods
        cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Allowed HTTP headers
        cors.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));

        // Allows authentication token in the CORS
        cors.setAllowCredentials(true);

        // This is used to map the CORS rule to a specific URL path in the backend
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // Register the CORS config to all backend endpoints("/**")
        source.registerCorsConfiguration("/**", cors);

        return source;

    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {

        // Tells Spring to use authentication logic that is mentioned in the security filter chain.
        return authConfig.getAuthenticationManager();
    }

}
