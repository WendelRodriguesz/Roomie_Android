package com.project.roomie.infra.security.service;

import com.project.roomie.ports.out.UsuarioPortOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioPortOut usuarioPortOut;

    @Autowired
    public UsuarioService(UsuarioPortOut usuarioPortOut){
        this.usuarioPortOut = usuarioPortOut;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioPortOut.findByEmail(email);
    }
}