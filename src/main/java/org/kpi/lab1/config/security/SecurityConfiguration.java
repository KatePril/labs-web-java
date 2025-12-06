package org.kpi.lab1.config.security;

import org.kpi.lab1.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

  private static final String API_FOOS = "api/v1/admin/**";

  @Bean
  @Order(1)
  public SecurityFilterChain apiFoosFilterChain(HttpSecurity http) throws Exception {
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new AuthorityConverter());

    http.cors(cors -> cors.disable())
        .csrf(csrf -> csrf.disable())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(
            (request, response, chain) -> {
              HttpServletRequest httpRequest = (HttpServletRequest) request;
              String apiKey = httpRequest.getHeader(SecurityUtil.X_API_KEY_HEADER);
              if (apiKey == null || apiKey.isBlank()) {
                throw new RuntimeException("Missing API key");
              }
              chain.doFilter(request, response);
            },
            UsernamePasswordAuthenticationFilter.class)
        .authorizeHttpRequests(
            authz ->
                authz
                    .requestMatchers(HttpMethod.GET, API_FOOS)
                    .hasAuthority("SCOPE_read")
                    .requestMatchers(HttpMethod.POST, "/foos")
                    .hasAuthority("SCOPE_write")
                    .anyRequest()
                    .authenticated())
        .oauth2ResourceServer(
            oauth2 ->
                oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)));

    return http.build();
  }

  @Bean
  @Order(2)
  public SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
            authz -> authz.requestMatchers("/login/**").permitAll().anyRequest().authenticated())
        .oauth2Login();

    return http.build();
  }
}
