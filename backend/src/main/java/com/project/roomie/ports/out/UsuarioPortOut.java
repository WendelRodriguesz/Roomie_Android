package com.project.roomie.ports.out;

import com.project.roomie.core.model.Usuario;

public interface UsuarioPortOut {
    Usuario findByEmail(String email);
    Usuario findById(Integer id);
}