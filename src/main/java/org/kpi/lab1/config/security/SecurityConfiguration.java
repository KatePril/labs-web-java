package org.kpi.lab1.config.security;

import java.util.*;
import java.util.stream.Collectors;
import jakarta.servlet.http.HttpServletRequest;

import org.kpi.lab1.util.SecurityUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.addFilterBefore(
            (request, response, chain) -> {
              String apiKey = request.getHeader(SecurityUtil.X_API_KEY_HEADER);
              if (apiKey == null || apiKey.isBlank()) {
                response.setStatus(401);
                return;
              }
              chain.doFilter(request, response);
            },
            AbstractPreAuthenticatedProcessingFilter.class)
        .authorizeHttpRequests(
            authz ->
                authz
                    .requestMatchers(HttpMethod.GET, "/api/v1/admin/**")
                    .hasAuthority("SCOPE_read")
                    .requestMatchers(HttpMethod.POST, "/api/v1/admin/")
                    .hasAuthority("SCOPE_write")
                    .anyRequest()
                    .authenticated())
        .oauth2ResourceServer(
            oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(customJwtConverter())));

    return http.build();
  }

  @Bean
  public JwtAuthenticationConverter customJwtConverter() {
    JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
    converter.setJwtGrantedAuthoritiesConverter(this::extractAuthorities);

    return converter;
  }

  private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
    Collection<GrantedAuthority> jwtAuthorities = new AuthorityConverter().convert(jwt);

    HttpServletRequest request =
        org.springframework.security.core.context.SecurityContextHolder.getContext()
                        .getAuthentication()
                    instanceof AbstractAuthenticationToken token
                && token.getDetails() instanceof HttpServletRequest r
            ? r
            : null;

    List<GrantedAuthority> headerAuthorities = new ArrayList<>();

    if (request != null) {
      String claimsHeader = request.getHeader(SecurityUtil.ROLE_CLAIMS_HEADER);
      if (claimsHeader != null && !claimsHeader.isBlank()) {
        headerAuthorities =
            Arrays.stream(claimsHeader.split(","))
                .map(String::trim)
                .map(role -> "ROLE_" + role)
                .map(org.springframework.security.core.authority.SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
      }
    }

    List<GrantedAuthority> merged = new ArrayList<>();
    merged.addAll(jwtAuthorities);
    merged.addAll(headerAuthorities);

    return merged;
  }
}
