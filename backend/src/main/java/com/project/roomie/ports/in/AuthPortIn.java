package com.project.roomie.ports.in;

import com.project.roomie.dto.auth.AuthDTO;
import org.springframework.http.ResponseEntity;

public interface AuthPortIn {

    ResponseEntity logar(AuthDTO usuario);
}