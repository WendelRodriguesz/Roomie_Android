package com.project.roomie.infra.security.configuration;

import com.project.roomie.infra.security.filter.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@Profile("prod")
public class SecurityConfigurationsProd {

    private final SecurityFilter securityFilter;

    @Autowired
    public SecurityConfigurationsProd(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, CorsConfigurationSource corsConfigurationSource) throws Exception {
        return httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize

                        // Authorization
                        .requestMatchers(HttpMethod.POST, "/api/auth/logar").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/refresh").permitAll()

                        // Usuário interessado
                        .requestMatchers(HttpMethod.POST, "/api/usuarioInteressado/cadastrar").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/usuarioInteressado/uploadFotoDePerfil/{id_usuario}").hasAnyRole("INTERESSADO")
                        .requestMatchers(HttpMethod.GET, "/api/usuarioInteressado/visualizar/{id_usuario}").hasAnyRole("INTERESSADO")
                        .requestMatchers(HttpMethod.PATCH, "/api/usuarioInteressado/atualizar/{id}").hasAnyRole("INTERESSADO")

                        // Usuário Ofertante
                        .requestMatchers(HttpMethod.POST, "/api/usuarioOfertante/cadastrar").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/usuarioOfertante/uploadFotoDePerfil/{id_usuario}").hasAnyRole("OFERTANTE")
                        .requestMatchers(HttpMethod.GET, "/api/usuarioOfertante/visualizar/{id_usuario}").hasAnyRole("OFERTANTE")
                        .requestMatchers(HttpMethod.PATCH, "/api/usuarioOfertante/atualizar/{id}").hasAnyRole("OFERTANTE")

                        // Interesses interessado
                        .requestMatchers(HttpMethod.POST, "/api/interessesInteressados/cadastrar/{id_usuario}").hasAnyRole("INTERESSADO")
                        .requestMatchers(HttpMethod.PATCH, "/api/interessesInteressados/atualizar/{id_usuario}").hasAnyRole("INTERESSADO")


                        // Interesses Ofertante
                        .requestMatchers(HttpMethod.POST, "/api/interessesOfertantes/cadastrar/{id_usuario}").hasAnyRole("OFERTANTE")
                        .requestMatchers(HttpMethod.PATCH, "/api/interessesOfertantes/atualizar/{id_usuario}").hasAnyRole("OFERTANTE")

                        // Anuncio
                        .requestMatchers(HttpMethod.POST, "/api/anuncio/cadastrar/{id_usuario}").hasAnyRole("OFERTANTE")
                        .requestMatchers(HttpMethod.POST, "/api/anuncio/uploadNovaFoto/{id_usuario}").hasAnyRole("OFERTANTE")
                        .requestMatchers(HttpMethod.PATCH, "/api/anuncio/atualizar/{id_usuario}").hasAnyRole("OFERTANTE")
                        .requestMatchers(HttpMethod.PATCH, "/api/anuncio/pausar/{id_usuario}").hasAnyRole("OFERTANTE")
                        .requestMatchers(HttpMethod.PATCH, "/api/anuncio/reativar/{id_usuario}").hasAnyRole("OFERTANTE")

                        // Match
                        .requestMatchers(HttpMethod.GET, "/api/match/buscarCandidatos").hasAnyRole("INTERESSADO")
                        .requestMatchers(HttpMethod.POST, "/api/match/enviarLike").hasAnyRole("INTERESSADO")
                        .requestMatchers(HttpMethod.POST, "/api/match/aceitar/{id_match}").hasAnyRole("OFERTANTE")
                        .requestMatchers(HttpMethod.POST, "/api/match/recusar/{id_match}").hasAnyRole("OFERTANTE")
                        .requestMatchers(HttpMethod.GET, "/api/match/visualizarMeusLikes").hasAnyRole("OFERTANTE")

                        .requestMatchers(HttpMethod.GET, "/api/anuncio/visualizarTodos").hasAnyRole("INTERESSADO")
                )

                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}