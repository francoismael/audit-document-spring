package com.audit.audit_document.infrastructure.security;

import com.audit.audit_document.domain.entity.Utilisateur;
import com.audit.audit_document.domain.repository.UtilisateurRepository;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UtilisateurRepository utilisateurRepository;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            UtilisateurRepository utilisateurRepository
    ) {
        this.jwtService = jwtService;
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        try {

            String authorizationHeader =
                    request.getHeader("Authorization");

            if (authorizationHeader != null &&
                authorizationHeader.startsWith("Bearer ")) {

                String token = authorizationHeader.substring(7);

                try {

                    String username =
                            jwtService.extractUsername(token);

                    Utilisateur utilisateur =
                            utilisateurRepository
                                    .findByUsername(username)
                                    .orElse(null);

                    if (utilisateur != null &&
                        utilisateur.getActif()) {

                        SimpleGrantedAuthority authority =
                                new SimpleGrantedAuthority(
                                        "ROLE_" +
                                        utilisateur.getRole().name()
                                );

                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        utilisateur,
                                        null,
                                        Collections.singletonList(authority)
                                );
                                System.out.println("Username : " + utilisateur.getUsername());
System.out.println("Role : ROLE_" + utilisateur.getRole().name());

                        SecurityContextHolder
                                .getContext()
                                .setAuthentication(authentication);
                    }

                } catch (Exception e) {
                    SecurityContextHolder
                            .clearContext();
                }
            }

            filterChain.doFilter(request, response);

        } finally {

            SecurityContextHolder.clearContext();
        }
    }
}