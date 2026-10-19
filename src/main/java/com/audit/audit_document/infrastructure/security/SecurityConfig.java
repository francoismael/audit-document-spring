package com.audit.audit_document.infrastructure.security;

import com.audit.audit_document.application.dto.ApiErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final ObjectMapper objectMapper;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            ObjectMapper objectMapper) {

        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        AuthenticationEntryPoint authenticationEntryPoint =
                (request, response, authException) ->
                        writeError(
                                response,
                                HttpStatus.UNAUTHORIZED,
                                "Authentification requise");

        AccessDeniedHandler accessDeniedHandler =
                (request, response, accessDeniedException) ->
                        writeError(
                                response,
                                HttpStatus.FORBIDDEN,
                                "Accès refusé : vous n'avez pas les droits nécessaires");

        http
            // Uses the global CORS rules of CorsConfig, including browser preflight requests.
            .cors()
            .and()

            .csrf().disable()

            .authorizeRequests()

            // AUTHENTICATION
            .antMatchers("/api/auth/**").permitAll()

            // USERS: administrators only
            .antMatchers(HttpMethod.GET, "/api/utilisateurs/**").hasRole("ADMIN")
            .antMatchers(HttpMethod.POST, "/api/utilisateurs").hasRole("ADMIN")
            .antMatchers(HttpMethod.PUT, "/api/utilisateurs/**").hasRole("ADMIN")
            .antMatchers(HttpMethod.DELETE, "/api/utilisateurs/**").hasRole("ADMIN")
            .antMatchers(HttpMethod.PATCH, "/api/utilisateurs/*/actif").hasRole("ADMIN")

            // MISSIONS: deletion is reserved to administrators
            .antMatchers(HttpMethod.GET, "/api/missions/**").hasAnyRole("ADMIN", "UTILISATEUR")
            .antMatchers(HttpMethod.POST, "/api/missions").hasAnyRole("ADMIN", "UTILISATEUR")
            .antMatchers(HttpMethod.PUT, "/api/missions/**").hasAnyRole("ADMIN", "UTILISATEUR")
            .antMatchers(HttpMethod.DELETE, "/api/missions/**").hasRole("ADMIN")

            // INTERVIEWS: deletion is reserved to administrators
            .antMatchers(HttpMethod.GET, "/api/interviews/**").hasAnyRole("ADMIN", "UTILISATEUR")
            .antMatchers(HttpMethod.POST, "/api/interviews").hasAnyRole("ADMIN", "UTILISATEUR")
            .antMatchers(HttpMethod.PUT, "/api/interviews/**").hasAnyRole("ADMIN", "UTILISATEUR")
            .antMatchers(HttpMethod.DELETE, "/api/interviews/**").hasRole("ADMIN")

            // DECLARATIONS OF INDEPENDENCE: deletion is reserved to administrators
            .antMatchers(HttpMethod.GET, "/api/declarations-independance/**").hasAnyRole("ADMIN", "UTILISATEUR")
            .antMatchers(HttpMethod.POST, "/api/declarations-independance").hasAnyRole("ADMIN", "UTILISATEUR")
            .antMatchers(HttpMethod.PUT, "/api/declarations-independance/**").hasAnyRole("ADMIN", "UTILISATEUR")
            .antMatchers(HttpMethod.DELETE, "/api/declarations-independance/**").hasRole("ADMIN")

            // MEETINGS (opening and closing): deletion is reserved to administrators
            .antMatchers(HttpMethod.GET, "/api/reunions/**").hasAnyRole("ADMIN", "UTILISATEUR")
            .antMatchers(HttpMethod.POST, "/api/reunions").hasAnyRole("ADMIN", "UTILISATEUR")
            .antMatchers(HttpMethod.PUT, "/api/reunions/**").hasAnyRole("ADMIN", "UTILISATEUR")
            .antMatchers(HttpMethod.DELETE, "/api/reunions/**").hasRole("ADMIN")

            // TDR: deletion is reserved to administrators
            .antMatchers(HttpMethod.GET, "/api/tdrs/**").hasAnyRole("ADMIN", "UTILISATEUR")
            .antMatchers(HttpMethod.POST, "/api/tdrs").hasAnyRole("ADMIN", "UTILISATEUR")
            .antMatchers(HttpMethod.PUT, "/api/tdrs/**").hasAnyRole("ADMIN", "UTILISATEUR")
            .antMatchers(HttpMethod.DELETE, "/api/tdrs/**").hasRole("ADMIN")

            // WORK PROGRAMMES: deletion is reserved to administrators
            .antMatchers(HttpMethod.GET, "/api/programmes-travail/**").hasAnyRole("ADMIN", "UTILISATEUR")
            .antMatchers(HttpMethod.POST, "/api/programmes-travail").hasAnyRole("ADMIN", "UTILISATEUR")
            .antMatchers(HttpMethod.PUT, "/api/programmes-travail/**").hasAnyRole("ADMIN", "UTILISATEUR")
            .antMatchers(HttpMethod.DELETE, "/api/programmes-travail/**").hasRole("ADMIN")

            // TESTS: deletion is reserved to administrators
            .antMatchers(HttpMethod.GET, "/api/tests/**").hasAnyRole("ADMIN", "UTILISATEUR")
            .antMatchers(HttpMethod.POST, "/api/tests").hasAnyRole("ADMIN", "UTILISATEUR")
            .antMatchers(HttpMethod.PUT, "/api/tests/**").hasAnyRole("ADMIN", "UTILISATEUR")
            .antMatchers(HttpMethod.DELETE, "/api/tests/**").hasRole("ADMIN")

            // FINDINGS: deletion is reserved to administrators
            .antMatchers(HttpMethod.GET, "/api/constats/**").hasAnyRole("ADMIN", "UTILISATEUR")
            .antMatchers(HttpMethod.POST, "/api/constats").hasAnyRole("ADMIN", "UTILISATEUR")
            .antMatchers(HttpMethod.PUT, "/api/constats/**").hasAnyRole("ADMIN", "UTILISATEUR")
            .antMatchers(HttpMethod.DELETE, "/api/constats/**").hasRole("ADMIN")

            // OTHER APIS: any authenticated user
            .anyRequest().authenticated()

            .and()
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint)
            .accessDeniedHandler(accessDeniedHandler)

            .and()
            .addFilterBefore(
                    jwtAuthenticationFilter,
                    UsernamePasswordAuthenticationFilter.class
            );
    }

    /*
     * Security errors happen before the controllers, so GlobalExceptionHandler
     * cannot format them: the same JSON body is written here instead.
     */
    private void writeError(
            HttpServletResponse response,
            HttpStatus status,
            String message) throws IOException {

        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        objectMapper.writeValue(
                response.getOutputStream(),
                new ApiErrorResponse(status.value(), message));
    }
}
