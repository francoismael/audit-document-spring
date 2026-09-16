package com.audit.audit_document.infrastructure.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        AuthenticationEntryPoint authenticationEntryPoint =
                (request, response, authException) ->
                        response.sendError(
                                HttpServletResponse.SC_UNAUTHORIZED,
                                "Authentification requise"
                        );

        http
            .csrf().disable()

            .authorizeRequests()

            
            // AUTHENTIFICATION
          

            .antMatchers("/api/auth/**")
                .permitAll()



            // UTILISATEURS

            .antMatchers(HttpMethod.GET,"/api/utilisateurs/**").hasRole("ADMIN")
            .antMatchers(HttpMethod.POST,"/api/utilisateurs").hasRole("ADMIN")
            .antMatchers(HttpMethod.PUT,"/api/utilisateurs/**").hasRole("ADMIN")
            .antMatchers(HttpMethod.DELETE,"/api/utilisateurs/**").hasRole("ADMIN")
            .antMatchers(HttpMethod.PATCH, "/api/utilisateurs/*/actif").hasRole("ADMIN")




            // MISSIONS
            .antMatchers( HttpMethod.GET,"/api/missions/**").hasAnyRole("ADMIN", "UTILISATEUR")
            .antMatchers( HttpMethod.POST,"/api/missions").hasAnyRole("ADMIN", "UTILISATEUR")
            .antMatchers(HttpMethod.PUT,"/api/missions/**").hasAnyRole("ADMIN", "UTILISATEUR")
            // Suppression : ADMIN uniquement
            .antMatchers(HttpMethod.DELETE, "/api/missions/**").hasRole("ADMIN")


// INTERVIEWS
        .antMatchers( HttpMethod.GET,"/api/interviews/**").hasAnyRole("ADMIN", "UTILISATEUR")
        .antMatchers(HttpMethod.POST,"/api/interviews").hasAnyRole("ADMIN", "UTILISATEUR")
        .antMatchers(HttpMethod.PUT,"/api/interviews/**").hasAnyRole("ADMIN", "UTILISATEUR")
// Suppression : ADMIN uniquement
        .antMatchers(HttpMethod.DELETE,"/api/interviews/**").hasRole("ADMIN")




// DECLARATIONS D'INDEPENDANCE
        .antMatchers(HttpMethod.GET, "/api/declarations-independance/**").hasAnyRole("ADMIN", "UTILISATEUR")
        .antMatchers(HttpMethod.POST,"/api/declarations-independance").hasAnyRole("ADMIN", "UTILISATEUR")
        .antMatchers(HttpMethod.PUT,"/api/declarations-independance/**").hasAnyRole("ADMIN", "UTILISATEUR")
// Suppression : ADMIN uniquement
        .antMatchers(HttpMethod.DELETE,"/api/declarations-independance/**").hasRole("ADMIN")


            // =========================
            // AUTRES API


            .anyRequest()
                .authenticated()

            .and()
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint)

            .and()
            .addFilterBefore(
                    jwtAuthenticationFilter,
                    UsernamePasswordAuthenticationFilter.class
            );
    }
}