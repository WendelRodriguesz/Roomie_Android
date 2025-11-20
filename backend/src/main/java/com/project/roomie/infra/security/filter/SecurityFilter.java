package com.project.roomie.infra.security.filter;

import com.project.roomie.infra.security.service.TokenService;
import com.project.roomie.ports.out.UsuarioPortOut;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioPortOut usuarioPortOut;

    @Autowired
    public SecurityFilter(TokenService tokenService,
                          UsuarioPortOut usuarioPortOut) {
        this.tokenService = tokenService;
        this.usuarioPortOut = usuarioPortOut;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, ServletException, IOException {
        var token = this.recoverToken(request);
        if(token != null) {
            var email = tokenService.validateToken(token);
            UserDetails user = (UserDetails) usuarioPortOut.findByEmail(email);

            var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}