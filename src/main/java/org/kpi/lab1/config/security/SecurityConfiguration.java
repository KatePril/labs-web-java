package org.kpi.lab1.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

  private static final String API_PATH = "api/v1/admin/products/**";

  @Bean
  @Order(1)
  public SecurityFilterChain apiroductsChain(HttpSecurity http, JwtDecoder jwtDecoder)
      throws Exception {
    JwtAuthenticationConverter jwtAuthConverter = new JwtAuthenticationConverter();
    jwtAuthConverter.setJwtGrantedAuthoritiesConverter(new AuthorityConverter());

    http.securityMatcher(API_PATH)
        .cors(cors -> cors.disable())
        .csrf(csrf -> csrf.disable())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(
            new CosmicApiKeyFilter(jwtDecoder), UsernamePasswordAuthenticationFilter.class)
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(HttpMethod.GET, API_PATH)
                    .hasAuthority("SCOPE_read")
                    .requestMatchers(HttpMethod.POST, "api/v1/admin/products")
                    .hasAuthority("SCOPE_write")
                    .anyRequest()
                    .authenticated())
        .oauth2ResourceServer(
            oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)));

    return http.build();
  }

  @Bean
  @Order(2)
  public SecurityFilterChain defaultChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
            auth -> auth.requestMatchers(API_PATH).permitAll().anyRequest().authenticated())
        .oauth2Login();

    return http.build();
  }
}
