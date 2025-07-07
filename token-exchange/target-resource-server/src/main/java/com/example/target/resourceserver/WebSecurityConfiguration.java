package com.example.target.resourceserver;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration {

    private final OAuth2ResourceServerProperties properties;

    public WebSecurityConfiguration(OAuth2ResourceServerProperties properties) {
        this.properties = properties;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(r -> r.jwt(withDefaults()));
        return http.build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder
                .withJwkSetUri(properties.getJwt().getJwkSetUri())
                .validateType(false).build();
        jwtDecoder.setJwtValidator(JwtValidators.createDefaultWithValidators(
                List.of(
                        JwtValidators.createAtJwtValidator()
                                .audience("http://localhost:9092/api/messages")
                                .clientId("demo-client-token-exchange")
                                .issuer("http://localhost:9500").build())));
        return jwtDecoder;
    }
}
