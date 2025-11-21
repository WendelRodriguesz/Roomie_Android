package com.project.roomie.core.service;

import com.project.roomie.core.model.Usuario;
import com.project.roomie.dto.auth.AuthDTO;
import com.project.roomie.dto.response.AuthResponseDTO;
import com.project.roomie.infra.security.service.RefreshTokenService;
import com.project.roomie.infra.security.service.TokenService;
import com.project.roomie.ports.in.AuthPortIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.auditing.AuditingHandlerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements AuthPortIn {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager,
                       TokenService tokenService,
                       RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public ResponseEntity logar(AuthDTO usuario){

        var usuarioSenha = new UsernamePasswordAuthenticationToken(usuario.getEmail(),usuario.getSenha());
        var auth = authenticationManager.authenticate(usuarioSenha);
        Usuario usuarioLogado = (Usuario) auth.getPrincipal();
        var token = tokenService.generatedToken(usuarioLogado);
        var refreshToken = refreshTokenService.generateRefreshToken(usuarioLogado);
        return ResponseEntity.ok(new AuthResponseDTO(token, refreshToken, usuarioLogado.getRole()));
    }
}