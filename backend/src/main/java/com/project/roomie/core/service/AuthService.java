package com.project.roomie.core.service;

import com.project.roomie.core.model.Usuario;
import com.project.roomie.dto.auth.AuthDTO;
import com.project.roomie.dto.auth.RefreshDTO;
import com.project.roomie.dto.response.AuthResponseDTO;
import com.project.roomie.infra.security.service.RefreshTokenService;
import com.project.roomie.infra.security.service.TokenService;
import com.project.roomie.ports.in.AuthPortIn;
import com.project.roomie.ports.out.UsuarioPortOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements AuthPortIn {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;
    private final UsuarioPortOut usuarioPortOut;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager,
                       TokenService tokenService,
                       RefreshTokenService refreshTokenService,
                       UsuarioPortOut usuarioPortOut) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.refreshTokenService = refreshTokenService;
        this.usuarioPortOut = usuarioPortOut;
    }

    @Override
    public ResponseEntity logar(AuthDTO usuario){

        var usuarioSenha = new UsernamePasswordAuthenticationToken(usuario.getEmail(),usuario.getSenha());
        var auth = authenticationManager.authenticate(usuarioSenha);
        Usuario usuarioLogado = (Usuario) auth.getPrincipal();
        var token = tokenService.generatedToken(usuarioLogado);
        var refreshToken = refreshTokenService.generateRefreshToken(usuarioLogado);
        return ResponseEntity.ok(new AuthResponseDTO(usuarioLogado.getId(),
                                                    token,
                                                    refreshToken,
                                                    usuarioLogado.getRole()));
    }

    @Override
    public ResponseEntity refresh(RefreshDTO refreshDTO) {

        if (refreshDTO.getRefresh_token() == null || refreshDTO.getRefresh_token().isBlank()) {
            return ResponseEntity.badRequest().body("Refresh token não informado.");
        }

        String username = refreshTokenService.validateRefreshToken(refreshDTO.getRefresh_token());
        if (username == null || username.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Refresh token inválido ou expirado.");
        }

        Usuario usuario = usuarioPortOut.findByEmail(username);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuário não encontrado para esse refresh token.");
        }

        var novoAccessToken = tokenService.generatedToken(usuario);
        var novoRefreshToken = refreshTokenService.generateRefreshToken(usuario);

        return ResponseEntity.ok(new AuthResponseDTO(usuario.getId(),
                                                     novoAccessToken,
                                                     novoRefreshToken,
                                                     usuario.getRole()));
    }
}